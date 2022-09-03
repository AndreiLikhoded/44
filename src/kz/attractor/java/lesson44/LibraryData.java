package kz.attractor.java.lesson44;

import java.util.ArrayList;
import java.util.List;

public class LibraryData {

    List<Client> clients = new ArrayList<Client>();
    public LibraryData(){
        clients = FileService.readFile();
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
