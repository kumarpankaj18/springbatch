package springbatchmultiplesteps.itemwriter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.domain.Person;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class PersonItemWriter implements ItemWriter<Person> , StepExecutionListener {

    private List<Person> personList;

    @Override
    public void write(List<? extends Person> items) throws Exception {
        log.info(items.toString());
        items.stream().forEach(System.out::println);
        personList = Optional.ofNullable(personList).orElse(new ArrayList<>());
        personList.addAll(items);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        return ;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String str = objectMapper.writeValueAsString(personList);
            stepExecution.getJobExecution().getExecutionContext().put("step2", str);
            stepExecution.getJobExecution().getExecutionContext().put("step3", str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
