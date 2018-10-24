package springbatchparallelchunks.job;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private Step step1;

    @Autowired
    private Step step2;

    @Autowired
    private Step step3;

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .start(step1)
                .build();
    }


}