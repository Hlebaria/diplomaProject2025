package com.choiceApp.MyChoiceApp.controller;

import com.choiceApp.MyChoiceApp.models.DTOs.CreatePollDTO;
import com.choiceApp.MyChoiceApp.models.DTOs.FullPollDTO;
import com.choiceApp.MyChoiceApp.models.DTOs.ShortPollDTO;
import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private PollService pollService;

    @Autowired
    public PollController(PollService pollService){
        this.pollService = pollService;
    }

    @GetMapping
    public ResponseEntity<List<ShortPollDTO>> getPolls(@RequestParam int loadedCount) {
        return pollService.getPolls(loadedCount);
    }

    @GetMapping("/{pollId}")
    public FullPollDTO getPollById(@PathVariable String pollId) {
        return pollService.getFullPoll(pollId);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPoll(@RequestBody CreatePollDTO pollDTO, @RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return pollService.createPoll(pollDTO, token);
    }

    @PatchMapping("/update/{pollId}")
    public ResponseEntity<Map<String, Object>> updatePoll(@PathVariable String pollId, @RequestBody Map<String, Boolean> updateFields, @RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return pollService.updatePoll(pollId, updateFields, token);
    }

}
