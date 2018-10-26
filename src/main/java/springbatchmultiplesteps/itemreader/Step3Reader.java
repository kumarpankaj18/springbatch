package springbatchmultiplesteps.itemreader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.constants.Constant;
import springbatchmultiplesteps.domain.Person;

import java.io.IOException;
import java.util.List;

@Component
public class Step3Reader extends BaseItemReader {


    Resource inputResource =  new FileSystemResource(Constant.OUTPUT_PATH_2);

    @Bean(name = "reader2")
    public SynchronizedItemStreamReader<Person> reader3() {
        return getReader(inputResource, new String[]{"rowNumber", "firstName", "lastName", "firstLastName", "lastFirstName"});
    }



//    private List<Person> personList;
//
//    @Override
//    public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        if(personList !=null && personList.size()>0){
//            return personList.remove(0);
//        }
//        return null;
//
//    }
//
//    @Override
//    public void beforeStep(StepExecution stepExecution) {
//        String src =  stepExecution.getJobExecution().getExecutionContext().getString("step3");
//        ObjectMapper objectMapper = new ObjectMapper();
//        JavaType type = objectMapper.getTypeFactory().
//                constructCollectionType(List.class, Person.class);
//        try {
//            personList = objectMapper.readValue(src, type);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public ExitStatus afterStep(StepExecution stepExecution) {
//        return null;
//    }
}
