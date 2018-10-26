package springbatchmultiplesteps.itemreader;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.constants.Constant;
import springbatchmultiplesteps.domain.Person;
import springbatchmultiplesteps.itemwriter.BaseItemWriter;

@Component
public class Step1Reader extends BaseItemReader{


        Resource inputResource =  new FileSystemResource(Constant.INPUT_PATH_1);

        @Bean(name = "reader")
        public SynchronizedItemStreamReader<Person> reader() {
            return getReader(inputResource, new String[] { "rowNumber", "firstName", "lastName" });
        }
}
