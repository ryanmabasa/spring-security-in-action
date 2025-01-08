package com.example.ch0506;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class Ch0506ApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("Test calling /hello endpoint without authentication returns unauthorized.")
	public void helloUnauthenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("Test calling /hello endpoint authenticated returns ok.")
	@WithMockUser
	public void helloAuthenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isOk());
	}

//	chapter 6-2

	@Test
	@DisplayName("Test calling /hello2 endpoint without authentication returns unauthorized.")
	public void hello2Unauthenticated() throws Exception {
		mvc.perform(get("/hello2"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("Test calling /hello endpoint authenticated returns ok.")
	@WithMockUser(username = "mary")
	public void hello2Authenticated() throws Exception {
		mvc.perform(get("/hello2"))
			.andExpect(content().string("Hello, mary!"))
			.andExpect(status().isOk());
	}


	/*
	* Figure 18.5 The difference between using annotations and the RequestPostProcessor
	* to create the test security environment. When using annotations,
	* the framework sets up the test security environment first.
	*  When using a RequestPostProcessor, the test request is created and
	* then changed to define other constraints such as the test security environment.
	* In the figure, the points where the framework applies
	* the test security environment are shaded.
	* */
	@Test
	@DisplayName("Test calling /ciao endpoint authenticated returns ok.")
	public void ciaoAuthenticated() throws Exception {
		mvc.perform(get("/ciao")
				.with(user("mary")))
			.andExpect(content().string("Ciao, mary!"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test calling /hola endpoint authenticated returns ok.")
	@WithMockUser(username = "mary")
	public void holaAuthenticated() throws Exception {
		mvc.perform(get("/hola"))
			.andExpect(content().string("Hola, mary!"))
			.andExpect(status().isOk());
	}

	//Chapter 6-3


	@Test
	@DisplayName("Test calling /hello endpoint without authentication returns unauthorized.")
	public void hello3Unauthenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(header().string("message", "Luke, I am your father!"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("Test calling /hello endpoint authenticated returns ok.")
	@WithMockUser(username = "mary")
	public void hello3Authenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(content().string("Hello"))
			.andExpect(status().isOk());
	}

	//form login
	@Test
	@DisplayName("Authenticating with wrong user")
	public void loggingInWithWrongUser() throws Exception {
		mvc.perform(formLogin()
				.user("joey").password("12345"))
			.andExpect(header().exists("failed"))
			.andExpect(unauthenticated());
	}

	@Test
	@DisplayName("Logging in authenticating with valid user but wrong authority")
	public void loggingInWithWrongAuthority() throws Exception {
		mvc.perform(formLogin()
				.user("mary").password("12345")
			)
			.andExpect(redirectedUrl("/error"))
			.andExpect(status().isFound())
			.andExpect(authenticated());
	}

	@Test
	@DisplayName("Logging in authenticating with valid user and correct authority")
	public void loggingInWithCorrectAuthority() throws Exception {
		mvc.perform(formLogin()
				.user("bill").password("12345")
			)
			.andExpect(redirectedUrl("/home"))
			.andExpect(status().isFound())
			.andExpect(authenticated());
	}

}
