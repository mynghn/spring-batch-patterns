package mynghn.springbatchpatterns.db2db.job;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import mynghn.springbatchpatterns.BatchTestConfig;
import mynghn.springbatchpatterns.db2db.dto.FromDbRow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBatchTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {BatchTestConfig.class, Database2DatabaseEtlJobConfiguration.class})
class Database2DatabaseEtlJobConfigurationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void testCleanUpStep() {
        JobExecution cleanUpStepExecution = jobLauncherTestUtils.launchStep("cleanUpStep");

        assertThat(cleanUpStepExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }
}
