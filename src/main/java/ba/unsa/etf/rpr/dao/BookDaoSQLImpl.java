package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * MySQL's implementation of the DAO
 * @author Almedin Pasalic
 */
public class BookDaoSQLImpl extends AbstractDao<Book> implements BookDao {


    public BookDaoSQLImpl() {
        super("Books");
    }

    @Override
    public List<Book> searchByAuthor(String author) throws BookException {
        List<Book> books = new LinkedList<>();

        try {
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM Books WHERE author = ?");
            statement.setString(1, author);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                books.add(row2object(rs));
            }

            rs.close();
        } catch (SQLException | BookException e) {
            throw new BookException(e.getMessage(),e);
        }

        return books;
    }

    @Override
    public List<Book> searchByText(String text) throws BookException {
        List<Book> books = new LinkedList<>();

        try {
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM Books WHERE name LIKE concat('%' , ? , '%')");
            statement.setString(1, text);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                books.add(row2object(rs));
            }

            rs.close();
        } catch (SQLException | BookException e) {
            throw new BookException(e.getMessage(),e);
        }

        return books;
    }

    @Override
    public List<Book> searchByLibrary(Library library) throws BookException {
        List<Book> books = new LinkedList<>();

        try {
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM Books WHERE library_id = ?");
            statement.setInt(1, library.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                books.add(row2object(rs));
            }

            rs.close();
        } catch (SQLException | BookException e) {
            throw new BookException(e.getMessage(),e);
        }

        return books;
    }

    @Override
    public List<Book> searchAllAvailable() throws BookException {
        List<Book> books = new LinkedList<>();

        try {
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM Books WHERE isAvailable = 1");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                books.add(row2object(rs));
            }

            rs.close();
        } catch (SQLException | BookException e) {
            throw new BookException(e.getMessage(),e);
        }

        return books;
    }

    @Override
    public Book getByName(String name) throws BookException {
        Book book = new Book();
        try{
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM Books WHERE name = ?");
            statement.setString(1,name);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                book = row2object(rs);
                rs.close();
            }

        }catch (SQLException e){
            throw new BookException(e.getMessage(),e);
        }

        return book;
    }

    @Override
    public Book row2object(ResultSet rs) throws BookException {
        try {
            Book b = new Book();
            b.setId(rs.getInt("id"));
            b.setBookName(rs.getString("name"));
            b.setBookLibrary(DaoFactory.libraryDao().getById(rs.getInt("library_id")));
            b.setBookAuthor(rs.getString("author"));

            return b;
        } catch (SQLException e) {
            throw new BookException(e.getMessage(),e);
        }
    }

    @Override
    public Map<String, Object> object2row(Book object) {
        Map<String, Object> row = new TreeMap<>();

        row.put("id", object.getId());
        row.put("name", object.getBookName());
        row.put("library_id",object.getBookLibrary().getId());
        row.put("author",object.getBookAuthor());
        row.put("isAvailable",object.getIsAvailable());

        return row;
    }
}
