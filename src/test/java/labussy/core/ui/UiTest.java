package labussy.core.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UiTest {

    @Test
    void msgError_formatsWithPrefixAndFallback() {
        Ui ui = new Ui();

        // 1) Includes the standard prefix and the provided detail (trimmed)
        String detailed = ui.msgError("invalid task number");
        assertTrue(detailed.startsWith("☹ BEW BEW!!! "), "Should start with the OOPS prefix");
        assertTrue(detailed.contains("invalid task number"), "Should include the detail message");

        // 2) Falls back to a generic message if detail is blank
        String fallback = ui.msgError("   ");
        assertEquals("☹ BEW BEW!!! Something went wrong.", fallback,
                "Blank detail should use the generic fallback message");
    }
}
