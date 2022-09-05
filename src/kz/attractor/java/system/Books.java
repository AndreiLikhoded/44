package kz.attractor.java.system;

import kz.attractor.java.service.ReadersService;

import java.util.List;

public class Books {
    private String name;

    private boolean busy;

    private Integer bookId;

    private transient Client client;


    public Books(String name) {
        this.name = name;

        bookStatus();
    }

    public void bookStatus() {
        if (busy) {
            List<Client> client = ReadersService.readFile();
            for (Client clients : client) {
                if (clients.getId().equals(bookId)) {
                    this.client = clients;
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

