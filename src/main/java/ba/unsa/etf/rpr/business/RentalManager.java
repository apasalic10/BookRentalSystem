package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.domain.Rental;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.util.List;

public class RentalManager {
    public List<Rental> getAll() throws BookException {
        return DaoFactory.rentalDao().getAll();
    }

    public void delete(int id) throws BookException{
        DaoFactory.rentalDao().delete(id);
    }

    public Rental getById(int rentalId) throws BookException{
        return DaoFactory.rentalDao().getById(rentalId);
    }

    public void update(Rental q) throws BookException{
        DaoFactory.rentalDao().update(q);
    }

    public Rental add(Rental q) throws BookException{
        return DaoFactory.rentalDao().add(q);
    }

    public List<Rental> searchByBook(Book book) throws BookException{
        return DaoFactory.rentalDao().searchByBook(book);
    }


    public List<Rental> searchByMember(Member member) throws BookException{
        return DaoFactory.rentalDao().searchByMember(member);
    }


    public List<Rental> searchByDate (java.sql.Date date) throws BookException{
        return DaoFactory.rentalDao().searchByDate(date);
    }
}
