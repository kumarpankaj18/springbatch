package springbatchmultipletaskparallel.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import springbatchmultipletaskparallel.domain.Person;
import springbatchmultipletaskparallel.itemprocessor.PersonConcatenateFirstAndLastProcessor;
import springbatchmultipletaskparallel.itemprocessor.PersonConcatenateLastAndFirstProcessor;
import springbatchmultipletaskparallel.itemprocessor.PersonToUpperProcessor;
import springbatchmultipletaskparallel.itemreader.PersonItemReader;
import springbatchmultipletaskparallel.itemreader.PersonItemReaderList;
import springbatchmultipletaskparallel.itemwriter.PersonItemWriter;

@Component
public class Steps {


    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonToUpperProcessor personItemProcessor;

    @Autowired
    private PersonItemReader personItemReader;

    @Autowired
    private PersonItemWriter personItemWriter;

    @Autowired
    PersonConcatenateFirstAndLastProcessor personConcatenateFirstAndLastProcessor;

    @Autowired
    PersonConcatenateLastAndFirstProcessor personConcatenateLastAndFirstProcessor;

    @Autowired
    PersonItemReaderList personItemReaderList;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<Person, Person>chunk(10)
                .reader(reader1())
                .processor(processor1())
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .<Person, Person>chunk(10)
                .reader(reader1())
                .processor(processor2())
                .build();
    }

    @Bean
    public Flow splitFlow() {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(taskExecutor())
                .add(flow1(), flow2())
                .build();
    }

    @Bean
    public Step splitStep() {
        return stepBuilderFactory.get("split_step")
                .flow(splitFlow())
                .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<SimpleFlow>("flow1")
                .start(step3())
                .build();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<SimpleFlow>("flow2")
                .start(step2())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor("spring_batch");
    }


    @Bean
    public PersonConcatenateLastAndFirstProcessor processor1(){
        return personConcatenateLastAndFirstProcessor;
    }

    @Bean
    public PersonConcatenateFirstAndLastProcessor processor2(){
        return personConcatenateFirstAndLastProcessor;
    }

    @Bean
    public PersonToUpperProcessor processor() {
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

    @Bean
    public PersonItemReaderList reader1() {
        return personItemReaderList;
    }
}
