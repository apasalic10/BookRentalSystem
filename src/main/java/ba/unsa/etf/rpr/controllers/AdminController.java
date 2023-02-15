package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    //components
    public Button membersButton;

    public void membersButtonClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) membersButton.getScene().getWindow();
        AbstractController.switchScene(ns,"adminMembersScreen.fxml","Members");
    }

    public void booksButtonClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) membersButton.getScene().getWindow();
        AbstractController.switchScene(ns,"adminBook.fxml","Books");
    }

    public void librariesButtonClick(ActionEvent actionEvent) {
    }

    public void logOutClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) membersButton.getScene().getWindow();
        AbstractController.switchScreen(ns,"login.fxml","Login");
    }

    public void rentalsButtonClick(ActionEvent actionEvent) {
    }
}
