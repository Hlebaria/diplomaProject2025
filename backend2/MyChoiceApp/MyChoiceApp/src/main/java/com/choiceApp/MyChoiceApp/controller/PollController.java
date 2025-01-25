package com.choiceApp.MyChoiceApp.controller;

import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.service.PollService;
import com.choiceApp.MyChoiceApp.TokenHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Poll> getPolls(@RequestParam int loadedCount) {
        return pollService.getPolls(loadedCount);
    }

    @GetMapping("/{pollId}")
    public Poll getPollById(@PathVariable String pollId) {
        return pollService.getFullPoll(pollId);
    }

    @PostMapping("/create")
    public String createPoll(@RequestBody Poll poll, @RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return pollService.createPoll(poll, token);
    }

    @PatchMapping("/update/{pollId}")
    public String updatePoll(@PathVariable String pollId, @RequestBody Map<String, Boolean> updateFields, @RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return pollService.updatePoll(pollId, updateFields, token);
    }

}
