package labussy.core.ui;

import javafx.application.Application;

/**
 * Compatibility launcher that calls Application.launch to work around classpath/gradle issues.
 */

public class Launcher {
    /**
     * Starts the application from the command line.
     * @param args parameter
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}