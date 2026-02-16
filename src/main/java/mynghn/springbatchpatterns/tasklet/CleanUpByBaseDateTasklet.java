package mynghn.springbatchpatterns.tasklet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mynghn.springbatchpatterns.util.DateUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
@RequiredArgsConstructor
public class CleanUpByBaseDateTasklet implements Tasklet {

    private final SqlSessionFactory sqlSessionFactory;
    private final String deleteStatementId;

    private final String baseDateJobParameterKey;
    private final DateTimeFormatter baseDateFormatter;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int deleted = session.delete(deleteStatementId,
                    Map.of("baseDate", getBaseDate(chunkContext)));

            log.info("Deleted {} records.", deleted);
        }

        return RepeatStatus.FINISHED;
    }

    private LocalDate getBaseDate(ChunkContext chunkContext) {
        String baseDateFromJobParams = chunkContext.getStepContext().getStepExecution()
                .getJobExecution().getExecutionContext().getString(baseDateJobParameterKey);
        return LocalDate.parse(baseDateFromJobParams, baseDateFormatter);
    }
}
