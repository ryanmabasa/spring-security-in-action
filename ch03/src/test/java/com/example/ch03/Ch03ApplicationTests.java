package com.example.ch03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@SpringBootTest
@AutoConfigureMockMvc
class Ch03ApplicationTests {

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
	public void helloAuthenticated() throws Exception {
		mvc.perform(get("/hello")
				.with(user("john")))
			.andExpect(status().isOk());
	}

}
