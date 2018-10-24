package springbatchparallelchunks.itemprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import springbatchparallelchunks.domain.Person;


@Slf4j
@Component
public class PersonToUpperProcessor implements ItemProcessor<Person,Person> {

    @Override
    public Person process(Person person) throws Exception
    {
        if(person == null) {
            throw new Exception();
        }
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        person.setLastFirstName(lastName);
        person.setFirstLastName(firstName);

        return person;
    }
}
