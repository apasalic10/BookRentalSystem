package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.LibraryManager;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
     * Test searchByLocation method from LibraryManager
     * @throws BookException
     */
    @Test
    void searchByLocation() throws BookException{
        List<Library> list = libraryManager.searchByLocation("Gradacac");
        boolean isValid = true;

        for(Library lib : list){
            if(!lib.getLocation().equals("Gradacac")){
                isValid = false;
            }
        }

        Assertions.assertTrue(isValid);
    }

    /**
     * Test getAll method from LibraryManager
     * @throws BookException
     */
    @Test
    public void getAllTest() throws BookException{
        List<Library> list = libraryManager.getAll();

        libraryManager.add(createLibrary());

        boolean isValid = false;

        List<Library> tempList = libraryManager.getAll();

        if(list.size() + 1 == tempList.size()){
            isValid = true;
        }

        Assertions.assertTrue(isValid);

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
