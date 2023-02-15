package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HomeController {
    public Button rentBookButton;

    public void rentBookClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) rentBookButton.getScene().getWindow();
        AbstractController.switchScene(ns,"rentScreen.fxml","Rent book");
    }

    public void logOutClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) rentBookButton.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Warning");
        alert.setContentText("Do You want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get().getButtonData().isDefaultButton()){
            AbstractController.switchScreen(ns,"login.fxml","Login");
        }
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
