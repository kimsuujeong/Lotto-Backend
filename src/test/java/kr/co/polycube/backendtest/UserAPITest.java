package kr.co.polycube.backendtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import kr.co.polycube.backendtest.Entity.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAPITest extends IntergrationTest {

	@Test
	public void createUser() throws Exception {
		User user = new User();
		user.setName("Test User");

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
				.andDo(MockMvcResultHandlers.print());

		resultActions.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("id").isNumber());
	}

	@Test
	public void getUser() throws Exception {
		User user = new User();
		user.setName("Test get User");

		ResultActions saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));

		String responseBody = saveResult.andReturn().getResponse().getContentAsString();
		Long userId = objectMapper.readTree(responseBody).get("id").asLong();

		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.get("/users/{id}", userId).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print());

		resultActions.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test get User"));
	}

	@Test
	public void updateUser() throws Exception {
		User user = new User();
		user.setName("Test User");

		ResultActions saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));

		String responseBody = saveResult.andReturn().getResponse().getContentAsString();
		Long userId = objectMapper.readTree(responseBody).get("id").asLong();

		User updatedUser = new User();
		updatedUser.setId(userId);
		updatedUser.setName("Test Update User");

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedUser)))
				.andDo(MockMvcResultHandlers.print());

		resultActions.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Update User"));
	}

}
