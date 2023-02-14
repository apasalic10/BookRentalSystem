package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.domain.Rental;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

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

        searchField.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.equals("")){
                refreshBooks();
            }
            else{
                refreshBookByText(newValue);
            }
        });

    }
    public void rentButtonAction(ActionEvent actionEvent) throws BookException {
        Book b = (Book) bookList.getSelectionModel().getSelectedItem();

        if(b != null){
            Book newbook = DaoFactory.bookDao().getByName(b.getBookName());
            Date d = new Date(System.currentTimeMillis());

            DaoFactory.rentalDao().add(createRentalObject(d,newbook,DaoFactory.memberDao().getByUsername(LoginController.getUsername())));
            DaoFactory.bookDao().setAvailableOnFalse(newbook.getId());
            refreshBooks();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have successfully rented the book!");
            alert.setHeaderText(null);
            alert.show();

        }
    }

    private void refreshBooks() {
        try {
            bookList.setItems(FXCollections.observableList(bookManager.searchAllAvailable()));
            bookList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void refreshBookByText(String text){
        try {
            bookList.setItems(FXCollections.observableList(bookManager.searchByText(text)));
            bookList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }


    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) searchField.getScene().getWindow();
        AbstractController.switchScene(ns,"home.fxml","Home");
    }

    private Rental createRentalObject(Date rentalDate, Book b, Member m){
        Rental rent = new Rental();

        rent.setRentalDate(rentalDate);
        rent.setRentalBook(b);
        rent.setRentalMember(m);

        return rent;
    }


}
