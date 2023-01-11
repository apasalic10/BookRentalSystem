package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    public GridPane signUpScreen;

    public void cancelClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) signUpScreen.getScene().getWindow();
        AbstractController.switchScreen(ns,"login.fxml","Login");
    }

}
