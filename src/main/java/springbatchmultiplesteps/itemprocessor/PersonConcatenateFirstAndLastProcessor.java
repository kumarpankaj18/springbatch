package springbatchmultiplesteps.itemprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.domain.Person;

@Component
@Slf4j
public class PersonConcatenateFirstAndLastProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        person.setFirstLastName(person.getFirstName() + person.getLastName());
        log.info(person.toString());
        Thread.sleep(1000);
        return person;
    }
}
