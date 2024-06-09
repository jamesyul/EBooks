package main.java.controllers;

import main.java.models.Usuario;
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

public class UsuarioController {
    private static final String JSON_FILE_PATH = "src/main/resources/db/usuarios.json";
    private static List<Usuario> usuarios;

    static {
        cargarUsuariosDesdeJSON();
    }

    public static void cargarUsuariosDesdeJSON() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(JSON_FILE_PATH);
            Type type = new TypeToken<ArrayList<Usuario>>() {}.getType();
            usuarios = gson.fromJson(new JsonParser().parse(reader).getAsJsonArray(), type);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarUsuariosEnJSON() {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(JSON_FILE_PATH);
            JsonArray jsonArray = gson.toJsonTree(usuarios).getAsJsonArray();
            writer.write(jsonArray.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean autenticarUsuario(String usuario, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static String obtenerPerfilUsuario(String usuario, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
                return u.getPerfil();
            }
        }
        return null;
    }
}