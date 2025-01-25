package com.choiceApp.MyChoiceApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomInjectedVariables {

    @Value("${custom.keycloak.url}")
    private String keycloakUrl;

    @Value("${custom.keycloak.realm}")
    private String keycloakRealm;

    @Value("${custom.keycloak.client.id}")
    private String keycloakClientId;

    @Value("${custom.keycloak.client.secret}")
    private String keycloakClientSecret;

    public String getKeycloakUrl() {
        return keycloakUrl;
    }

    public String getKeycloakRealm() {
        return keycloakRealm;
    }

    public String getKeycloakClientId() {
        return keycloakClientId;
    }

    public String getKeycloakClientSecret() {
        return keycloakClientSecret;
    }
}
