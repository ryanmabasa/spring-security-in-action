package com.example.ch12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Ch12ApplicationTests {

	@Autowired
	private MockMvc mvc;


	@Test
	@DisplayName("Endpoint /sell returns size 2")
	public void testResult2() throws Exception {
		mvc.perform(get("/sell")
						.with(httpBasic("nikolai", "12345"))) // Add basic auth
				.andExpect(status().isOk()) // Expect 200 OK
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[\n" +
						"  {\"name\":\"beer\",\"owner\":\"nikolai\"},\n" +
						"  {\"name\":\"candy\",\"owner\":\"nikolai\"}\n" +
						"]"));
	}

	@Test
	@DisplayName("Endpoint /sell returns size 1")
	public void testResult1() throws Exception {
		mvc.perform(get("/sell")
						.with(httpBasic("julien", "12345"))) // Add basic auth
				.andExpect(status().isOk()) // Expect 200 OK
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[\n" +
						"  {\"name\":\"chocolate\",\"owner\":\"julien\"}\n" +
						"]"));
	}

	@Test
	@DisplayName("Endpoint /find returns size 1")
	public void testFind1() throws Exception {
		mvc.perform(get("/find")
						.with(httpBasic("julien", "12345"))) // Add basic auth
				.andExpect(status().isOk()) // Expect 200 OK
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[\n" +
						"  {\"name\":\"chocolate\",\"owner\":\"julien\"}\n" +
						"]"));
	}

	@Test
	@DisplayName("Endpoint /products/c returns size 1")
	public void testProducts1() throws Exception {
		mvc.perform(get("/products/c")
						.with(httpBasic("nikolai", "12345"))) // Add basic auth
				.andExpect(status().isOk()) // Expect 200 OK
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[\n" +
						"  {\"id\":2,\"name\":\"candy\",\"owner\":\"nikolai\"}\n" +
						"]"));
	}
}