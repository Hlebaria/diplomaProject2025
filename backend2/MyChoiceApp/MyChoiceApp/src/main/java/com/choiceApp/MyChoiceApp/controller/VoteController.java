package com.choiceApp.MyChoiceApp.controller;

import com.choiceApp.MyChoiceApp.models.DTOs.VoteDTO;
import com.choiceApp.MyChoiceApp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService){
        this.voteService = voteService;
    }

    @PostMapping("/{pollId}")
    public ResponseEntity<Map<String, Object>> castVote(@RequestHeader Map<String, String> headers, @RequestBody List<VoteDTO> voteDTOs, @PathVariable String pollId) throws IOException {
        String token = headers.get("authorization");
        return voteService.castVote(pollId, token, voteDTOs);
    }

}
