package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.LibraryManager;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Rental;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminLibrariesController {
    //managers
    private final LibraryManager libraryManager = new LibraryManager();
    private final BookManager bookManager = new BookManager();
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

    public void addClick(ActionEvent actionEvent) throws BookException {
        if(checkNameForAdd(libraryManager.getAll(),nameId.getText(),locationId.getText()) && !nameId.getText().isEmpty() && !locationId.getText().isEmpty()){
            libraryManager.add(createLibraryObject(nameId.getText(),locationId.getText()));
            refreshLibraries();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have successfully added the library!");
            alert.setHeaderText(null);
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Input is not correct!");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    public void updateClick(ActionEvent actionEvent) throws BookException {
        Library lib = (Library) librariesList.getSelectionModel().getSelectedItem();

        if(checkNameForUpdate(libraryManager.getAll(),nameId.getText(),lib.getName()) && !nameId.getText().isEmpty() && !locationId.getText().isEmpty()){
            Library l = new Library();
            l.setId(lib.getId());
            l.setName(nameId.getText());
            l.setLocation(locationId.getText());

            libraryManager.update(l);
            refreshLibraries();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have successfully updated the library!");
            alert.setHeaderText(null);
            alert.show();


        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Input is not correct!");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    public void deleteClick(ActionEvent actionEvent) throws BookException {
        Library lib = (Library) librariesList.getSelectionModel().getSelectedItem();

        if(lib != null && lib.getName().equals(nameId.getText()) && lib.getLocation().equals(locationId.getText())){
            for(Book b : bookManager.searchByLibrary(lib)){
                bookManager.delete(b.getId());
            }

            libraryManager.delete(lib.getId());
            refreshLibraries();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The fields are not correct!");
            alert.setHeaderText(null);
            alert.show();
        }

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

    private boolean checkNameForAdd(List<Library> list, String name, String location){
        for(Library lib : list){
            if(lib.getName().equals(name) && lib.getLocation().equals(location)){
                return false;
            }
        }

        return true;
    }

    private boolean checkNameForUpdate(List<Library> list, String name, String currentName){
        for(Library lib : list){
            if(lib.getName().equals(name) && !lib.getName().equals(currentName)){
                return false;
            }
        }

        return true;
    }

    private Library createLibraryObject(String name, String location){
        Library lib = new Library();
        lib.setName(name);
        lib.setLocation(location);

        return lib;
    }
}
