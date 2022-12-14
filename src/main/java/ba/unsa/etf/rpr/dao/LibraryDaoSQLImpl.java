package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class LibraryDaoSQLImpl implements LibraryDao {

    private Connection connection;

    public LibraryDaoSQLImpl() throws IOException {
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
    public Library getById(int id) {
        return null;
    }

    @Override
    public Library add(Library item) {
        return null;
    }

    @Override
    public Library update(Library item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Library> getAll() {
        return null;
    }

    @Override
    public Library getByName(String name) {

        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Libraries WHERE name = ?");
            statement.setString(1,name);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                Library lib = new Library();
                lib.setLibraryId(rs.getInt("library_id"));
                lib.setName(rs.getString("name"));
                lib.setLocation(rs.getString("location"));
                return lib;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Library> searchByLocation(String location) {
        return null;
    }
}
