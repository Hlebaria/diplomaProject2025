package com.choiceApp.MyChoiceApp.service;

import com.choiceApp.MyChoiceApp.dataAccess.ChoiceRepository;
import com.choiceApp.MyChoiceApp.dataAccess.PollRepository;
import com.choiceApp.MyChoiceApp.dataAccess.QuestionRepository;
import com.choiceApp.MyChoiceApp.dataAccess.VoteRepository;
import com.choiceApp.MyChoiceApp.models.Choice;
import com.choiceApp.MyChoiceApp.models.DTOs.ShortPollDTO;
import com.choiceApp.MyChoiceApp.models.DTOs.VoteDTO;
import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.models.Question;
import com.choiceApp.MyChoiceApp.models.Vote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends MainService {

    public UserService(PollRepository pollRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, VoteRepository voteRepository) {
        super(pollRepository, questionRepository, choiceRepository, voteRepository);
    }

    public ResponseEntity<List<ShortPollDTO>> getUsersOwnedPolls(String token) throws IOException {

        if(token.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if(!ValidToken(token)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String userId = extractUserIdFromToken(token);

        List<Poll> polls = pollRepository.findByCreator(userId);

        List<ShortPollDTO> pollDTOs = new ArrayList<>(List.of());

        for(Poll poll : polls){
            ShortPollDTO pollDTO = new ShortPollDTO(poll);
            pollDTOs.add(pollDTO);
        }

        return ResponseEntity.ok(pollDTOs);
    }

    public ResponseEntity<List<ShortPollDTO>> getUsersVotedPolls(String token) throws IOException {

        if(token.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if(!ValidToken(token)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String userId = extractUserIdFromToken(token);

        List<Poll> polls = pollRepository.customFindPollsVotedByUser(userId);

        List<ShortPollDTO> pollDTOs = new ArrayList<>(List.of());

        for(Poll poll : polls){
            ShortPollDTO pollDTO = new ShortPollDTO(poll);
            pollDTOs.add(pollDTO);
        }

        return ResponseEntity.ok(pollDTOs);

    }

    public ResponseEntity<List<VoteDTO>> getUserChoicesFromPoll(String pollId, String token) throws IOException {

        List<VoteDTO> voteDTOs = new ArrayList<>(List.of());

        if(!ValidToken(token)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(voteDTOs);
        }

        String userId = extractUserIdFromToken(token);

        Optional<Poll> optionalPoll = pollRepository.findById(pollId);

        if (optionalPoll.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(voteDTOs);
        }

        Poll poll = optionalPoll.get();

        for(Question question : poll.getQuestions()){

            VoteDTO voteDTO = new VoteDTO();
            voteDTO.setQuestionId(question.getId());

            voteDTO.setChoiceIds(voteRepository.checkVotesForQuestion(userId, pollId, question.getId()));

            if(voteDTO.getChoiceIds().isEmpty()){
               return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(voteDTOs);
            }

            voteDTOs.add(voteDTO);
        }


        return ResponseEntity.ok(voteDTOs);
    }

    public Boolean isOwner(String pollId, String token) throws IOException {

        if(token.isEmpty()){
            return false;
        }

        if(!ValidToken(token)){
            return false;
        }

        return extractUserIdFromToken(token).equals(pollRepository.customFindOwnerOfPoll(pollId));

    }

}
