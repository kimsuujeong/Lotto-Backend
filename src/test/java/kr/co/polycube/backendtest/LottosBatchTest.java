package kr.co.polycube.backendtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.co.polycube.backendtest.Entity.Lotto;
import kr.co.polycube.backendtest.Repository.LottosRepository;
import kr.co.polycube.backendtest.Repository.WinnerRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LottosBatchTest extends IntergrationTest{

	@Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job checkWinnersJob;

    @Autowired
    private LottosRepository lottosRepository;

    @Autowired
    private WinnerRepository winnerRepository;

    @BeforeEach
    @Transactional
    public void setUp() {
        // Clear previous data
        lottosRepository.deleteAll();
        winnerRepository.deleteAll();

        // Create test lotto entries
        for (int i = 0; i < 10; i++) {
            Set<Integer> numbers = new HashSet<>();
            while (numbers.size() < 6) {
                numbers.add(i + 1); // Ensuring overlap with winning numbers
            }
            lottosRepository.save(convertToLottoEntity(numbers));
        }
    }

    @Test
    public void testBatchJob() throws Exception {
        JobExecution jobExecution = jobLauncher.run(checkWinnersJob, new JobParameters());
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        long winnerCount = winnerRepository.count();
        assertThat(winnerCount).isGreaterThan(0);
    }

    private Lotto convertToLottoEntity(Set<Integer> numbers) {
        Lotto lotto = new Lotto();
        Integer[] numArray = numbers.toArray(new Integer[0]);

        lotto.setNumber_1(numArray[0]);
        lotto.setNumber_2(numArray[1]);
        lotto.setNumber_3(numArray[2]);
        lotto.setNumber_4(numArray[3]);
        lotto.setNumber_5(numArray[4]);
        lotto.setNumber_6(numArray[5]);
        return lotto;
    }
	
}
