package kz.attractor.java.lesson44;

public class Books {
    private String book;

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Books{" +
                "book='" + book + '\'' +
                '}';
    }
}
