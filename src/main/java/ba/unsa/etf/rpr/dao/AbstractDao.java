package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 *
 * Abstract class that implements core DAO CRUD methods for every entity
 */
public abstract class AbstractDao <Type extends Idable> implements Dao<Type>{

    private static Connection connection = null;
    private  String tableName;

    public AbstractDao(String tableName){
        this.tableName = tableName;
        createConnection();
    }

    private static void createConnection(){
        if(connection == null){
            try{

                FileReader reader = new FileReader("db.properties");
                Properties p = new Properties();
                p.load(reader);

                connection = DriverManager.getConnection(p.getProperty("db.url") , p.getProperty("db.username"), p.getProperty("db.password"));
            }catch (SQLException | IOException e){
                System.out.println("Unable to connect to the database!");
                e.printStackTrace();

            }
            finally {
                Runtime.getRuntime().addShutdownHook(new Thread(){
                    @Override
                    public void run(){
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public abstract Type row2object(ResultSet rs) throws BookException;

    public abstract Map<String, Object> object2row(Type object);

    public Type getById(int id) throws BookException{
        String query = "SELECT * FROM " + this.tableName + " WHERE id = ?";
        return this.executeQueryUnique(query,id);
    }

    public List<Type> getAll() throws BookException{
       return this.executeQuery("SELECT * FROM " + tableName, null);
    }

    public void delete(int id) throws BookException{
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try{
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();

        }catch (SQLException e){
            if(e.getMessage().contains("The client was disconnected by the server because of inactivity.")){
                createConnection();
                delete(id);
            }
            throw new BookException(e.getMessage(), e);
        }
    }

    public Type add(Type item) throws BookException{
        Map<String, Object> row = object2row(item);
        Map.Entry<String, String> columns = prepareInsertParts(row);

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(tableName);
        builder.append(" (").append(columns.getKey()).append(") ");
        builder.append("VALUES (").append(columns.getValue()).append(")");

        try{
            PreparedStatement stmt = getConnection().prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS);

            int counter = 1;

            for (Map.Entry<String, Object> entry: row.entrySet()) {
                if (entry.getKey().equals("id")) continue;
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            item.setId(rs.getInt(1));

            return item;

        }catch (SQLException e){
            if(e.getMessage().contains("The client was disconnected by the server because of inactivity.")){
                createConnection();
                add(item);
            }
            throw new BookException(e.getMessage(), e);
        }
    }

    public Type update(Type item) throws BookException{
        Map<String, Object> row = object2row(item);
        String updateColumns = prepareUpdateParts(row);
        StringBuilder builder = new StringBuilder();

        builder.append("UPDATE ")
                .append(tableName)
                .append(" SET ")
                .append(updateColumns)
                .append(" WHERE id = ?");

        try{
            PreparedStatement stmt = getConnection().prepareStatement(builder.toString());

            int counter = 1;

            for (Map.Entry<String, Object> entry: row.entrySet()) {
                if (entry.getKey().equals("id")) continue;
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.setObject(counter, item.getId());
            stmt.executeUpdate();

            return item;

        }catch (SQLException e){
            if(e.getMessage().contains("The client was disconnected by the server because of inactivity.")){
                createConnection();
                update(item);
            }
            throw new BookException(e.getMessage(), e);
        }
    }

    public List<Type> executeQuery(String query, Object... params) throws BookException {
        try{
            PreparedStatement statement = getConnection().prepareStatement(query);
            if(params != null){
                for(int i = 1; i <= params.length; i++){
                    statement.setObject(i,params[i-1]);
                }
            }
            ResultSet rs = statement.executeQuery();
            LinkedList<Type> result = new LinkedList<>();

            while (rs.next()){
                result.add(row2object(rs));
            }

            rs.close();
            return result;
        }catch (SQLException | BookException e) {
            if(e.getMessage().contains("The client was disconnected by the server because of inactivity.")){
                createConnection();
                executeQuery(query,params);
            }
            throw new BookException(e.getMessage(), e);
        }
    }

    public Type executeQueryUnique (String query, Object... params) throws BookException {
        List<Type> result = executeQuery(query,params);

        if (result != null && result.size() == 1){
            return result.get(0);
        }else{
            throw new BookException("Object not found");
        }
    }



    /**
     * Prepare sql query for insert
     * Example: (id,name) (?,?,?)
     * @param row - the row to be inserted
     * @return map in which the query for insert was created
     */
    private Map.Entry<String, String> prepareInsertParts(Map<String, Object> row){
        StringBuilder columns = new StringBuilder();
        StringBuilder questions = new StringBuilder();

        int counter = 0;

        for (Map.Entry<String, Object> entry: row.entrySet()) {
            counter++;

            if (entry.getKey().equals("id")) continue;
            columns.append(entry.getKey());
            questions.append("?");

            if (row.size() != counter) {
                columns.append(",");
                questions.append(",");
            }
        }

        return new AbstractMap.SimpleEntry<String,String>(columns.toString(), questions.toString());
    }

    /**
     * Prepare columns for update statement
     * Example: id=?, name=?
     * @param row - the row to be updated
     * @return map in which the query for update was created
     */
    private String prepareUpdateParts(Map<String, Object> row){
        StringBuilder columns = new StringBuilder();

        int counter = 0;

        for (Map.Entry<String, Object> entry: row.entrySet()) {
            counter++;
            if (entry.getKey().equals("id")) continue; //skip update of id due where clause
            columns.append(entry.getKey()).append("= ?");
            if (row.size() != counter) {
                columns.append(",");
            }
        }

        return columns.toString();
    }

}
