package com.choiceApp.MyChoiceApp.service;

import com.choiceApp.MyChoiceApp.TokenHttpRequest;
import com.choiceApp.MyChoiceApp.dataAccess.ChoiceRepository;
import com.choiceApp.MyChoiceApp.dataAccess.PollRepository;
import com.choiceApp.MyChoiceApp.dataAccess.QuestionRepository;
import com.choiceApp.MyChoiceApp.dataAccess.VoteRepository;
import com.choiceApp.MyChoiceApp.models.Poll;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Service
public abstract class MainService {

    protected final PollRepository pollRepository;
    protected final QuestionRepository questionRepository;
    protected final ChoiceRepository choiceRepository;
    protected final VoteRepository voteRepository;

    @Autowired
    public MainService(PollRepository pollRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.voteRepository = voteRepository;
    }

    protected String extractUserIdFromToken(String token) {
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payload);

            return jsonNode.get("sub").asText();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decode or parse the JWT payload", e);
        }

    }

    protected String extractUserNameFromToken(String token) {
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payload);

            return jsonNode.get("preferred_username").asText();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decode or parse the JWT payload", e);
        }

    }

    protected boolean ValidToken(String token) throws IOException {

        // comment out to pass test for keycloak to debug
        if (token != null) {

            return TokenHttpRequest.sendKeycloakPOST(token);

        }

        return false;

//        if(token != null && !token.isEmpty()){
//            return false;
//        }
//
//        return true;

    }

    public boolean isPollClosingTime(Poll poll) {
        if(poll.getCloseTime() == null){
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(poll.getCloseTime());
    }

    public String generateUniquePollId() {

        String pollId;

        do {
           pollId = NanoIdUtils.randomNanoId();
        } while (pollRepository.existsById(pollId));

        return pollId;

    }

    public ResponseEntity<Map<String, Object>> createResponse(boolean success, String message){

        Map<String, Object> response = new HashMap<>();
        response.put("status", success);
        response.put("message", message);

        if(success){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

}