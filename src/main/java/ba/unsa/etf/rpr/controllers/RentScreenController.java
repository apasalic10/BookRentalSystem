package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.BookManager;
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

public class RentScreenController {

    public TableColumn<Book,String> nameColumn;
    public TableColumn<Book,Library> libraryColumn;
    public TableColumn<Book,String> authorColumn;

    //managers
    BookManager bookManager = new BookManager();


    //components
    public TableView<Book> bookList;
    public TextField searchField;

    String username;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("bookName"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("bookAuthor"));
        libraryColumn.setCellValueFactory(new PropertyValueFactory<Book, Library>("bookLibrary"));
        refreshBooks();

    }
    public void rentButtonAction(ActionEvent actionEvent) {
        Book b = (Book) bookList.getSelectionModel().getSelectedItem();
        System.out.println(b);
        System.out.println(LoginController.getUsername());
    }

    private void refreshBooks() {
        try {
            bookList.setItems(FXCollections.observableList(bookManager.searchAllAvailable()));
            bookList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }


    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) searchField.getScene().getWindow();
        AbstractController.switchScreen(ns,"home.fxml","Home");
    }


}
