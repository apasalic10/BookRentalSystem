package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class BookDaoSQLImpl implements BookDao{

    private Connection connection;

    public BookDaoSQLImpl() throws IOException{
        FileReader reader = new FileReader("db.properties");
        Properties p = new Properties();
        p.load(reader);

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/" + p.getProperty("username") , p.getProperty("username"), p.getProperty("password"));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        List<Book> books = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Books WHERE author = ?");
            statement.setString(1,author);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("name"));
                book.setBookLibrary(null);                             //implementirati poslije
                book.setBookAuthor(rs.getString("author"));
                books.add(book);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> searchByText(String text) {
        List<Book> books = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Books WHERE name LIKE concat('%' , ? , '%')");
            statement.setString(1,text);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setBookName(rs.getString("name"));
                b.setBookLibrary(null);                             //implementirati poslije
                b.setBookAuthor(rs.getString("author"));
                books.add(b);
            }

            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> searchByLibrary(Library library) {
        List<Book> books = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Books WHERE library_id = ?");
            statement.setInt(1,library.getLibraryId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Book b = new Book();

                b.setBookId(rs.getInt("book_id"));
                b.setBookName(rs.getString("name"));
                b.setBookLibrary(null);                             //implementirati poslije
                b.setBookAuthor(rs.getString("author"));
                books.add(b);
            }

            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> searchAllAvailable() {
        List<Book> books = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Books WHERE isAvailable = 0");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setBookName(rs.getString("name"));
                b.setBookLibrary(null);                             //implementirati poslije
                b.setBookAuthor(rs.getString("author"));
                books.add(b);
            }

            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Book getById(int id) {

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT  * FROM Books WHERE book_id = ? ");
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("name"));
                book.setBookLibrary(null);                             //implementirati poslije
                book.setBookAuthor(rs.getString("author"));

                return book;
            }
            else{
                System.out.println("Object not found!");
            }

            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book add(Book item) {

        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Books(book_id,name,library_id,author,isAvaliable) VALUES(?,?,?,?,?)");
            statement.setInt(1,item.getBookId());
            statement.setString(2,item.getBookName());
            statement.setInt(3,item.getBookLibrary().getLibraryId());
            statement.setString(4,item.getBookAuthor());
            statement.setInt(5,item.getIsAvailable());
            statement.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public Book update(Book item) {
        String query = "UPDATE Books SET book_id = " + item.getBookId() + ", name = " + item.getBookName() + ", library_id = " + item.getBookLibrary().getLibraryId() +
                        ",author = " + item.getBookAuthor() + ", isAvailable = " + item.getIsAvailable() + " WHERE book_id = " + item.getBookId();

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM Books WHERE book_id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(delete, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getAll() {
        String query = "SELECT * FROM Books";
        List<Book> books = new LinkedList<>();

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setBookName(rs.getString("name"));
                b.setBookLibrary(null);                             //implementirati poslije
                b.setBookAuthor(rs.getString("author"));
                books.add(b);
            }

            rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return  books;
    }
}
