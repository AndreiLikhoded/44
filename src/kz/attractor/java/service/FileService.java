package kz.attractor.java.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kz.attractor.java.system.Books;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = Paths.get("./books.json");

    public static List<Books> readFile(){
        String json = "";
        try{
            json = Files.readString(PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Books[] users = GSON.fromJson(json, Books[].class);
        return new ArrayList<Books>(List.of(users));
    }

    public static void writeFile(List<Books> books){
        String json = GSON.toJson(books);
        try{
            byte[] arr = json.getBytes();
            Files.write(PATH, arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
