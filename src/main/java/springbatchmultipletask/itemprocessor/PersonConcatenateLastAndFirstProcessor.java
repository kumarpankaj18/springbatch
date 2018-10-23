package springbatchmultipletask.itemprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import springbatchmultipletask.domain.Person;

@Component
@Slf4j
public class PersonConcatenateLastAndFirstProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {

        person.setLastFirstName(person.getLastName() + person.getFirstName());
        log.info(person.toString());
        Thread.sleep(1000);
        return person;
    }
}
