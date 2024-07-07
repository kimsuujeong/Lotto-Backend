package kr.co.polycube.backendtest.Configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {
	
	@Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job checkWinnersJob;

    @Scheduled(cron = "0 0 0 * * SUN")
    public void runJob() {
        try {
            jobLauncher.run(checkWinnersJob, new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
