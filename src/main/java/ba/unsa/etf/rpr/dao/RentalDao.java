package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Izvjestaj;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.domain.Rental;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.util.List;

public interface RentalDao extends Dao<Rental> {
    /**
     * return rent list of the given book
     * @param book - the book from which rents are requested
     * @return list of rentals for the given book
     */
    public List<Rental> searchByBook(Book book) throws BookException;

    /**
     * return rent list of the given member
     * @param member - the member from whom rents are requested
     * @return list of rentals for the given member
     */
    public List<Rental> searchByMember(Member member) throws BookException;

    /**
     * rent list for the given date
     * @param date - the date by which rents are requested
     * @return list of rentals for the given date
     */
    public List<Rental> searchByDate (java.sql.Date date) throws BookException;

    public List<Izvjestaj> getByDates() throws BookException;

}
