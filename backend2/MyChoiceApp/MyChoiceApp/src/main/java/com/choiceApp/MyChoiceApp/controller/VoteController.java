package com.choiceApp.MyChoiceApp.controller;

import com.choiceApp.MyChoiceApp.TokenHttpRequest;
import com.choiceApp.MyChoiceApp.service.UserService;
import com.choiceApp.MyChoiceApp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService){
        this.voteService = voteService;
    }

    @PostMapping("/{poll.id}")
    public String castVote(@RequestHeader Map<String, String> headers, @RequestBody List<Integer> choiceIds, @PathVariable String pollId) throws IOException {
        String token = headers.get("authorization");
        return voteService.castVote(pollId, token, choiceIds);
    }

}
