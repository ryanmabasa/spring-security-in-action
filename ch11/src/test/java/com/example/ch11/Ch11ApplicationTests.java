package com.example.ch11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Ch11ApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Endpoint /hello is ok")
    @WithUserDetails("emma")
    public void testHelloControllerIsOk() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Endpoint /hello is forbidden")
    @WithUserDetails("natalie")
    public void testHelloControllerForbidden() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Endpoint /secret/names/emma is ok")
    @WithUserDetails("emma")
    public void testSecretNamesIsOk() throws Exception {
        mvc.perform(get("/secret/names/emma")
                        .with(httpBasic("emma", "12345"))) // Add basic auth
                .andExpect(status().isOk()) // Expect 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Endpoint /secret/names/natalie is forbidden")
    @WithUserDetails("natalie")
    public void testSecretNamesIsForbidden() throws Exception {
        mvc.perform(get("/secret/names/emma")
                        .with(httpBasic("natalie", "12345"))) // Add basic auth
                .andExpect(status().isForbidden());
    }

	@Test
	@DisplayName("Endpoint /book/details/emma is ok")
	@WithUserDetails("emma")
	public void testBookDetailsIsOk() throws Exception {
		mvc.perform(get("/book/details/emma")
						.with(httpBasic("emma", "12345"))) // Add basic auth
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@DisplayName("Endpoint /book/details/natalie is forbidden")
	@WithUserDetails("natalie")
	public void testBookDetailsIsForbidden() throws Exception {
		mvc.perform(get("/book/details/natalie")
						.with(httpBasic("natalie", "12345"))) // Add basic auth
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Endpoint /documents/abc123 is ok")
	@WithUserDetails("natalie")
	public void testDocumentsIsOk() throws Exception {
		mvc.perform(get("/documents/abc123")
						.with(httpBasic("natalie", "12345"))) // Add basic auth
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Endpoint /documents/abc123 is forbidden")
	@WithUserDetails("emma")
	public void testDocumentsIsForbidden() throws Exception {
		mvc.perform(get("/documents/abc123")
						.with(httpBasic("emma", "12345"))) // Add basic auth
				.andExpect(status().isForbidden());
	}




}
