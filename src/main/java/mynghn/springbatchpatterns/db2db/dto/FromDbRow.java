package mynghn.springbatchpatterns.db2db.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemCountAware;


@Getter
public class FromDbRow implements ItemCountAware {

    private final int id;

    private final String colA;
    private final String colB;
    private final String colC;

    private final LocalDateTime createdAt;

    @Builder
    public FromDbRow(int id, String colA, String colB, String colC, LocalDateTime createdAt) {
        this.id = id;
        this.colA = colA;
        this.colB = colB;
        this.colC = colC;
        this.createdAt = createdAt;
    }

    private int itemCount;

    @Override
    public void setItemCount(int count) {
        itemCount = count;
    }
}
