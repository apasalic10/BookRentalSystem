package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    public Button rentBookButton;

    public void rentBookClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) rentBookButton.getScene().getWindow();
        AbstractController.switchScene(ns,"rentScreen.fxml","Rent book");
    }

    public void logOutClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) rentBookButton.getScene().getWindow();
        AbstractController.switchScreen(ns,"login.fxml","Login");
    }

    public void viewRentsClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) rentBookButton.getScene().getWindow();
        AbstractController.switchScene(ns,"rentList.fxml","Rent List");
    }

    public void changeProfileClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) rentBookButton.getScene().getWindow();
        AbstractController.switchScene(ns,"changeProfile.fxml","Change profile");
    }
}
