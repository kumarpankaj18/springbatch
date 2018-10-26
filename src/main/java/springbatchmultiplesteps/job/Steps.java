package springbatchmultiplesteps.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.constants.Constant;
import springbatchmultiplesteps.domain.Person;
import springbatchmultiplesteps.itemprocessor.PersonConcatenateFirstAndLastProcessor;
import springbatchmultiplesteps.itemprocessor.PersonConcatenateLastAndFirstProcessor;
import springbatchmultiplesteps.itemprocessor.PersonToUpperProcessor;
import springbatchmultiplesteps.itemreader.Step1Reader;
import springbatchmultiplesteps.itemreader.Step2Reader;
import springbatchmultiplesteps.itemwriter.PersonItemWriter;
import springbatchmultiplesteps.itemwriter.PersonItemWriter2;

import java.io.IOException;

@Component
public class Steps {


    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonToUpperProcessor personItemProcessor;

    @Autowired
    private SynchronizedItemStreamReader<Person> reader;

    @Autowired
    private  SynchronizedItemStreamReader<Person> reader1;

    @Autowired
    private SynchronizedItemStreamReader<Person> reader2;

    @Autowired
    private FlatFileItemWriter<Person> writer1;
    @Autowired
    private FlatFileItemWriter<Person> writer2;
    @Autowired
    private FlatFileItemWriter<Person> writer3;

    @Autowired
    PersonConcatenateFirstAndLastProcessor personConcatenateFirstAndLastProcessor;

    @Autowired
    PersonConcatenateLastAndFirstProcessor personConcatenateLastAndFirstProcessor;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(10)
                .reader(reader)
                .processor(processor())
                .writer(writer1)
                // if you want to perform  steps again on restart then you can set this to true
                //.allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<Person, Person>chunk(3)
                .reader(reader1)
                .processor(processor1())
                .writer(writer2)
                .faultTolerant()
                .retryLimit(3)
                .retry(Exception.class)
                //.taskExecutor(taskExecutor())

               // .skip(Exception.class)
               // .skipLimit(1)
                .build();
    }

//    @Bean
//    public Step partitionStep(){
//        return stepBuilderFactory.get("partitionStep")
//                .partitioner(step2())
//                .partitioner("parallel_master_slave", partitioner())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public Partitioner partitioner(){
//        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
//        Resource[] resources;
//        try {
//            resources = resourcePatternResolver.getResources(Constant.OUTPUT_PATH_1);
//        } catch (IOException e) {
//            throw new RuntimeException("I/O problems when resolving the input file pattern.",e);
//        }
//        partitioner.setResources(resources);
//        return partitioner;
//    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .<Person, Person>chunk(10)
                .reader(reader2)
                .processor(processor2())
                .writer(writer3)
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
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

}
