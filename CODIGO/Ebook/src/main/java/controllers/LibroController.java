package main.java.controllers;

import main.java.models.Libro;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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
        guardarLibrosEnJSON(); // Llama al método para guardar los cambios en el archivo JSON
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

    public static Libro obtenerLibroPorId(String idBNE) {
        JSONParser parser = new JSONParser();

        try {
            JSONArray libros = (JSONArray) parser.parse(new FileReader("src/main/resources/db/set_libros_1.json"));

            for (Object o : libros) {
                JSONObject libroJson = (JSONObject) o;

                if (libroJson.get("id_BNE").equals(idBNE)) {
                    Libro libro = new Libro();
                    libro.setId_BNE((String) libroJson.get("id_BNE"));
                    libro.setAutor_personas((String) libroJson.get("autor_personas"));
                    libro.setEditorial((String) libroJson.get("editorial"));
                    libro.setLugar_publicacion((String) libroJson.get("lugar_publicacion"));
                    libro.setFecha_publicacion((String) libroJson.get("fecha_publicacion"));
                    libro.setDescripcion_fisica((String) libroJson.get("descripcion_fisica"));
                    libro.setDescripcion_notas((String) libroJson.get("descripcion_notas"));
                    libro.setGenero_forma((String) libroJson.get("genero_forma"));
                    libro.setLugar_relacionado((String) libroJson.get("lugar_relacionado"));
                    libro.setPais_publicacion((String) libroJson.get("pais_publicacion"));
                    libro.setLengua_publicacion((String) libroJson.get("lengua_publicacion"));
                    libro.setTipo_documento((String) libroJson.get("tipo_documento"));
                    libro.setSignatura((String) libroJson.get("signatura"));
                    libro.setVersion_digital((String) libroJson.get("version_digital"));
                    libro.setTexto_OCR((String) libroJson.get("texto_OCR"));
                    return libro;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}