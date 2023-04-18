package com.dooray.samplecmd;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SampleCmdApplicationTests {

	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void buttonTest() throws Exception {
		this.mockMvc.perform(post("/api/button")
									 .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
									 .content("{}")
				)
				.andExpect(status().isOk());
	}
}
