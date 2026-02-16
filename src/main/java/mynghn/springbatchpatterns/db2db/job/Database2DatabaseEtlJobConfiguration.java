package mynghn.springbatchpatterns.db2db.job;

import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import mynghn.springbatchpatterns.tasklet.CleanUpByBaseDateTasklet;
import mynghn.springbatchpatterns.util.DateUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class Database2DatabaseEtlJobConfiguration {

    private static final String BASE_DATE_KEY = "base_date";
    private static final DateTimeFormatter BASE_DATE_FORMATTER = DateUtils.FORMATTER_yyyyMMdd;
    private static final String[] REQUIRED_KEYS = new String[]{BASE_DATE_KEY};
    private static final String CLEAN_UP_STATEMENT_ID = "mynghn.springbatchpatterns.db2db.mapper.ToDbMapper.cleanUp";
    private static final int CHUNK_SIZE = 1000;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final JobBuilderFactory jobBuilders;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final StepBuilderFactory stepBuilders;

    @Qualifier("mainTransactionManager")
    private final PlatformTransactionManager toTxManager;
    @Qualifier("mainSqlSessionFactory")
    private final SqlSessionFactory toSqlSessionFactory;

    private static JobParametersValidator buildJobParametersValidator() {
        return new DefaultJobParametersValidator(REQUIRED_KEYS, new String[]{});
    }

    @Bean
    public Job db2DbEtlJob() {
        return jobBuilders.get("db2DbEtlJob")
                .validator(buildJobParametersValidator())
                .start(cleanUpStep())
                .next(etlStep())
                .build();
    }

    @Bean
    public Step cleanUpStep() {
        return stepBuilders.get("cleanUpStep")
                .transactionManager(toTxManager)
                .tasklet(new CleanUpByBaseDateTasklet(toSqlSessionFactory, CLEAN_UP_STATEMENT_ID,
                        BASE_DATE_KEY, BASE_DATE_FORMATTER))
                .build();
    }

    @Bean
    public Step etlStep() {
        return stepBuilders.get("etlStep")
                .transactionManager(toTxManager)
                .chunk(CHUNK_SIZE)
//                .reader(fromDbReader(null))
//                .processor()
//                .writer()
//                .listener()
                .build();
    }
//
//    @Bean
//    public ItemReader<FromDbRow> fromDbReader(
//            @Value("#{jobParameters['base_date']}") String baseDate) {
//        return new MyBatisCursorItemReaderBuilder<FromDbRow>()
//                .sqlSessionFactory(fromDbSqlSessionFactory)
//                .queryId(READ_QUERY_ID)
//                .parameterValues(Map.of("baseDate", LocalDate.parse(baseDate, BASE_DATE_FORMATTER)))
//                .build();
//    }
}
