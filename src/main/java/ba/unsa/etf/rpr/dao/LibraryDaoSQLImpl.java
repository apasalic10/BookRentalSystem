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

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT  * FROM Libraries WHERE library_id = ? ");
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                Library lib = new Library();
                lib.setLibraryId(rs.getInt("library_id"));
                lib.setName(rs.getString("name"));
                lib.setLocation(rs.getString("location"));
                return lib;
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
    public Library add(Library item) {
        return null;
    }

    @Override
    public Library update(Library item) {
        return null;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM Libraries WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(delete, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
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

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Library> searchByLocation(String location) {
        List<Library> libraries = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Libraries WHERE location = ?");
            statement.setString(1,location);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Library lib = new Library();
                lib.setLibraryId(rs.getInt("library_id"));
                lib.setName(rs.getString("name"));
                lib.setLocation(rs.getString("location"));

                libraries.add(lib);
            }

            rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return libraries;
    }
}
