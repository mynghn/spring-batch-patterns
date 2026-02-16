package mynghn.springbatchpatterns;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableBatchProcessing
public class BatchTestConfig {

    @Bean
    @Profile("test")
    public BatchConfigurer testBatchConfigurer() {
        return new DefaultBatchConfigurer(testFrameworkDataSource());
    }

    @Bean
    @Profile("test")
    @ConfigurationProperties("spring.datasource.test-framework")
    public DataSourceProperties testFrameworkDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Profile("test")
    public DataSource testFrameworkDataSource() {
        return testFrameworkDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Profile("test")
    @ConfigurationProperties("spring.datasource.test-main")
    public DataSourceProperties testMainDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Profile("test")
    public DataSource testMainDataSource() {
        return testMainDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Profile("test")
    @ConfigurationProperties("spring.datasource.test-external")
    public DataSourceProperties testExternalDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Profile("test")
    public DataSource testExternalDataSource() {
        return testExternalDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
