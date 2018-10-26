package springbatchmultiplesteps.itemreader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import springbatchmultiplesteps.domain.Person;

public class BaseItemReader {

    public SynchronizedItemStreamReader<Person> getReader(Resource inputResource, String [] headers){
        SynchronizedItemStreamReader<Person> temp = new SynchronizedItemStreamReader<>();

        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        temp.setDelegate(reader);
        reader.setResource(inputResource);
        reader.setLineMapper(new DefaultLineMapper() {
            {

                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(headers);
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
                    {
                        setTargetType(Person.class);
                    }
                });
            }
        });
        return temp;
    }
}
