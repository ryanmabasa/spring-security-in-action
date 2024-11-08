package com.example.ch09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class Ch09ApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("Call endpoint /hello using GET")
	public void testHelloGET() throws Exception {
		mvc.perform(get("/hello"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Call endpoint /hello using POST without providing the CSRF token")
	public void testHelloPOST() throws Exception {
		mvc.perform(post("/hello"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Call endpoint /hello using POST providing the CSRF token")
	public void testHelloPOSTWithCSRF() throws Exception {
		mvc.perform(post("/hello").with(csrf()))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Authenticating with a wrong user")
	public void loggingInWithWrongUser() throws Exception {
		mvc.perform(formLogin())
				.andExpect(unauthenticated());
	}

	@Test
	@DisplayName("Logging in authenticating with valid user")
	@WithUserDetails("mary")
	public void loggingInWithWrongAuthority() throws Exception {
		mvc.perform(formLogin()
						.user("mary").password("12345")
				)
				.andExpect(redirectedUrl("/main"))
				.andExpect(status().isFound())
				.andExpect(authenticated());
	}
}