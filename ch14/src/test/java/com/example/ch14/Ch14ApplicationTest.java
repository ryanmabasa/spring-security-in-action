package com.example.ch14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("client-credentials")
class Ch14ApplicationTest {

    @Autowired
    private MockMvc mvc;


    @Test
    @DisplayName("Endpoint /should return access token")
    public void testResult2() throws Exception {
        mvc.perform(post("/oauth2/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // Specify form data type
                        .header("Authorization", "Basic Y2xpZW50OnNlY3JldA==") // Add custom header
                        .param("grant_type", "client_credentials")
                        .param("scope", "CUSTOM")// Add form data
                ) // Add basic auth
                .andExpect(status().isOk()) // Expect 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));


    }
  
}