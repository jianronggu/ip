package labussy.task;
import labussy.time.Dates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    void toString_showsTypeAndDesc_andBlankStatusByDefault() {
        Deadline d = new Deadline("homework", new Dates("2025-09-01 1800"));
        // Expected: "[T]" + "[ ] " + description  -> "[T][ ] read book"
        assertEquals("[D][ ] homework (by: Sep 1 2025 6:00 pm)", d.toString());
    }
}
