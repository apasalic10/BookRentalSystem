package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.LibraryManager;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LibraryManagerTest {
    LibraryManager libraryManager = new LibraryManager();

    /**
     * Test getByName method from LibraryManager class
     * @throws BookException
     */
    @Test
    public void getByNameTest() throws BookException {
        libraryManager.add(createLibrary());

        Assertions.assertEquals("Test", libraryManager.getByName("Test").getName());

        deleteLibrary();
    }

    /**
     * Create library for tests
     * @throws BookException
     */
    private Library createLibrary() throws BookException {
        Library lib = new Library();

        lib.setName("Test");
        lib.setLocation("Test");

        return lib;
    }


    /**
     * Delete library after test
     * @throws BookException
     */
    private void deleteLibrary() throws BookException {
        libraryManager.delete(libraryManager.getByName("Test").getId());
    }
}
