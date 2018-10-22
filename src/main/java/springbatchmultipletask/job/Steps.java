package springbatchmultipletask.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springbatchmultipletask.domain.Person;
import springbatchmultipletask.itemprocessor.PersonConcatenateFirstAndLastProcessor;
import springbatchmultipletask.itemprocessor.PersonConcatenateLastAndFirstProcessor;
import springbatchmultipletask.itemprocessor.PersonToUpperProcessor;
import springbatchmultipletask.itemreader.PersonItemReader;
import springbatchmultipletask.itemreader.PersonItemReaderList;
import springbatchmultipletask.itemwriter.PersonItemWriter;

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
                .writer(writer())
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
