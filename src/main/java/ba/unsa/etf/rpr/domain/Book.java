package ba.unsa.etf.rpr.domain;

import java.util.Objects;

public class Book {
    private int bookId;
    private String bookName;
    private Library bookLibrary;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Library getBookLibrary() {
        return bookLibrary;
    }

    public void setBookLibrary(Library bookLibrary) {
        this.bookLibrary = bookLibrary;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookLibrary=" + bookLibrary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && bookName.equals(book.bookName) && bookLibrary.equals(book.bookLibrary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookName, bookLibrary);
    }
}
