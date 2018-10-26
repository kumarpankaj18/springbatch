package springbatchmultiplesteps.job;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.JDBCType;

@Configuration
@EnableBatchProcessing
public class BatchInfraStructure   {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }


    @Bean
    public DataSource dataSource(Environment environment) {

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setPassword(null);
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);


        return dataSource;
    }
}
