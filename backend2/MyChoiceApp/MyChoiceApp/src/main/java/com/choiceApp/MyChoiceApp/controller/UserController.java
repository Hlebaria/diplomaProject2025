package com.choiceApp.MyChoiceApp.controller;

import com.choiceApp.MyChoiceApp.TokenHttpRequest;
import com.choiceApp.MyChoiceApp.models.Choice;
import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.service.PollService;
import com.choiceApp.MyChoiceApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Poll> getUsersPolls(@RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return userService.getUsersPolls(token);
    }

    @GetMapping("/activity")
    public List<Poll> getUsersVotedPolls(@RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");
        return userService.getUsersVotedPolls(token);

    }

    @GetMapping("/vote/{poll.id}")
    public List<Choice> getChoicesFromPoll(@RequestHeader Map<String, String> headers, @PathVariable String pollId) throws IOException {
        String token = headers.get("authorization");
        return userService.getUserChoicesFromPoll(pollId, token);
    }

}
