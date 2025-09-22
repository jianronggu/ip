package labussy.core.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import labussy.Labussy;

/** A GUI for Duke using FXML. */
public class Main extends Application {

    private final Labussy labussy = new Labussy();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Labussy");

            // Add stylesheet if not already
            scene.getStylesheets().add(Main.class.getResource("/view/styles.css").toExternalForm());

            // Make the whole window pink
            root.getStyleClass().add("theme-pink");

            stage.show();

            // inject Duke into the controller
            MainWindow controller = fxmlLoader.getController();
            controller.setLabussy(labussy);
        } catch (IOException e) {
            // simple error print for the tutorial
            e.printStackTrace();
        }
    }
}
