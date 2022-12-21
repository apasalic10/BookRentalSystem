package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractDao <Type extends Idable> implements Dao<Type>{

    private Connection connection;
    private  String tableName;

    private AbstractDao(String tableName){
        try{
            this.tableName = tableName;
            FileReader reader = new FileReader("db.properties");
            Properties p = new Properties();
            p.load(reader);

            this.connection = DriverManager.getConnection(p.getProperty("db.url") , p.getProperty("db.username"), p.getProperty("db.password"));
        }catch (SQLException | IOException e){
            System.out.println("Unable to connect to the database!");
            e.printStackTrace();

        }
    }

    public Connection getConnection(){
        return connection;
    }

    public abstract Type row2object(ResultSet rs);

    public abstract Map<String, Object> object2row(Type object);

    public Type getById(int id) throws RuntimeException{
        String query = "SELECT * FROM " + this.tableName + " WHERE id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Type result = row2object(rs);
                rs.close();
                return result;

            } else {
                throw new RuntimeException("Object not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Type> getAll(){
        String query = "SELECT * FROM "+ tableName;
        List<Type> results = new LinkedList<>();

        try{
            PreparedStatement stmt = getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Type object = row2object(rs);
                results.add(object);
            }
            rs.close();
            return results;

        }catch (SQLException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
