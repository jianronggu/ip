package labussy.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    void toString_showsTypeAndDesc_andBlankStatusByDefault() {
        ToDo t = new ToDo("read book");
        // Expected: "[T]" + "[ ] " + description  -> "[T][ ] read book"
        assertEquals("[T][ ] read book", t.toString());
    }
}
