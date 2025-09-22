package labussy.core.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DialogBox.fxml", e);
        }

        dialog.setText(text);
        dialog.setWrapText(true);
        displayPicture.setImage(img);

        // Circular avatar clip sized from fitWidth/fitHeight
        if (displayPicture != null) {
            Circle clip = new Circle();
            clip.radiusProperty().bind(displayPicture.fitWidthProperty().divide(2));
            clip.centerXProperty().bind(displayPicture.fitWidthProperty().divide(2));
            clip.centerYProperty().bind(displayPicture.fitHeightProperty().divide(2));
            displayPicture.setClip(clip);
        }
    }

    /** Bot (left) bubble: avatar then text. */
    public static DialogBox getDukeDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.setAlignment(Pos.TOP_LEFT);
        db.styleAs("duke");
        db.setChildrenOrder(db.displayPicture, db.dialog); // SAFE reorder
        return db;
    }

    /** Bot error bubble: same as duke + error class. */
    public static DialogBox getDukeErrorDialog(String text, Image img) {
        DialogBox db = getDukeDialog(text, img);
        db.dialog.getStyleClass().add("error");
        return db;
    }

    /** User (right) bubble: text then avatar. */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.setAlignment(Pos.TOP_RIGHT);
        db.styleAs("user");
        db.setChildrenOrder(db.dialog, db.displayPicture); // SAFE reorder
        return db;
    }

    private void styleAs(String role) {
        List<String> clz = dialog.getStyleClass();
        if (!clz.contains("bubble")) clz.add("bubble");
        if (!clz.contains(role)) clz.add(role);
    }

    /** Safe reorder (no in-place reverse). */
    private void setChildrenOrder(Node first, Node second) {
        ArrayList<Node> order = new ArrayList<>(2);
        order.add(first);
        order.add(second);
        getChildren().setAll(order);
    }
}
