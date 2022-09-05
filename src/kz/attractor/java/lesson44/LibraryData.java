package kz.attractor.java.lesson44;

import kz.attractor.java.service.FileService;
import kz.attractor.java.system.Books;
import kz.attractor.java.system.Client;

import java.util.ArrayList;
import java.util.List;

public class LibraryData {


    private List<Books> books;

    public LibraryData() {
        this.books = FileService.readFile();
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }
}
