package springbatchmultiplesteps.itemwriter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import springbatchmultiplesteps.constants.Constant;
import springbatchmultiplesteps.domain.Person;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class PersonItemWriter3 extends BaseItemWriter {


    private Resource outputResource = new FileSystemResource(Constant.OUTPUT_PATH_3);

    @PostConstruct
    public void init() throws Exception {
        baseInit(outputResource, Constant.OUTPUT_PATH_3);

    }

    @Bean(name = "writer3")
    public FlatFileItemWriter<Person> writer3() {
        return writer(outputResource);
    }
}