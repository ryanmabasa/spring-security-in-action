package com.example.ch16.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    // Omitted constructor
    public DemoController(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }

    @GetMapping("/token")
    public String token() {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("1")
                .principal("client")
                .build();

        var client =
                oAuth2AuthorizedClientManager.authorize(request);

        return client
                .getAccessToken().getTokenValue();
    }
}
