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
        return null;
    }

    @Override
    public Rental add(Rental item) {
        return null;
    }

    @Override
    public Rental update(Rental item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Rental> getAll() {
        return null;
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
                r.setRentalBook(null);                             //implementirati poslije
                r.setRentalMember(null);  //iimplemetacija kasnije
                rentals.add(r);
            }

            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
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
                r.setRentalBook(null);                             //implementirati poslije
                r.setRentalMember(null);  //iimplemetacija kasnije
                rentals.add(r);
            }

            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
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
                r.setRentalBook(null);                             //implementirati poslije
                r.setRentalMember(null);  //iimplemetacija kasnije
                rentals.add(r);
            }

            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return rentals;
    }
}
