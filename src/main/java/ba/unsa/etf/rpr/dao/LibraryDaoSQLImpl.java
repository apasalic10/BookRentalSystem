package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * MySQL implementation of the DAO
 * @author Almedin Pasalic
 */

public class LibraryDaoSQLImpl extends AbstractDao<Library> implements LibraryDao {

    private static LibraryDaoSQLImpl instance = null;

    public static LibraryDaoSQLImpl getInstance(){
        if(instance==null)
            instance = new LibraryDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance=null;
    }


    LibraryDaoSQLImpl() {
        super("Libraries");
    }

    @Override
    public Library row2object(ResultSet rs) throws BookException {
        try{
            Library lib  = new Library();
            lib.setId(rs.getInt("id"));
            lib.setName(rs.getString("name"));
            lib.setLocation(rs.getString("location"));

            return lib;
        }catch (SQLException e){
            throw new BookException(e.getMessage(),e);
        }
    }

    @Override
    public Map<String, Object> object2row(Library object) {
        Map<String, Object> row = new TreeMap<>();

        row.put("id", object.getId());
        row.put("name", object.getName());
        row.put("location",object.getLocation());

        return row;
    }



    @Override
    public Library getByName(String name) throws BookException {
        return this.executeQueryUnique("SELECT * FROM Libraries WHERE name = ?",name);
    }

    @Override
    public List<Library> searchByLocation(String location) throws BookException {
        return this.executeQuery("SELECT * FROM Libraries WHERE location = ?",location);
    }
}
