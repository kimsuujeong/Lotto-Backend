package kr.co.polycube.backendtest.Configuration;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kr.co.polycube.backendtest.Entity.Lotto;
import kr.co.polycube.backendtest.Service.LottosService;

@Configuration
public class BatchConfig {

    @Autowired
    private LottosService lottoService;

    @Bean
    Job checkWinnersJob(JobRepository jobRepository, Step checkWinnersStep) {
        return new JobBuilder("checkWinnersJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(checkWinnersStep)
                .build();
    }

    @Bean
    Step checkWinnersStep(JobRepository jobRepository, Tasklet checkWinnersTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("checkWinnersStep", jobRepository)
                .tasklet(checkWinnersTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    Tasklet checkWinnersTasklet() {
        return (contribution, chunkContext) -> {
            // 로또 번호 가져오기
            List<Lotto> lottos = lottoService.getAllLottos();
            // 당첨 번호 (랜덤하게 설정)
            int[] winningNumbers = {1, 2, 3, 4, 5, 6}; // 예제 번호, 실제로는 다른 로직 필요
            int bonusNumber = 7; // 예제 보너스 번호

            // 당첨자 체크
            lottoService.checkWinners(lottos, winningNumbers, bonusNumber);

            return RepeatStatus.FINISHED;
        };
    }
    
    public JobLauncher getJobLauncher() {
        return getJobLauncher();
    }

    public JobRepository getJobRepository() {
        return getJobRepository();
    }
}