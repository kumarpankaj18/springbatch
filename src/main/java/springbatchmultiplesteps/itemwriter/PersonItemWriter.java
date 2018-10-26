package springbatchmultiplesteps.itemwriter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.domain.Person;
import springbatchmultiplesteps.constants.Constant;


import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class PersonItemWriter  extends  BaseItemWriter{

    private Resource outputResource = new FileSystemResource(Constant.OUTPUT_PATH_1);

    @PostConstruct
    public void init () throws Exception{
        baseInit(outputResource, Constant.OUTPUT_PATH_1);

    }

    @Bean(name = "writer1")
    public FlatFileItemWriter<Person> writer1() {
        return writer(outputResource);
    }
}
