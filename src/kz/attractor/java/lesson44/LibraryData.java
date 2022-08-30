package kz.attractor.java.lesson44;

import java.util.ArrayList;
import java.util.List;

public class LibraryData {

    List<Clients> clients = new ArrayList<Clients>();
    public LibraryData(){
        clients = FileService.readFile();
    }

    public List<Clients> getClients() {
        return clients;
    }

    public void setClients(List<Clients> clients) {
        this.clients = clients;
    }
}
