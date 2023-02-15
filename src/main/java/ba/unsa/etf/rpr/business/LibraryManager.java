package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.util.List;

/**
 * Business Logic Layer for Library
 *
 * @author Pasalic Almedin
 */
public class LibraryManager {
    public List<Library> getAll() throws BookException {
        return DaoFactory.libraryDao().getAll();
    }

    public void delete(int id) throws BookException{
        DaoFactory.libraryDao().delete(id);
    }

    public Library getById(int libraryId) throws BookException{
        return DaoFactory.libraryDao().getById(libraryId);
    }

    public void update(Library q) throws BookException{
        DaoFactory.libraryDao().update(q);
    }

    public Library add(Library q) throws BookException{
        return DaoFactory.libraryDao().add(q);
    }

    public Library getByName(String name) throws BookException{
        return DaoFactory.libraryDao().getByName(name);
    }

    public List<Library> searchByLocation(String location) throws BookException{
        return DaoFactory.libraryDao().searchByLocation(location);
    }
}
