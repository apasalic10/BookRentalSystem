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
    private static BookDaoSQLImpl instance = null;

    public static BookDaoSQLImpl getInstance(){
        if(instance==null)
            instance = new BookDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance=null;
    }


    public BookDaoSQLImpl() {
        super("Books");
    }


    @Override
    public List<Book> searchByAuthor(String author) throws BookException {
        return this.executeQuery("SELECT * FROM Books WHERE author = ?", author);
    }

    @Override
    public List<Book> searchByText(String text) throws BookException {
        return this.executeQuery("SELECT * FROM Books WHERE name LIKE concat('%' , ? , '%') OR author LIKE concat('%' , ? , '%')", text,text);
    }

    @Override
    public List<Book> searchByLibrary(Library library) throws BookException {
        return this.executeQuery("SELECT * FROM Books WHERE library_id = ?",library.getId());
    }

    @Override
    public List<Book> searchAllAvailable() throws BookException {
        return this.executeQuery("SELECT * FROM Books WHERE isAvailable = 1",null);
    }

    @Override
    public Book getByName(String name) throws BookException {
        return this.executeQueryUnique("SELECT * FROM Books WHERE name = ?",name);
    }

    @Override
    public void setAvailableOnFalse(int id) throws BookException {
        try{
            PreparedStatement statement = this.getConnection().prepareStatement("UPDATE Books SET isAvailable = 0 WHERE id = ?");
            statement.setInt(1,id);
            statement.executeUpdate();

        } catch (SQLException e){
            throw new BookException(e.getMessage(),e);
        }
    }

    @Override
    public void setAvailableOnTrue(int id) throws BookException {
        try{
            PreparedStatement statement = this.getConnection().prepareStatement("UPDATE Books SET isAvailable = 1 WHERE id = ?");
            statement.setInt(1,id);
            statement.executeUpdate();

        } catch (SQLException e){
            throw new BookException(e.getMessage(),e);
        }
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
