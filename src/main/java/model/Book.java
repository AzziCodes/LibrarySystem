package model;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private final String title;
    private final String author;
    private final String ISBN;
    private final LocalDate releaseDate;

    public Book(String title, String author, String ISBN, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.releaseDate = releaseDate;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getISBN() { return ISBN; }
    public LocalDate getReleaseDate() { return releaseDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(ISBN, book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    @Override
    public String toString() {
        return String.format("%s by %s (ISBN: %s)", title, author, ISBN);
    }
}
