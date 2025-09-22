package labussy.core.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import labussy.Labussy;

import java.io.InputStream;

/**
 * JavaFX controller for the main chat window: handles input, displays dialog boxes and exits gracefully on 'bye'.
 */

public class MainWindow {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;

    private Labussy labussy;

    // Lazy-loaded in initialize(); keep null-safe
    private Image userImage;
    private Image dukeImage;

    /**
     * FXML callback after field injection. Binds scrolling, loads avatars and styles the input bar.
     */

    @FXML
    public void initialize() {
        // Always scroll to newest message
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Make chat fill width so bubbles wrap on resize
        dialogContainer.setFillWidth(true);
        dialogContainer.getStyleClass().add("chat");
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(12));

        // Load avatars (try multiple common names), but don't crash if missing
        userImage = loadImageOrNull("/images/User.png", "/images/DaUser.png");
        dukeImage = loadImageOrNull("/images/Duke.png", "/images/DaDuke.png");
        // If both are null, DialogBox will simply show no avatar (that's OK)
    }

    /**
     * Injects the legacy application backend for compatibility.
     * @param app parameter
     */

    public void setLabussy(Labussy app) {
        this.labussy = app;
        // Optionally show a welcome message:
        // dialogContainer.getChildren().add(DialogBox.getDukeDialog(new Ui().msgWelcome(), dukeImage));
    }

    /** Back-compat if older code still calls setDuke(...) */
    public void setDuke(Labussy app) { setLabussy(app); }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null) return;
        input = input.trim();
        if (input.isEmpty()) return;

        String response = labussy.getResponse(input).trim();
        boolean isError = response.startsWith("☹") || response.toLowerCase().contains("oops");

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                isError
                        ? DialogBox.getDukeErrorDialog(response, dukeImage)
                        : DialogBox.getDukeDialog(response, dukeImage)
        );

        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            PauseTransition delay = new PauseTransition(Duration.millis(400));
            delay.setOnFinished(e -> Platform.exit());
            delay.play();
        }
    }

    /** Tries candidates in order; returns first found Image or null (never throws). */
    private Image loadImageOrNull(String... candidates) {
        for (String path : candidates) {
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                try {
                    return new Image(is);
                } catch (Exception ignored) {
                    // corrupted image; try next candidate
                }
            }
        }
        return null;
    }
}
