package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.LibraryManager;
import ba.unsa.etf.rpr.business.RentalManager;
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

public class AdminBookController {
    //managers
    private final BookManager bookManager = new BookManager();
    private final LibraryManager libraryManager = new LibraryManager();
    private final RentalManager rentalManager = new RentalManager();
    //components
    public TableView<Book> bookList;
    public TableColumn<Book, Integer> idColumn;
    public TableColumn<Book, String> nameColumn;
    public TableColumn<Book, Library> libraryColumn;
    public TableColumn<Book, String> authorColumn;
    public TableColumn<Book, Integer> isAvailableColumn;
    public TextField nameId;
    public TextField libraryId;
    public TextField authorId;




    @FXML
    public void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("bookName"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("bookAuthor"));
        libraryColumn.setCellValueFactory(new PropertyValueFactory<Book, Library>("bookLibrary"));


        refreshBooks();

        bookList.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            if(newSelection != null){
                Book book = (Book) newSelection;
                nameId.setText(book.getBookName());
                libraryId.setText(book.getBookLibrary().getName());
                authorId.setText(book.getBookAuthor());
            }
        });
    }

    private void refreshBooks() {
        try {
            bookList.setItems(FXCollections.observableList(bookManager.getAll()));
            bookList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }

    public void addClick(ActionEvent actionEvent) throws BookException {
        List<Library> libraries = libraryManager.getAll();
        List<Book> books = bookManager.getAll();

        if(checkLibrary(libraries,libraryId.getText()) && checkName(books,nameId.getText())){
            bookManager.add(createBookObject(nameId.getText(),libraryId.getText(),authorId.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have successfully added the book!");
            alert.setHeaderText(null);
            alert.show();

            refreshBooks();
            return;
        }


        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Input is not correct!");
        alert.setHeaderText(null);
        alert.show();

    }

    public void updateClick(ActionEvent actionEvent) throws BookException {
        List<Library> libraries = libraryManager.getAll();
        List<Book> books = bookManager.getAll();


        Book b = (Book) bookList.getSelectionModel().getSelectedItem();

        if(checkLibrary(libraries,libraryId.getText()) && checkNameForUpdate(books,nameId.getText(),b.getBookName())){
            Book book = new Book();
            book.setId(b.getId());
            book.setBookName(nameId.getText());
            book.setBookLibrary(libraryManager.getByName(libraryId.getText()));
            book.setBookAuthor(authorId.getText());
            book.setIsAvailable(b.getIsAvailable());
            bookManager.update(book);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have successfully updated the book!");
            alert.setHeaderText(null);
            alert.show();

            refreshBooks();
            return;
        }


        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Input is not correct!");
        alert.setHeaderText(null);
        alert.show();

    }

    public void deleteClick(ActionEvent actionEvent) throws BookException {
        Book b = (Book) bookList.getSelectionModel().getSelectedItem();

        if(b != null && b.getBookName().equals(nameId.getText()) && b.getBookAuthor().equals(authorId.getText()) && b.getBookLibrary().getName().equals(libraryId.getText())){
            for(Rental r : rentalManager.searchByBook(b)){
                rentalManager.delete(r.getId());
            }
            bookManager.delete(b.getId());

            refreshBooks();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The fields are not correct!");
            alert.setHeaderText(null);
            alert.show();
        }

    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) bookList.getScene().getWindow();
        AbstractController.switchScene(ns,"adminScreen.fxml","Admin Screen");

    }

    private boolean checkName(List<Book> list, String newName){
        for(Book b : list){
            if(b.getBookName().equals(newName)){
                return false;
            }
        }

        return true;
    }

    private boolean checkNameForUpdate(List<Book> list, String newName,String currentName){
        for(Book b : list){
            if(b.getBookName().equals(newName) && !b.getBookName().equals(currentName)){
                return false;
            }
        }

        return true;
    }



    private boolean checkLibrary(List<Library> list, String newName){
        for(Library b : list){
            if(b.getName().equals(newName)){
                return true;
            }
        }

        return false;
    }

    private Book createBookObject(String bookName, String libraryName, String authorName) throws BookException {
        Book book = new Book();


        book.setBookName(bookName);
        book.setBookLibrary(libraryManager.getByName(libraryName));
        book.setBookAuthor(authorName);
        book.setIsAvailable(1);


        return book;
    }

}
