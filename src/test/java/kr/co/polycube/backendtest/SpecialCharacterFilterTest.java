package kr.co.polycube.backendtest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SpecialCharacterFilterTest extends IntergrationTest {

	@Test
	public void testInvalidCharacterInUrl() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}?name=test!!", 1L)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	public void testValidCharacterInUrl() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}?name=TestUser", 1L)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(MockMvcResultMatchers.status().isOk());
	}

}
