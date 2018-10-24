package springbatchmultiplesteps.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.domain.Person;
import springbatchmultiplesteps.itemprocessor.PersonConcatenateFirstAndLastProcessor;
import springbatchmultiplesteps.itemprocessor.PersonConcatenateLastAndFirstProcessor;
import springbatchmultiplesteps.itemprocessor.PersonToUpperProcessor;
import springbatchmultiplesteps.itemreader.Step1Reader;
import springbatchmultiplesteps.itemreader.Step2Reader;
import springbatchmultiplesteps.itemwriter.PersonItemWriter;

@Component
public class Steps {


    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonToUpperProcessor personItemProcessor;

    @Autowired
    private Step1Reader personItemReader;

    @Autowired
    private PersonItemWriter personItemWriter;

    @Autowired
    PersonConcatenateFirstAndLastProcessor personConcatenateFirstAndLastProcessor;

    @Autowired
    PersonConcatenateLastAndFirstProcessor personConcatenateLastAndFirstProcessor;

    @Autowired
    Step2Reader personItemReaderList;

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
    public Step1Reader reader() {
        return personItemReader;
    }

    @Bean
    public PersonItemWriter writer() {
        return personItemWriter;
    }

    @Bean
    public Step2Reader reader1() {
        return personItemReaderList;
    }
}
