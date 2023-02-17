package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.MemberManager;
import ba.unsa.etf.rpr.business.RentalManager;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HomeController {

    private static RentalManager rentalManager = new RentalManager();
    private static MemberManager memberManager = new MemberManager();
    private static BookManager bookManager = new BookManager();
    public Button rentBookButton;
    public Button viewRentsButton;
    public Button izvjestajButtonId;

    @FXML
    public void initialize() throws BookException {
        viewRentsButton.setText("View Rents" + " (" + rentalManager.searchByMember(memberManager.getByUsername(LoginController.getUsername())).size() + ")");
        rentBookButton.setText("Rent Book" + " (" + bookManager.searchAllAvailable().size() + ")");
    }

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

    public void izvjestajClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) rentBookButton.getScene().getWindow();
        AbstractController.switchScene(ns,"izvjestaj.fxml","Izvjestaj");
    }
}
