package com.choiceApp.MyChoiceApp.controller;

import com.choiceApp.MyChoiceApp.models.DTOs.ShortPollDTO;
import com.choiceApp.MyChoiceApp.models.DTOs.VoteDTO;
import com.choiceApp.MyChoiceApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/polls")
    public ResponseEntity<List<ShortPollDTO>> getUsersPolls(@RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return userService.getUsersOwnedPolls(token);
    }

    @GetMapping("/activity")
    public ResponseEntity<List<ShortPollDTO>> getUsersVotedPolls(@RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return userService.getUsersVotedPolls(token);
    }

    @GetMapping("/vote/{pollId}")
    public ResponseEntity<List<VoteDTO>> getChoicesFromPoll(@RequestHeader Map<String, String> headers, @PathVariable String pollId) throws IOException {
        String token = headers.get("authorization");
        return userService.getUserChoicesFromPoll(pollId, token);
    }

    @GetMapping("owner-check/{pollId}")
    public Boolean isPollOwnerCheck(@PathVariable String pollId, @RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return userService.isOwner(pollId, token);
    }

}
