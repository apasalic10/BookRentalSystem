package ba.unsa.etf.rpr.domain;

import java.util.Date;
import java.util.Objects;

public class Rental {
   private int rentalId;
   private Date rentalDate;
   private Book rentalBook;
   private Member rentalMember;

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Book getRentalBook() {
        return rentalBook;
    }

    public void setRentalBook(Book rentalBook) {
        this.rentalBook = rentalBook;
    }

    public Member getRentalMember() {
        return rentalMember;
    }

    public void setRentalMember(Member rentalMember) {
        this.rentalMember = rentalMember;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalId=" + rentalId +
                ", rentalDate=" + rentalDate +
                ", rentalBook=" + rentalBook +
                ", rentalMember=" + rentalMember +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return rentalId == rental.rentalId && rentalDate.equals(rental.rentalDate) && rentalBook.equals(rental.rentalBook) && rentalMember.equals(rental.rentalMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, rentalDate, rentalBook, rentalMember);
    }
}
