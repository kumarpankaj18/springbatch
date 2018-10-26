package springbatchmultiplesteps.itemwriter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.constants.Constant;
import springbatchmultiplesteps.domain.Person;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class PersonItemWriter2 extends BaseItemWriter {


    private Resource outputResource = new FileSystemResource(Constant.OUTPUT_PATH_2);

    @PostConstruct
    public void init () throws Exception{
        baseInit(outputResource, Constant.OUTPUT_PATH_2);

    }

    @Bean(name = "writer2")
    public FlatFileItemWriter<Person> writer2() {
        return writer(outputResource);
    }


//    private List<Person> personList;
//
//    @Override
//    public void write(List<? extends Person> items) throws Exception {
//        log.info(items.toString());
//        items.stream().forEach(System.out::println);
//        personList = Optional.ofNullable(personList).orElse(new ArrayList<>());
//        personList.addAll(items);
//    }
//
//    @Override
//    public void beforeStep(StepExecution stepExecution) {
//        return ;
//    }
//
//    @Override
//    public ExitStatus afterStep(StepExecution stepExecution){
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String str = objectMapper.writeValueAsString(personList);
//            stepExecution.getJobExecution().getExecutionContext().put("step2", str);
//            stepExecution.getJobExecution().getExecutionContext().put("step3", str);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
