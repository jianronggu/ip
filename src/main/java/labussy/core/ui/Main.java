package labussy.core.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import labussy.core.Duke;

/** A GUI for Duke using FXML. */
public class Main extends Application {

    private final Duke duke = new Duke();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Labussy");
            stage.show();

            // inject Duke into the controller
            MainWindow controller = fxmlLoader.getController();
            controller.setDuke(duke);
        } catch (IOException e) {
            // simple error print for the tutorial
            e.printStackTrace();
        }
    }
}
