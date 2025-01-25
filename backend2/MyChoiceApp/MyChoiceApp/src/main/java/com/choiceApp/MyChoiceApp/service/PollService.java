package com.choiceApp.MyChoiceApp.service;

import com.choiceApp.MyChoiceApp.dataAccess.ChoiceRepository;
import com.choiceApp.MyChoiceApp.dataAccess.PollRepository;
import com.choiceApp.MyChoiceApp.dataAccess.QuestionRepository;
import com.choiceApp.MyChoiceApp.dataAccess.VoteRepository;
import com.choiceApp.MyChoiceApp.models.Choice;
import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PollService extends MainService {

    public PollService(PollRepository pollRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, VoteRepository voteRepository) {
        super(pollRepository, questionRepository, choiceRepository, voteRepository);
    }

    public Poll getFullPoll(String pollId) {

        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if (pollOptional.isEmpty()) {
            throw new RuntimeException("Poll not found with id: " + pollId);
        }

        Poll poll = pollOptional.get();
        List<Question> questions = questionRepository.findByPollId(pollId);

        for (Question question : questions) {
            List<Choice> choices = choiceRepository.findByQuestionId(question.getId());
            question.setChoices(choices);
        }

        poll.setQuestions(questions);

        return poll;
    }

    public List<Poll> getPolls(int loadedPolls){

        List<Poll> polls = pollRepository.customPageFindPolls(loadedPolls);

        return polls;

    }

    public String createPoll (Poll poll, String token) throws IOException {

        if(!ValidToken(token)){
            return "Invalid Token";
        }

        if (poll == null) {
            throw new IllegalArgumentException("Poll cannot be null.");
        }

        poll.setCreator(token);

        if (poll.getQuestions() == null || poll.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("Poll must have at least one question.");
        }

        for (Question question : poll.getQuestions()) {

            if (question.getChoices().isEmpty()) {
                throw new IllegalArgumentException("Question must have at least one choice.");
            }

            if (question.getChoices().size() < Integer.parseInt(question.getAllowedChoices())) {
                throw new IllegalArgumentException("Invalid number of Choices to Question");
            }

        }

        pollRepository.save(poll);

        return "Successfully Created Poll";

    }

    public String updatePoll (String pollId, Map<String, Boolean> updateFields, String token) throws IOException {

        if(!ValidToken(token)){
            return "Invalid Token";
        }

        Optional<Poll> existingPollOptional = pollRepository.findById(pollId);

        if (existingPollOptional.isEmpty()) {
            throw new RuntimeException("Poll not found with id: " + pollId);
        }

        Poll existingPoll = existingPollOptional.get();

        if (updateFields.containsKey("open")) {
            existingPoll.setOpen(updateFields.get("open"));
        }
        if (updateFields.containsKey("showResults")) {
            existingPoll.setShowResults(updateFields.get("showResults"));
        }
        if (updateFields.containsKey("publicity")) {
            existingPoll.setPublicity(updateFields.get("publicity"));
        }

        pollRepository.save(existingPoll);

        return "Successfully Update Poll";
    }

}
