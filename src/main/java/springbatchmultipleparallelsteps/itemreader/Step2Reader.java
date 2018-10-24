package springbatchmultipleparallelsteps.itemreader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import springbatchmultipleparallelsteps.domain.Person;

import java.io.IOException;
import java.util.List;

@Component
public class Step2Reader implements ItemReader<Person> , StepExecutionListener {

    private List<Person> personList;

    @Override
    public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(personList !=null && personList.size()>0){
            return personList.remove(0);
        }
        return null;

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String src =  stepExecution.getJobExecution().getExecutionContext().getString("step2");
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().
                constructCollectionType(List.class, Person.class);
        try {
            personList = objectMapper.readValue(src, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
