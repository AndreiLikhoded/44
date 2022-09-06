package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.Cookie;
import kz.attractor.java.server.ResponseCodes;
import kz.attractor.java.server.Utils;
import kz.attractor.java.service.ReadersService;
import kz.attractor.java.system.Client;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson46Server extends Lesson45Server {

    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);

        registerGet("/cookie", this::cookieHandler);
    }

    private void cookieHandler(HttpExchange exchange) {
        Cookie sessionCookie = Cookie.make("userId", "123");
        exchange.getResponseHeaders()
                .add("Set-Cookie", sessionCookie.toString());
        Map<String, Object> data = new HashMap<>();
        String name = "tames";
        int times = 2;
        data.put("times", times);

        Cookie c1 = Cookie.make("user%Id", "456");
        setCookie(exchange, c1);
        Cookie c2 = Cookie.make("user-mail", "example@mail");
        setCookie(exchange, c2);
        Cookie c3 = Cookie.make("restricted()<>@,;:\\\"/[]?={}", "()<>@,;:\\\"/[]?={}");
        setCookie(exchange, c3);
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        String cookieValue = cookies.getOrDefault("times", "0");
        int times = Integer.parseInt(cookieValue) + 1;
        Cookie c4 = new Cookie(name,times);
        setCookie(exchange, c4);

        data.put(name,times);
        data.put("cookies",cookies);


    }
    protected static String getCookies(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getOrDefault("Cookie", List.of(""))
                .get(0);

    }
    protected void setCookie(HttpExchange exchange, Cookie cookie) {
        exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }

    public void profileGetData (HttpExchange exchange){
        renderTemplate(exchange, "profile.html", new Client());
    }

    public void profileGet (HttpExchange exchange){
        Path path = makeFilePath("errorRegistration.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

}
