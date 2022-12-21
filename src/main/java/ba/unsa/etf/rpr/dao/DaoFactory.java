package ba.unsa.etf.rpr.dao;

public class DaoFactory {
    private static final BookDao bookDao = new BookDaoSQLImpl();
    private static final LibraryDao libraryDao = new LibraryDaoSQLImpl();

    private static final MemberDao memberDao = new MemberDaoSQLImpl();
    private static final RentalDao rentalDao = new RentalDaoSQLImpl();

    private DaoFactory(){
    }

    public static BookDao bookDao(){
        return bookDao;
    }

    public static LibraryDao libraryDao(){
        return libraryDao;
    }

    public static MemberDao memberDao(){
        return memberDao;
    }

    public static RentalDao rentalDao(){
        return rentalDao;
    }

}
