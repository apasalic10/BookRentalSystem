package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.domain.Rental;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
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
        return null;
    }

    @Override
    public List<Rental> searchByMember(Member member) {
        return null;
    }

    @Override
    public List<Rental> searchByDate(Date date) {
        return null;
    }
}
