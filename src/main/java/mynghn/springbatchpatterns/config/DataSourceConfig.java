package mynghn.springbatchpatterns.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DataSourceConfig {

    @Bean
    public BatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer(frameworkDataSource());
    }

    @Bean
    @ConfigurationProperties("spring.datasource.framework")
    public DataSourceProperties frameworkDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.framework.hikari")
    public HikariDataSource frameworkDataSource() {
        return frameworkDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.main")
    public DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mainDataSource() {
        return mainDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSourceTransactionManager mainTransactionManager() {
        return new DataSourceTransactionManager(mainDataSource());
    }

    @Bean
    public SqlSessionFactory mainSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(mainDataSource());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.external")
    public DataSourceProperties externalDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource externalDataSource() {
        return externalDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
