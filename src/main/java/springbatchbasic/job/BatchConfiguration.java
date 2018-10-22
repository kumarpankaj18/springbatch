package springbatchbasic.job;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbatchbasic.domain.Person;
import springbatchbasic.itemprocessor.PersonItemProcessor;
import springbatchbasic.itemreader.PersonItemReader;
import springbatchbasic.itemwriter.PersonItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonItemProcessor personItemProcessor;

    @Autowired
    private PersonItemReader personItemReader;

    @Autowired
    private PersonItemWriter personItemWriter;


    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .start(step1)
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return personItemProcessor;
    }

    @Bean
    public PersonItemReader reader() {
        return personItemReader;
    }

    @Bean
    public PersonItemWriter writer() {
        return personItemWriter;
    }

}