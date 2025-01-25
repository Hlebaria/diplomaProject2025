package com.choiceApp.MyChoiceApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenHttpRequest {

    //to automate injected variables like KEYCLOAK_REALM
    //injected varables can't be mapped to statis fields
    private static CustomInjectedVariables vars;

    @Autowired
    public TokenHttpRequest(CustomInjectedVariables vars) {
        TokenHttpRequest.vars = vars;
    }

    public static Boolean sendKeycloakPOST(String token) throws IOException {
//        token = token.split(" ")[1]; // if there is a Bearer field

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(vars.getKeycloakUrl() + "/realms/" + vars.getKeycloakRealm() + "/protocol/openid-connect/token/introspect");
        System.out.println(vars.getKeycloakUrl() + "/realms/" + vars.getKeycloakRealm() + "/protocol/openid-connect/token/introspect");

        List<BasicNameValuePair> params = new ArrayList<>(3);
        params.add(new BasicNameValuePair("client_id", vars.getKeycloakClientId()));
        params.add(new BasicNameValuePair("client_secret", vars.getKeycloakClientSecret()));
        params.add(new BasicNameValuePair("token", token));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpclient.execute(httppost);

        String responseString = EntityUtils.toString(response.getEntity());

        System.out.println(params.toString());
        System.out.println("Response Status: " + response.getStatusLine());
        System.out.println("Response Body: " + responseString);

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseString);

        //Extract the "active" field
        return responseJson.get("active").asBoolean();

//        return responseJson.toString();
//        return response.toString();

    }

}
