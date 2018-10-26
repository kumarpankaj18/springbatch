package springbatchmultiplesteps.itemprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.domain.Person;

@Component
@Slf4j
public class PersonConcatenateLastAndFirstProcessor implements ItemProcessor<Person, Person> {

    static  int i =0;
    @Override
    public Person process(Person person) throws Exception {

//        if(person.getRowNumber() == 4){
//            log.info("retry processing {}", person);
//            throw  new Exception();
//        }

        person.setLastFirstName(person.getLastName() + person.getFirstName());
        log.info(person.toString());
        //Thread.sleep(1000);
        return person;
    }
}
