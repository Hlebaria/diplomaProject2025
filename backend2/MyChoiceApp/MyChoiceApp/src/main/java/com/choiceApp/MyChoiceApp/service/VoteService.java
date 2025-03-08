package com.choiceApp.MyChoiceApp.service;

import com.choiceApp.MyChoiceApp.dataAccess.ChoiceRepository;
import com.choiceApp.MyChoiceApp.dataAccess.PollRepository;
import com.choiceApp.MyChoiceApp.dataAccess.QuestionRepository;
import com.choiceApp.MyChoiceApp.dataAccess.VoteRepository;
import com.choiceApp.MyChoiceApp.models.Choice;
import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.models.Question;
import com.choiceApp.MyChoiceApp.models.Vote;
import com.choiceApp.MyChoiceApp.models.DTOs.VoteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class VoteService extends MainService {

    public VoteService(PollRepository pollRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, VoteRepository voteRepository) {
        super(pollRepository, questionRepository, choiceRepository, voteRepository);
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> castVote (String pollId, String token, List<VoteDTO> voteDTOs) throws IOException {

        Optional<Poll> optionalPoll = pollRepository.findById(pollId);

        if(optionalPoll.isEmpty()){
            return createResponse(false, "Poll Not Found");
        }

        Poll poll = optionalPoll.get();

        if(!poll.getOpen()){
            return createResponse(false, "Poll Closed");
        }

        if(isPollClosingTime(poll)){
            poll.setOpen(false);
            pollRepository.save(poll);
            return createResponse(false, "Poll Closed");
        }

        if(!ValidToken(token) && poll.getPlatformOnly()){
            return createResponse(false,"Invalid Account Session");
        }

        if(!token.isEmpty()) {
            if (!voteRepository.checkVote(extractUserIdFromToken(token), pollId).isEmpty()) {
                return createResponse(false, "User Already voted");
            }
        }

        //increment every choice count by one
        //create a rows to register each choice

        List<Integer> votedQuestions = new java.util.ArrayList<>(List.of());
        List<Choice> choices = new java.util.ArrayList<>(List.of());
        List<Vote> votes = new java.util.ArrayList<>(List.of());

        for (VoteDTO voteDTO : voteDTOs) {

            Optional<Question> optionalQuestion = questionRepository.findById(voteDTO.getQuestionId());

            if(optionalQuestion.isEmpty()){
                return createResponse(false, "Question Not Found");
            }

            Question question = optionalQuestion.get();

            votedQuestions.add(question.getId());

            int choiceCounter = voteDTO.getChoiceIds().size();

            if(choiceCounter > question.getMaxVoterChoices() || choiceCounter < question.getMinVoterChoices()){
                return createResponse(false, "Invalid number of choices");
            }


            for (Integer choiceId : voteDTO.getChoiceIds()) {

                Optional<Choice> optionalChoice = choiceRepository.findById(choiceId);

                if (optionalChoice.isEmpty()) {
                    return createResponse(false, "Error processing choices");
                }

                //remember what to persist after iteration and checks
                Choice choice = optionalChoice.get();
                choice.setVotesCount(choice.getVotesCount() + 1);
                choices.add(choice);

                if(!token.isEmpty()) {
                    Vote vote = new Vote();
                    vote.setPoll(poll);
                    vote.setChoice(choice);
                    vote.setQuestion(question);
                    vote.setUserId(extractUserIdFromToken(token));
                    votes.add(vote);
                }

            }

        }

        for(Question question : poll.getQuestions()){
            if(!votedQuestions.contains(question.getId())) {
                return createResponse(false, "Blank Votes");
            }
        }

        choiceRepository.saveAll(choices);
        voteRepository.saveAll(votes);

        poll.setVoterCount(poll.getVoterCount() + 1);
        pollRepository.save(poll);

        return createResponse(true, "Vote Casted Successfully");
    }
}
