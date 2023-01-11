package ba.unsa.etf.rpr.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AbstractController {
    /**
     * The method is used to change scenes
     * @param previousStage - stage which is currently open
     * @param newStageName - path to open a new screen
     * @param stageTitle - title for a new screen
     */
    static void switchScreen(Stage previousStage, String newStageName, String stageTitle) throws IOException {
        FXMLLoader root = new FXMLLoader(AbstractController.class.getResource("/fxml/" + newStageName));
        Stage newStage = new Stage();
        newStage.setTitle(stageTitle);
        newStage.getIcons().add(new Image("/img/LoginScreen/redBookIcon.png"));
        newStage.setResizable(false);
        newStage.setScene(new Scene(root.load(),USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        previousStage.hide();
        newStage.show();
    }
}
