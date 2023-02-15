package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.RentalManager;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Rental;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class AdminRentalsController {
    //managers
    private final RentalManager rentalManager = new RentalManager();
    private final BookManager bookManager = new BookManager();
    //components
    public TableView<Rental> rentalList;
    public TableColumn<Rental, Integer> idColumn;
    public TableColumn<Rental, String> memberColumn;
    public TableColumn<Rental, Date> dateColumn;
    public TableColumn<Rental, String> bookColumn;

    @FXML
    public void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<Rental, Integer>("Id"));
        memberColumn.setCellValueFactory(new PropertyValueFactory<Rental,String>("rentalMember"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Rental,Date>("rentalDate"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<Rental,String>("rentalBook"));

        refreshRentals();

    }

    public void deleteClick(ActionEvent actionEvent) throws BookException {
        Rental rental = (Rental) rentalList.getSelectionModel().getSelectedItem();

        if(rental != null){
            bookManager.setAvailableOnTrue(rental.getRentalBook().getId());
            rentalManager.delete(rental.getId());

            refreshRentals();
        }
    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) rentalList.getScene().getWindow();
        AbstractController.switchScene(ns,"adminScreen.fxml","Admin Screen");
    }

    private void refreshRentals() {
        try {
            rentalList.setItems(FXCollections.observableList(rentalManager.getAll()));
            rentalList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }
}
