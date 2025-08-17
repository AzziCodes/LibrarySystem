package model;

import java.util.Objects;

public class Copy {
    private final String copyId; // TODO: Automation of assigning copyID
    private final Book book;
    private CopyStatus status;
    private CopyCondition condition;

    // Constructor with default condition
    public Copy(String copyId, Book book) {
        this(copyId, book, CopyCondition.NEW);
    }

    // Constructor with explicit condition
    public Copy(String copyId, Book book, CopyCondition condition) {
        this.copyId = copyId;
        this.book = book;
        this.status = CopyStatus.AVAILABLE;
        this.condition = condition;
    }

    public String getCopyId() { return copyId; }
    public Book getBook() { return book; }
    public CopyStatus getStatus() { return status; }
    public CopyCondition getCondition() { return condition; }

    public void setCondition(CopyCondition condition) {
        this.condition = condition;
    }
    public void setStatus(CopyStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Copy[%s] of %s by %s (ISBN: %s): Condition=%s, Status=%s",
                copyId, book.getTitle(), book.getAuthor(), book.getISBN(), condition, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Copy)) return false;
        Copy copy = (Copy) o;
        return Objects.equals(copyId, copy.copyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(copyId);
    }
}
