package ba.unsa.etf.rpr.domain;

import java.util.Objects;

public class Library {
   private int libraryId;
   private String name;
   private String location;

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
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
                "libraryId=" + libraryId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return libraryId == library.libraryId && name.equals(library.name) && location.equals(library.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(libraryId, name, location);
    }
}
