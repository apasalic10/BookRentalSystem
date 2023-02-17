package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Izvjestaj;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.domain.Rental;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class RentalDaoSQLImpl extends AbstractDao<Rental> implements RentalDao{

    private static RentalDaoSQLImpl instance = null;

    public static RentalDaoSQLImpl getInstance(){
        if(instance==null)
            instance = new RentalDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance=null;
    }


    public RentalDaoSQLImpl()  {
       super("Rentals");
    }

    @Override
    public Rental row2object(ResultSet rs) throws BookException {
        try{
            Rental r = new Rental();
            r.setId(rs.getInt("id"));
            r.setRentalDate(rs.getDate("rental_date"));
            r.setRentalBook(DaoFactory.bookDao().getById(rs.getInt("book_id")));
            r.setRentalMember(DaoFactory.memberDao().getById(rs.getInt("member_id")));

            return r;
        }catch (SQLException e){
            throw new BookException(e.getMessage(),e);
        }
    }

    @Override
    public Map<String, Object> object2row(Rental object) {

        Map<String, Object> row = new TreeMap<>();

        row.put("id", object.getId());
        row.put("rental_date", object.getRentalDate());
        row.put("book_id",object.getRentalBook().getId());
        row.put("member_id",object.getRentalMember().getId());

        return row;
    }


    @Override
    public List<Rental> searchByBook(Book book) throws BookException {
        List<Rental> rentals = new LinkedList<>();

        try{
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM Rentals WHERE book_id = ?");
            statement.setInt(1,book.getId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                rentals.add(row2object(rs));
            }

            rs.close();
        }
        catch(SQLException e){
            throw new BookException(e.getMessage(),e);
        }

        return rentals;
    }

    @Override
    public List<Rental> searchByMember(Member member) throws BookException {
        return this.executeQuery("SELECT * FROM Rentals WHERE member_id = ?",member.getId());
    }

    @Override
    public List<Rental> searchByDate(java.sql.Date date) throws BookException {
        return this.executeQuery("SELECT * FROM Rentals WHERE rental_date = ?",date);
    }

    @Override
    public List<Izvjestaj> getByDates() throws BookException {
        List<Rental> lista = getAll();
        List<Izvjestaj> result = new LinkedList<>();

        int brojac = 0;

        LocalDate endDate = LocalDate.now().plusDays(1);



        for (LocalDate date = LocalDate.of(2023,2,15); date.isBefore(endDate); date = date.plusDays(1)){
            for(Rental r : lista){
                if(date.equals(convertDateToLocalDate(r.getRentalDate()))){
                    brojac++;
                }
            }

            result.add(createIzvjestajObject(date,brojac));
            brojac = 0;
        }

        return result;
    }

    private Izvjestaj createIzvjestajObject(LocalDate date, int rents){
        Izvjestaj iz = new Izvjestaj();

        iz.setDate(date);
        iz.setRents(rents);

        return iz;
    }
    public static LocalDate convertDateToLocalDate(Date dateToConvert){
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
