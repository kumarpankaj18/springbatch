package springbatchmultiplesteps.itemprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.domain.Person;


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

        return Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

    }
}
