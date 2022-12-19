package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.domain.Rental;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class RentalDaoSQLImpl implements RentalDao{


    private Connection connection;

    public RentalDaoSQLImpl() throws IOException {
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
    public Rental getById(int id) {

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT  * FROM Rentals WHERE rental_id = ? ");
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                Rental r = new Rental();

                r.setRentalId(rs.getInt("rental_id"));
                r.setRentalDate(rs.getDate("rental_date"));
                r.setRentalBook(new BookDaoSQLImpl().getById(rs.getInt("book_id")));
                r.setRentalMember(new MemberDaoSQLImpl().getById(rs.getInt("member_id")));

                return r;
            }
            else{
                System.out.println("Object not found!");
            }

            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Rental add(Rental item) {

        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Rentals(rental_date,book_id,member_id) VALUES(?,?,?)");
            statement.setDate(1, (java.sql.Date) item.getRentalDate());
            statement.setInt(2,item.getRentalBook().getBookId());
            statement.setInt(3,item.getRentalMember().getMemberId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public Rental update(Rental item) {

        String query = "UPDATE Rentals SET rental_id = " + item.getRentalId() + ", rental_date = " + item.getRentalDate() +
                ", book_id = " + item.getRentalBook().getBookId() + ", member_id = " + item.getRentalMember().getMemberId() + " WHERE rental_id = " + item.getRentalId();

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM Rentals WHERE rental_id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(delete, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Rental> getAll() {

        String query = "SELECT * FROM Rentals";
        List<Rental> rentals = new LinkedList<>();

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Rental r = new Rental();

                r.setRentalId(rs.getInt("rental_id"));
                r.setRentalDate(rs.getDate("rental_date"));
                r.setRentalBook(new BookDaoSQLImpl().getById(rs.getInt("book_id")));
                r.setRentalMember(new MemberDaoSQLImpl().getById(rs.getInt("member_id")));
                rentals.add(r);
            }

            rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  rentals;
    }

    @Override
    public List<Rental> searchByBook(Book book) {
        List<Rental> rentals = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Rentals WHERE book_id = ?");
            statement.setInt(1,book.getBookId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Rental r = new Rental();

                r.setRentalId(rs.getInt("rental_id"));
                r.setRentalDate(rs.getDate("rental_date"));
                r.setRentalBook(new BookDaoSQLImpl().getById(rs.getInt("book_id")));
                r.setRentalMember(new MemberDaoSQLImpl().getById(rs.getInt("member_id")));
                rentals.add(r);
            }

            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rentals;
    }

    @Override
    public List<Rental> searchByMember(Member member) {
        List<Rental> rentals = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Rentals WHERE member_id = ?");
            statement.setInt(1, member.getMemberId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Rental r = new Rental();

                r.setRentalId(rs.getInt("rental_id"));
                r.setRentalDate(rs.getDate("rental_date"));
                r.setRentalBook(new BookDaoSQLImpl().getById(rs.getInt("book_id")));
                r.setRentalMember(new MemberDaoSQLImpl().getById(rs.getInt("member_id")));
                rentals.add(r);
            }

            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rentals;
    }

    @Override
    public List<Rental> searchByDate(Date date) {
        List<Rental> rentals = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Rentals WHERE rental_date = ?");
            statement.setDate(1, (java.sql.Date) date);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Rental r = new Rental();

                r.setRentalId(rs.getInt("rental_id"));
                r.setRentalDate(rs.getDate("rental_date"));
                r.setRentalBook(new BookDaoSQLImpl().getById(rs.getInt("book_id")));
                r.setRentalMember(new MemberDaoSQLImpl().getById(rs.getInt("member_id")));
                rentals.add(r);
            }

            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rentals;
    }
}
