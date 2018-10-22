package springbatchbasic.itemreader;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import springbatchbasic.domain.Person;

@Component
public class PersonItemReader implements ItemReader<Person> {


    @Override
    public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException
    {
        String firstName = RandomStringUtils.random(10, true, false);
        String lastName = RandomStringUtils.random(6, true, false);
        return new Person(firstName, lastName);
    }


}
