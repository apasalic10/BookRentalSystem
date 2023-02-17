package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Izvjestaj;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.domain.Rental;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
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

    public List<Izvjestaj> getByDates(LocalDate start, LocalDate end) throws BookException{
        List<Rental> lista = DaoFactory.rentalDao().getByDates(start.minusDays(1),end.plusDays(1));
        List<Izvjestaj> result = new LinkedList<>();

        int brojac = 0;




        for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)){
            for(Rental r : lista){
                if(date.equals(convertDateToLocalDate(r.getRentalDate()))){
                    brojac++;
                }
            }

            result.add(new Izvjestaj(date,brojac));
            brojac = 0;
        }

        return result;
    }


    public static LocalDate convertDateToLocalDate(Date dateToConvert){
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
