package ba.unsa.etf.rpr.exceptions;

public class BookException extends Exception {

    public BookException(String message, Exception reason){
        super(message,reason);
    }

    public BookException(String message){
        super(message);
    }
}
