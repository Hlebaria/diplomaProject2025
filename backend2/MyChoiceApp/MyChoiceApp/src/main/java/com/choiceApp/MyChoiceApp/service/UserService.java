package com.choiceApp.MyChoiceApp.service;

import com.choiceApp.MyChoiceApp.dataAccess.ChoiceRepository;
import com.choiceApp.MyChoiceApp.dataAccess.PollRepository;
import com.choiceApp.MyChoiceApp.dataAccess.QuestionRepository;
import com.choiceApp.MyChoiceApp.dataAccess.VoteRepository;
import com.choiceApp.MyChoiceApp.models.Choice;
import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.models.Vote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserService extends MainService {

    public UserService(PollRepository pollRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, VoteRepository voteRepository) {
        super(pollRepository, questionRepository, choiceRepository, voteRepository);
    }

    public List<Poll> getUsersPolls(String token) throws IOException {

        if(!ValidToken(token)){
            return null;
        }

        String userId = extractUserIdFromToken(token);

        return pollRepository.findByCreator(userId);
    }

    public List<Poll> getUsersVotedPolls(String token) throws IOException {

        if(!ValidToken(token)){
            return null;
        }

        String userId = extractUserIdFromToken(token);

        List<Poll> votedPolls = pollRepository.customFindPollsVotedByUser(userId);

        return votedPolls;
    }

    public List<Choice> getUserChoicesFromPoll(String pollId, String token) throws IOException {

        if(!ValidToken(token)){
            return null;
        }

        String userId = extractUserIdFromToken(token);

        List<Choice> choices = choiceRepository.findByPollId(pollId);

        if(!pollRepository.showResults(pollId)){
            for (Choice choice : choices) {
                choice.setVotesCount(0);
            }
        }

        return choices;
    }

}
