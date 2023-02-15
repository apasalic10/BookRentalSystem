package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.LibraryManager;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.exceptions.BookException;
import org.apache.commons.cli.*;

import java.io.PrintWriter;

/**
 * CLI (Command Line Interface) implementation
 */
public class App {
    private static final Option addBook = new Option("b", "add-book", false, "Adding a new book to database");
    private static final Option addLibrary = new Option("l", "add-library", false, "Adding a new library to database");
    private static final Option deleteBook = new Option("delB", "delete-book", false, "Deleting a book from database");
    private static final Option getBooks = new Option("getB", "get-books", false, "Printing all books from database");
    private static final Option deleteLibrary = new Option("delL", "delete-library", false, "Deleting a library from database");
    private static final Option getLibraries = new Option("getL", "get-library", false, "Printing all libraries from database");

    /**
     * Print formatted options.
     *
     * @param options the options
     */
    public static void printFormattedOptions(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        helpFormatter.printUsage(printWriter, 150, "java -jar BookRentalSystem-cli-jar-with-dependencies.jar [option] (parameters)");
        helpFormatter.printOptions(printWriter, 150, options, 2, 7);
        printWriter.close();
    }

    /**
     * Add options.
     *
     * @return the options
     */
    public static Options addOptions() {
        Options options = new Options();
        options.addOption(addBook);
        options.addOption(deleteBook);
        options.addOption(getBooks);
        options.addOption(deleteLibrary);
        options.addOption(getLibraries);
        options.addOption(addLibrary);

        return options;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws ParseException   the parse exception
     * @throws BookException the filmovi exception
     */

    public static void main(String[] args) throws BookException, ParseException {
        Options options = addOptions();
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine cl = commandLineParser.parse(options, args);

        BookManager bookManager = new BookManager();
        LibraryManager libraryManager = new LibraryManager();

        if(cl.hasOption(addBook.getOpt()) || cl.hasOption(addBook.getLongOpt()) ){
            try{
                Book book = new Book();
                book.setBookName(cl.getArgList().get(0));
                book.setBookAuthor(cl.getArgList().get(1));
                book.setBookLibrary(libraryManager.getByName(cl.getArgList().get(2)));
                book.setIsAvailable(1);

                bookManager.add(book);
                System.out.println("Book successfully added to database!");
            }
            catch (Exception e){
                System.out.println("Error. Invalid parameters.");
                printFormattedOptions(options);
                System.exit(-1);
            }
        }else if(cl.hasOption(deleteBook.getOpt()) || cl.hasOption(deleteBook.getLongOpt())){
            try{
                Book book = new Book();
                book = (Book) bookManager.searchByText(cl.getArgList().get(0));
                bookManager.delete(book.getId());
                System.out.println("Book successfully deleted from database!");
            }catch (Exception e){
                System.out.println("Book with that name does not exist.");
                System.exit(-1);
            }
        }
        else if(cl.hasOption(getBooks.getOpt()) || cl.hasOption(getBooks.getLongOpt())){
            bookManager.getAll().forEach(f -> System.out.println(f.getBookName()));
        }
        else if (cl.hasOption(addLibrary.getOpt()) || cl.hasOption(addLibrary.getLongOpt())){{
                try {
                    Library lib = new Library();

                    lib.setName(cl.getArgList().get(0));
                    lib.setLocation(cl.getArgList().get(1));

                    libraryManager.add(lib);
                    System.out.println("Library successfully added to database!");
                } catch (Exception e) {
                    System.out.println("Error. Invalid parameters.");
                    printFormattedOptions(options);
                    System.exit(-1);
                }
            }
        }
        else if(cl.hasOption(deleteLibrary.getOpt()) || cl.hasOption(deleteLibrary.getLongOpt())){
            try{
                Library lib = new Library();
                lib = libraryManager.getByName(cl.getArgList().get(0));
                libraryManager.delete(lib.getId());
                System.out.println("Library successfully deleted from database!");
            }catch (Exception e){
                System.out.println("Library with that name does not exist.");
                System.exit(-1);
            }
        }
        else if (cl.hasOption(getLibraries.getOpt()) || cl.hasOption(getLibraries.getLongOpt())){
            libraryManager.getAll().forEach(f -> System.out.println(f.getName()));
        }
        else{
            printFormattedOptions(options);
            System.exit(-1);
        }
    }


}
