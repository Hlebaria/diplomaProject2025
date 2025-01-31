package com.choiceApp.MyChoiceApp.service;

import com.choiceApp.MyChoiceApp.dataAccess.ChoiceRepository;
import com.choiceApp.MyChoiceApp.dataAccess.PollRepository;
import com.choiceApp.MyChoiceApp.dataAccess.QuestionRepository;
import com.choiceApp.MyChoiceApp.dataAccess.VoteRepository;
import com.choiceApp.MyChoiceApp.models.*;
import com.choiceApp.MyChoiceApp.models.DTOs.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class PollService extends MainService {

    public PollService(PollRepository pollRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, VoteRepository voteRepository) {
        super(pollRepository, questionRepository, choiceRepository, voteRepository);
    }

    public ResponseEntity<Map<String, Object>> createPollCreationResponse(boolean success, String message, String id){

        Map<String, Object> response = new HashMap<>();
        response.put("status", success);
        response.put("message", message);
        response.put("id", id);

        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    public FullPollDTO getFullPoll(String pollId) {

        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if (pollOptional.isEmpty()) {
            throw new RuntimeException("Poll not found with id: " + pollId);
        }

        Poll poll = pollOptional.get();
        FullPollDTO pollDTO= new FullPollDTO(poll);

        //hide results
        if(!poll.getShowResults()) {
            for (FullQuestionDTO questionDTO : pollDTO.getQuestions()) {
                for (FullChoiceDTO choiceDTO : questionDTO.getChoices()) {
                    choiceDTO.setVotes(0);
                }
            }
        }

        return pollDTO;
    }

    public ResponseEntity<List<ShortPollDTO>> getPolls(int loadedPolls){

        List<Poll> polls = pollRepository.customPageFindPolls(loadedPolls);
        List<ShortPollDTO> pollsDTO = new ArrayList<>(List.of());

        for(Poll poll : polls){

            ShortPollDTO pollDTO = new ShortPollDTO(poll);
            pollsDTO.add(pollDTO);

        }

        return ResponseEntity.ok(pollsDTO);

    }

    public ResponseEntity<Map<String, Object>> createPoll (CreatePollDTO pollDTO, String token) throws IOException {

        Poll poll = new Poll();

        if(!ValidToken(token)){
            return createResponse(false, "Invalid Token");
        }

        if (pollDTO.getQuestions().isEmpty()) {
            return createResponse(false, "Poll must have at least one question.");
        }

        List<Question> questions = new ArrayList<>(List.of());

        for (CreateQuestionDTO questionDTO : pollDTO.getQuestions()) {

            if (questionDTO.getQuestionText().isEmpty()){
                return createResponse(false, "All questions must have text!");
            }

            if (questionDTO.getChoiceTexts().isEmpty()) {
                return createResponse(false, "Question must have at least one choice.");
            }

            if (1 > questionDTO.getMinVotes()) {
                return createResponse(false, "Invalid number of Minimum permitted Choices to Question");
            }

            if (questionDTO.getMinVotes() > questionDTO.getMaxVotes()) {
                return createResponse(false, "Invalid number of Maximum Choices to Question");
            }

            if (questionDTO.getChoiceTexts().size() < questionDTO.getMaxVotes()) {
                return createResponse(false, "Invalid number of Choices to Question");
            }

            Question question = new Question();

            List<Choice> choices = new ArrayList<>(List.of());

            for (String choiceText : questionDTO.getChoiceTexts()){

                if(choiceText.isEmpty()){
                    return createResponse(false, "All options must have text!");
                }

                Choice choice = new Choice();
                choice.setChoiceText(choiceText);
                choice.setQuestion(question);
                choices.add(choice);

            }

            question.setChoices(choices);
            question.setPoll(poll);
            question.setMinVoterChoices(questionDTO.getMinVotes());
            question.setMaxVoterChoices(questionDTO.getMaxVotes());
            question.setQuestionText(questionDTO.getQuestionText());

            questions.add(question);

        }


        poll.setQuestions(questions);
        poll.setId(generateUniquePollId());
        poll.setCreator(extractUserIdFromToken(token));
        poll.setCreatorName(extractUserNameFromToken(token));
        poll.setCaption(pollDTO.getCaption());
        poll.setPollText(pollDTO.getDescription());
        poll.setCloseTime(pollDTO.getCloseTime());
        poll.setPublicity(pollDTO.getPublicity());
        poll.setPlatformOnly(pollDTO.getPlatformOnly());

        pollRepository.save(poll);

        return createPollCreationResponse(true, "Successfully Created Poll", poll.getId());

    }

    public ResponseEntity<Map<String, Object>> updatePoll (String pollId, Map<String, Boolean> updateFields, String token) throws IOException {



        if(!ValidToken(token)){
            return createResponse(false, "Invalid Token!");
        }

        Optional<Poll> pollOptional = pollRepository.findById(pollId);

        if (pollOptional.isEmpty()) {
            return createResponse(false, "Poll Not Found!");
        }

        Poll poll = pollOptional.get();

        if(!extractUserIdFromToken(token).equals(poll.getCreator())){
            return createResponse(false, "User doesn't own the poll to update it!");
        }

        if (updateFields.containsKey("open")) {
            poll.setOpen(updateFields.get("open"));
        }
        if (updateFields.containsKey("showResults")) {
            poll.setShowResults(updateFields.get("showResults"));
        }
        if (updateFields.containsKey("publicity")) {
            poll.setPublicity(updateFields.get("publicity"));
        }

        pollRepository.save(poll);

        return createResponse(true, "Poll Updated!");
    }

}
