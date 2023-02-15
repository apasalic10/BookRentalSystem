package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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

    public void librariesButtonClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) membersButton.getScene().getWindow();
        AbstractController.switchScene(ns,"adminLibraries.fxml","Libraries");
    }

    public void logOutClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) membersButton.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Warning");
        alert.setContentText("Do You want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get().getButtonData().isDefaultButton()){
            AbstractController.switchScreen(ns,"login.fxml","Login");
        }
    }

    public void rentalsButtonClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) membersButton.getScene().getWindow();
        AbstractController.switchScene(ns,"adminRentals.fxml","Rentals");
    }
}
