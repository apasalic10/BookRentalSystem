package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class BookDaoSQLImpl implements BookDao{

    private Connection connection;

    public BookDaoSQLImpl(){
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return null;
    }

    @Override
    public List<Book> searchByText(String text) {
        return null;
    }

    @Override
    public List<Book> searchByLibrary(Library library) {
        return null;
    }

    @Override
    public Book getById(int id) {
        return null;
    }

    @Override
    public Book add(Book item) {
        return null;
    }

    @Override
    public Book update(Book item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Book> getAll() {
        return null;
    }
}
