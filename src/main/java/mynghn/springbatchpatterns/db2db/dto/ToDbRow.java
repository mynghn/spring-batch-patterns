package mynghn.springbatchpatterns.db2db.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ToDbRow {

    private final int srcId;
    private final String col1;
    private final String col2;
    private final String col3;
    private final LocalDateTime srcCreatedAt;

    private final LocalDate baseDate;
    private final LocalDateTime copiedAt;
}
