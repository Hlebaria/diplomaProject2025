package com.choiceApp.MyChoiceApp.service;

import com.choiceApp.MyChoiceApp.dataAccess.ChoiceRepository;
import com.choiceApp.MyChoiceApp.dataAccess.PollRepository;
import com.choiceApp.MyChoiceApp.dataAccess.QuestionRepository;
import com.choiceApp.MyChoiceApp.dataAccess.VoteRepository;
import com.choiceApp.MyChoiceApp.models.Choice;
import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.models.Vote;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService extends MainService {

    public VoteService(PollRepository pollRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, VoteRepository voteRepository) {
        super(pollRepository, questionRepository, choiceRepository, voteRepository);
    }

    public String castVote (String pollId, String token, List<Integer> choices) throws IOException {

        Optional<Poll> optionalPoll = pollRepository.findById(pollId);

        if(optionalPoll.isEmpty()){
            return "Poll Not Found";
        }

        Poll poll = optionalPoll.get();

        if(!poll.getOpen()){
            return "Poll Closed";
        }

        if(isPollClosingTime(poll)){
            poll.setOpen(false);
            pollRepository.save(poll);
            return "Poll Closed";
        }

        if(poll.getPlatformOnly()){

            if(!ValidToken(token)){
                return "Invalid Token";
            }

            //increment every choice count by one
            //create a rows to register each choice

            for (String choiceId : choices) {
                Optional<Choice> optionalChoice = choiceRepository.findById(choiceId);
                if (optionalChoice.isEmpty()) {
                    return "Choice Not Found";
                }

                Choice choice = optionalChoice.get();
                choice.setVotesCount(choice.getVotesCount() + 1);
                choiceRepository.save(choice);

                Vote vote = new Vote();
                vote.setPoll(poll);
                vote.setChoice(choice);
                vote.setUserId(extractUserIdFromToken(token));
                voteRepository.save(vote);
            }

        }

        poll.setVoterCount(poll.getVoterCount() + 1);
        pollRepository.save(poll);

        return "Vote Casted Successfully";
    }
}
