package com.example.ch18.controller;

import com.example.ch18.custom.WithCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SampleTestConfiguration.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test calling /hello endpoint without authentication returns unauthorized.")
    public void helloUnauthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }

    //Using a RequestPostProcessor to define a mock user
    @Test
    @DisplayName("Test calling /hello endpoint without authentication returns unauthorized.")
    public void helloAuthenticatedRequestPostProcessor() throws Exception {
        mvc.perform(get("/hello").with(user("mary")))
                .andExpect(status().isOk());
    }

   // To test the second scenario,
    // we need a mock user.
    // To validate the behavior of calling the /hello endpoint
    // with an authenticated user, we use the @WithMockUser annotation.
    // By adding this annotation above the test method, we instruct Spring to set up a SecurityContext
   // that contains a UserDetails implementation instance. It’s basically skipping authentication. Now calling the endpoint behaves as if the user defined by the @WithMockUser annotation successfully authenticated.
    @Test
    @WithMockUser(username = "mary")
    public void helloAuthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }

//    This section discusses obtaining the user details for tests from a UserDetailsService.
//    This approach is an alternative to creating a mock user.
//    The difference is that this time, instead of creating a fake user,
//    we need to get the user from a given UserDetailsService.
//    You use this approach if you want to also test integration with the data source from where your app loads the user details (figure 18.6).

    @Test
    @WithUserDetails("john")
    public void helloAuthenticatedUserDetails() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomUser(username = "mary")
    public void helloAuthenticatedCustomSecurityContext() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }


    @Test
    public void helloAuthenticatingWithValidUser() throws Exception {
        mvc.perform(
                get("/hello")
                        .with(httpBasic("john","12345")))
         .andExpect(status().isOk());
    }

    @Test
    public void loggingInWithWrongAuthority() throws Exception {
        mvc.perform(formLogin()
                        .user("bill").password("12345")
                )
                .andExpect(redirectedUrl("/login?error"))
            .andExpect(status().isFound())
                .andExpect(unauthenticated());
    }

    @Test
    public void loggingInWithCorrectAuthority() throws Exception {
        mvc.perform(formLogin()
                        .user("john").password("12345")
                )
                .andExpect(redirectedUrl("/"))
            .andExpect(status().isFound())
                .andExpect(authenticated());
    }

    //For oauth resource server
//    @Test
//    void demoEndpointSuccessfulAuthenticationTest() throws Exception {
//        mockMvc.perform(
//                get("/hello").with(jwt()))
//    .andExpect(status().isOk());
//    }

//    @Test
//    void demoEndpointSuccessfulAuthenticationTest() throws Exception {
//        mockMvc.perform(
//                get("/demo").with(opaqueToken()))
//    .andExpect(status().isOk());
//    }

    //CSRF


    @Test
    public void testHelloPOST() throws Exception {
        mvc.perform(post("/hello"))
          .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("john") //to authenticate
    public void testHelloPOSTWithCSRF() throws Exception {
        mvc.perform(post("/hello").with(csrf()))
          .andExpect(status().isOk());
    }

    //for cors
//    @Test
//    public void testCORSForTestEndpoint() throws Exception {
//        mvc.perform(options("/test")
//                .header("Access-Control-Request-Method", "POST")
//                .header("Origin", "http://www.example.com")
//      )
//
//      .andExpect(header().exists("Access-Control-Allow-Origin"))
//                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
//                .andExpect(header().exists("Access-Control-Allow-Methods"))
//                .andExpect(header().string("Access-Control-Allow-Methods", "POST"))
//                .andExpect(status().isOk());
//    }

    //for reactive

//    @SpringBootTest
//    @AutoConfigureWebTestClient
//    class MainTests {
//
//        @Autowired
//        private WebTestClient client;
//
//        @Test
//        @WithMockUser
//        void testCallHelloWithValidUser() {
//            client.get()
//            .uri("/hello")
//                    .exchange()
//                    .expectStatus().isOk();
//        }
//
//        @Test
//        void testCallHelloWithValidUserWithMockUser() {
//            client.mutateWith(mockUser())
//           .get()
//                    .uri("/hello")
//                    .exchange()
//                    .expectStatus().isOk();
//
//            client.mutateWith(csrf())
//                    .post()
//                    .uri("/hello")
//                    .exchange()
//                    .expectStatus().isOk();
//        }
//
//
//    }

}