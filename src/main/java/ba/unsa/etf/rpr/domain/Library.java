package ba.unsa.etf.rpr.domain;

import java.util.Objects;

public class Library implements Idable{
   private int Id;
   private String name;
   private String location;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Library{" +
                "libraryId=" + Id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Id == library.Id && name.equals(library.name) && location.equals(library.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, location);
    }
}
