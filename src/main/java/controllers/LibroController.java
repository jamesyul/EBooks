package main.java.controllers;

import main.java.models.Libro;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class LibroController {
    private static final String JSON_FILE_PATH = "src/main/resources/db/set_libros_1.json";
    private static List<Libro> libros;

    static {
        cargarLibrosDesdeJSON();
    }

    public static void cargarLibrosDesdeJSON() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(JSON_FILE_PATH);
            Type type = new TypeToken<ArrayList<Libro>>() {}.getType();
            libros = gson.fromJson(new JsonParser().parse(reader).getAsJsonArray(), type);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarLibrosEnJSON() {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(JSON_FILE_PATH);
            JsonArray jsonArray = gson.toJsonTree(libros).getAsJsonArray();
            writer.write(jsonArray.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Operaciones CRUD
    public static void crearLibro(Libro libro) {
        libros.add(libro);
        guardarLibrosEnJSON();
    }

    public static List<Libro> obtenerLibros() {
        return libros;
    }

    public static void actualizarLibro(Libro libroActualizado) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getId_BNE().equals(libroActualizado.getId_BNE())) {
                libros.set(i, libroActualizado);
                break;
            }
        }
        guardarLibrosEnJSON();
    }

    public static void eliminarLibro(String id_BNE) {
        libros.removeIf(libro -> libro.getId_BNE().equals(id_BNE));
        guardarLibrosEnJSON();
    }

    // Métrica
    public static Map<String, Integer> obtenerMetricaEditorial() {
        Map<String, Integer> metricaEditorial = new HashMap<>();

        for (Libro libro : libros) {
            String editorial = libro.getEditorial();
            metricaEditorial.put(editorial, metricaEditorial.getOrDefault(editorial, 0) + 1);
        }

        return metricaEditorial;
    }
}