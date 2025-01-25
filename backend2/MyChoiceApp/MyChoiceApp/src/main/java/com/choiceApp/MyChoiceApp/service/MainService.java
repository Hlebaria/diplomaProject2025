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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class MainService {

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

    protected boolean ValidToken(String token) throws IOException {

        if (token != null) {

            return TokenHttpRequest.sendKeycloakPOST(token);

        }

        return false;

    }

    public boolean isPollClosingTime(Poll poll) {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(poll.getCloseTime());
    }

}