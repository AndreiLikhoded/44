package kz.attractor.java.system;

import kz.attractor.java.service.FileService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Client {
    private Integer id;
    private String name;

    private String login;
    private String email;
    private String password;

    private List<Integer> booksFor1;
    private List<Integer> booksFor2;

    private transient List<Books> books1;
    private transient List<Books> books2;

    public void setBooks(){
        List<Books> books = FileService.readFile();
        books1 = booksFor1.stream().map(id -> books.get(id - 1)).collect(Collectors.toList());
        books2 = booksFor2.stream().map(id -> books.get(id - 1)).collect(Collectors.toList());
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Client(Integer id, String name, String login, String email, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public static Client createClient(Integer id, Map<String, String> map) {
        return new Client(id, map.get("name"), map.get("login"), map.get("email"), map.get("password"));
    }

    public static Boolean checkClientForExistence(Client  client, Client client2) {
        return client.getEmail().equals(client2.getEmail());
    }

    public static Boolean checkLoginForExistence(Client  client, Client client2) {
        return client.getLogin().equals(client2.getLogin());
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
