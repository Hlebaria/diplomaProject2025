package com.choiceApp.MyChoiceApp.controller;

import com.choiceApp.MyChoiceApp.TokenHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/api/hello")
    public String sayHello(@RequestHeader Map<String, String> headers) throws IOException {
        String token = headers.get("authorization");

        if(!token.isEmpty()){
            if (TokenHttpRequest.sendKeycloakPOST(token)) {
                return "Token is valid!";
            }
            return "Token is NOT valid!";
        }

        return "Hello From User Free Backend!";
    }

}
