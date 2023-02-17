package ba.unsa.etf.rpr.domain;



import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Izvjestaj{
    private LocalDate date;
    private Integer rents;

    public Izvjestaj() {
    }

    public Izvjestaj(LocalDate date, Integer rents) {
        this.date = date;
        this.rents = rents;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getRents() {
        return rents;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRents(Integer rents) {
        this.rents = rents;
    }

    @Override
    public String toString() {
        return "Izvjestaj{" +
                ", date=" + date +
                ", rents=" + rents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Izvjestaj izvjestaj = (Izvjestaj) o;
        return date.equals(izvjestaj.date) && Objects.equals(rents, izvjestaj.rents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rents);
    }
}
