package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.LibraryManager;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLibrariesController {
    //managers
    private final LibraryManager libraryManager = new LibraryManager();
    //components
    public TableView<Library> librariesList;
    public TableColumn<Library, Integer> idColumn;
    public TableColumn<Library, String> nameColumn;
    public TableColumn<Library, String> locationColumn;
    public TextField nameId;
    public TextField locationId;

    @FXML
    public void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<Library, Integer>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Library,String>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Library,String>("location"));



        refreshLibraries();

        librariesList.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            if(newSelection != null){
                Library library = (Library) newSelection;
                nameId.setText(library.getName());
                locationId.setText(library.getLocation());
            }
        });
    }

    public void addClick(ActionEvent actionEvent) {
    }

    public void updateClick(ActionEvent actionEvent) {
    }

    public void deleteClick(ActionEvent actionEvent) {
    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) librariesList.getScene().getWindow();
        AbstractController.switchScene(ns,"adminScreen.fxml","Admin Screen");
    }

    private void refreshLibraries() {
        try {
            librariesList.setItems(FXCollections.observableList(libraryManager.getAll()));
            librariesList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }
}
