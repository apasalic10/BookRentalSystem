package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.util.List;

public interface LibraryDao extends Dao<Library> {
    /**
     * return library with the given name
     * @param name of the library
     * @return library with the given name
     */
    public Library getByName(String name) throws BookException;

    /**
     * return list of libraries that are in the given location
     * @param location - the location where the libraries are located
     * @return list of libraries that are in the given location
     */
    public List<Library> searchByLocation(String location) throws BookException;
}
