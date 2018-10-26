package springbatchparallelchunks.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import springbatchparallelchunks.constants.Constant;
import springbatchparallelchunks.domain.Person;
import springbatchparallelchunks.itemprocessor.PersonConcatenateFirstAndLastProcessor;
import springbatchparallelchunks.itemprocessor.PersonConcatenateLastAndFirstProcessor;
import springbatchparallelchunks.itemprocessor.PersonToUpperProcessor;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class Steps {


    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonToUpperProcessor personItemProcessor;


    @Autowired
    PersonConcatenateFirstAndLastProcessor personConcatenateFirstAndLastProcessor;

    @Autowired
    PersonConcatenateLastAndFirstProcessor personConcatenateLastAndFirstProcessor;

    private Resource outputResource = new FileSystemResource(Constant.OUTPUT_PATH);

    @PostConstruct
    public void init () throws Exception{
        if(outputResource.exists()) {
            File file = outputResource.getFile();
            file.delete();
            file.createNewFile();
        } else {
            File file = new File(Constant.OUTPUT_PATH);
            file.createNewFile();
        }

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(5)
                .reader(reader())
                .processor(compositeItemProcessor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .throttleLimit(20)
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
    public FlatFileItemReader<Person> reader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper() {
            {

                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "rowNumber", "firstName", "lastName" });
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
                    {
                        setTargetType(Person.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public FlatFileItemWriter<Person> writer() {
        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(outputResource);


        writer.setAppendAllowed(true);

        writer.setLineAggregator(new DelimitedLineAggregator<Person>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Person>() {
                    {
                        setNames(new String[] { "rowNumber", "firstName", "lastName", "firstLastName", "lastFirstName"});
                    }
                });
            }
        });
        return writer;
    }

    @Bean
    public CompositeItemProcessor compositeItemProcessor(){
        List<ItemProcessor> processors = new ArrayList<>();
        processors.add(processor());
        processors.add(processor1());
        processors.add(processor2());
        CompositeItemProcessor itemProcessor = new CompositeItemProcessor();
        itemProcessor.setDelegates(processors);
        return itemProcessor;
    }

}
