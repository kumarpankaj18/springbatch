package springbatchmultipleparallelsteps.job;

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

import springbatchmultipleparallelsteps.domain.Person;
import springbatchmultipleparallelsteps.itemprocessor.PersonConcatenateFirstAndLastProcessor;
import springbatchmultipleparallelsteps.itemprocessor.PersonConcatenateLastAndFirstProcessor;
import springbatchmultipleparallelsteps.itemprocessor.PersonToUpperProcessor;
import springbatchmultipleparallelsteps.itemreader.Step1Reader;
import springbatchmultipleparallelsteps.itemreader.Step2Reader;
import springbatchmultipleparallelsteps.itemreader.Step3Reader;
import springbatchmultipleparallelsteps.itemwriter.PersonItemWriter;

@Component
public class Steps {


    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonToUpperProcessor personItemProcessor;

    @Autowired
    private Step1Reader step1Reader;

    @Autowired
    private Step2Reader step2Reader;

    @Autowired
    private Step3Reader step3Reader;

    @Autowired
    private PersonItemWriter personItemWriter;

    @Autowired
    PersonConcatenateFirstAndLastProcessor personConcatenateFirstAndLastProcessor;

    @Autowired
    PersonConcatenateLastAndFirstProcessor personConcatenateLastAndFirstProcessor;


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
                .reader(reader2())
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
    public Step1Reader reader() {
        return step1Reader;
    }

    @Bean
    public PersonItemWriter writer() {
        return personItemWriter;
    }

    @Bean
    public Step2Reader reader1() {
        return step2Reader;
    }

    @Bean
    public Step3Reader reader2() {
        return step3Reader;
    }
}
