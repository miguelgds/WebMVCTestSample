package com.miguelgs.webmvctest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class WebmvctestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void hello_with_name_must_return_greeting() throws Exception {
		mockMvc.perform(get("/hello?name=Miguel")
						.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Hello Miguel!")));
	}

	@Test
	void hello_without_name_must_fail() throws Exception {
		mockMvc.perform(get("/hello")
						.accept(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code", is(1)))
				.andExpect(jsonPath("$.description", is("The name field is mandatory")));
	}

}
