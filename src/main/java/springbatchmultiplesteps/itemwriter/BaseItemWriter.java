package springbatchmultiplesteps.itemwriter;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.Resource;
import springbatchmultiplesteps.constants.Constant;
import springbatchmultiplesteps.domain.Person;

import java.io.File;

public class BaseItemWriter {

    public static FlatFileItemWriter<Person> writer(Resource outputResource) {
        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(outputResource);


        writer.setAppendAllowed(true);

        writer.setLineAggregator(new DelimitedLineAggregator<Person>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Person>() {
                    {
                        setNames(new String[]{"rowNumber", "firstName", "lastName", "firstLastName", "lastFirstName"});
                    }
                });
            }
        });
        return writer;
    }

    public static void baseInit(Resource outputResource, String outputPath) throws Exception{
        if(outputResource.exists()) {
//            File file = outputResource.getFile();
//            file.delete();
//            file.createNewFile();
        } else {
            File file = new File(outputPath);
            file.createNewFile();
        }

    }
}
