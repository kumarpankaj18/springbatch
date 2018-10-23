package springbatchmultipletaskparallel.itemprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import springbatchmultipletaskparallel.domain.Person;

@Component
@Slf4j
public class PersonConcatenateFirstAndLastProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        person.setFirstLastName(person.getFirstName() + person.getLastName());
        Thread.sleep(1000);
        log.info(person.toString());
        return person;
    }
}
