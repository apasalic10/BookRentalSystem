package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.MemberManager;
import ba.unsa.etf.rpr.business.RentalManager;
import ba.unsa.etf.rpr.dao.DaoFactory;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RentListController {
    //managers
    private final RentalManager rentalManager = new RentalManager();
    private final MemberManager memberManager = new MemberManager();
    private final BookManager bookManager = new BookManager();
    //components
    public TableView<Book> rentList;
    public TableColumn<Book,String> nameColumn;
    public TableColumn<Book, Library> libraryColumn;
    public TableColumn<Book, String> authorColumn;
    public Button backButton;
    public Button returnBookButton;


    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("bookName"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("bookAuthor"));
        libraryColumn.setCellValueFactory(new PropertyValueFactory<Book, Library>("bookLibrary"));
        refreshRentList();
    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) backButton.getScene().getWindow();
        AbstractController.switchScene(ns,"home.fxml","Home");
    }

    public void returnBookClick(ActionEvent actionEvent) throws BookException {
        Book b = (Book) rentList.getSelectionModel().getSelectedItem();

        if(b != null){

            Book newbook = DaoFactory.bookDao().getByName(b.getBookName());
            bookManager.setAvailableOnTrue(newbook.getId());

            for(Rental r : rentalManager.searchByBook(newbook)){
                rentalManager.delete(r.getId());
            }
            
            refreshRentList();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have successfully returned the book!");
            alert.setHeaderText(null);
            alert.show();

        }
    }

    private void refreshRentList(){
        try {
            rentList.setItems(FXCollections.observableList(createBookListByRent()));
            rentList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }

    private List<Book> createBookListByRent() throws BookException {
        List<Book> bookList = new LinkedList<>();

        for(Rental r : rentalManager.searchByMember(memberManager.getByUsername(LoginController.getUsername()))){
            bookList.add(bookManager.getById(r.getRentalBook().getId()));
        }

        return  bookList;
    }
}
