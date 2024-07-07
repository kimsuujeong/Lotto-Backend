package kr.co.polycube.backendtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest
@AutoConfigureMockMvc
public class LottosAPITest extends IntergrationTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job checkWinnersJob;

	@Test
	public void testLottoJob() throws Exception {
		
		JobParameters jobParameters = new JobParameters();
		JobExecution jobExecution = jobLauncher.run(checkWinnersJob, jobParameters);
		ExitStatus exitStatus = jobExecution.getExitStatus();
		assertThat(exitStatus).isEqualTo(ExitStatus.COMPLETED);
		
	}

	@Test
	public void testGenerateLotto() throws Exception {
		
		mockMvc.perform(post("/lottos").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> {

					String jsonResponse = result.getResponse().getContentAsString();
					JsonNode jsonNode = objectMapper.readTree(jsonResponse);

					assertThat(jsonNode.has("numbers")).isTrue();
					assertThat(jsonNode.get("numbers").isArray()).isTrue();

					JsonNode numbers = jsonNode.get("numbers");
					assertThat(numbers.size()).isEqualTo(6);

					for (JsonNode number : numbers) {
						
						assertThat(number.asInt()).isBetween(1, 45);

					}

				});
		
	}

}
