package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.server.Utils;
import kz.attractor.java.service.ReadersService;
import kz.attractor.java.system.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;


public class Lesson45Server extends Lesson44Server{

    private Client client = null;


    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/loginPage", this::loginGet);
        registerGet("/registration", this::registeringGet);
        registerGet("/profile", this::profileGet);

        registerPost("/loginPage", this::loginPost);
        registerPost("/registration", this::registeringPost);
    }

    private void registeringGet(HttpExchange exchange) {
        renderTemplate(exchange, "registration.html", null);
    }

    private void registeringPost(HttpExchange exchange) {
        Map<String, Object> map = new HashMap<>();
        String cType = getContentType(exchange);
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            if (parsed.size() == Client.class.getDeclaredFields().length - 1) {
                List<Client> clients = ReadersService.readFile();
                Client client = Client.createClient(clients.size() + 1, parsed);
                for (Client reader : clients) {
                    if (Client.checkClientForExistence(client, reader)) {
                        throw new RuntimeException("user already exist!");
                    }
                }
                clients.add(client);
                ReadersService.writeFile(clients);
                redirect303(exchange, "/loginPage");
            } else {
                map.put("fail_text", "Fill in all fields!");
                renderTemplate(exchange, "registration.html", map);
            }
        } catch (Exception e) {
            e.printStackTrace();

            map.put("fail_text", "Error");
            renderTemplate(exchange, "register.html", map);
        }
    }

    private void loginGet(HttpExchange exchange) {
        HashMap<String, Object> parsed = new HashMap<>();
        parsed.put("fail", false);
        renderTemplate(exchange, "index.html", parsed);

    }

    private void loginPost(HttpExchange exchange) {
        Map<String, Object> map = new HashMap<>();
        String cType = getContentType(exchange);
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            if (parsed.size() == 2) {
                List<Client> clients = ReadersService.readFile();
                for (Client reader : clients) {
                    if (reader.getEmail().equals(parsed.get("email")) && reader.getPassword().equals(parsed.get("password"))) {
                        client = reader;
                        throw new RuntimeException();
                    }
                }
            }
            map.put("fail", true);
            renderTemplate(exchange, "index.html", map);
        } catch (Exception e) {
            redirect303(exchange, "/profile");
        }
    }

    private void profileGet(HttpExchange exchange) {
        if(client != null) {
            client.setBooks();
        }
        renderTemplate(exchange, "profile.html", client);
    }

    public static String getContentType(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getOrDefault("Content-Type", List.of(""))
                .get(0);
    }

    protected String getBody(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        Charset utf8 = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(input, utf8);
        try (BufferedReader reader = new BufferedReader(isr)) {
            return reader.lines().collect(joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected final void redirect303(HttpExchange exchange, String path) {
        try {
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(303, 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
