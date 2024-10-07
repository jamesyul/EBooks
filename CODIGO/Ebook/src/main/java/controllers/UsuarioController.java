package main.java.controllers;

import com.google.gson.stream.JsonReader;
import main.java.models.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UsuarioController {
    private static final String JSON_FILE_PATH = "src/main/resources/db/usuarios.json";
    private static List<Usuario> usuarios;

    static {
        cargarUsuariosDesdeJSON();
    }

    // Agregar esta línea al inicio de la clase
    private static final String BIBLIOTECAS_FILE_PATH = "src/main/resources/db/bibliotecas.json";
    private static List<Map<String, String>> bibliotecas;

    static {
        cargarBibliotecas();
    }

    private static void cargarBibliotecas() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(BIBLIOTECAS_FILE_PATH);
            Type type = new TypeToken<ArrayList<Map<String, String>>>(){}.getType();
            bibliotecas = gson.fromJson(reader, type);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean existeBiblioteca(String idBiblioteca) {
        for (Map<String, String> biblioteca : bibliotecas) {
            if (biblioteca.get("id").equals(idBiblioteca)) {
                return true;
            }
        }
        return false;
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

    public static boolean existeUsuario(String usuario) {
        JSONParser parser = new JSONParser();

        try {
            JSONArray usuarios = (JSONArray) parser.parse(new FileReader("src/main/resources/db/usuarios.json"));

            for (Object o : usuarios) {
                JSONObject usuarioJson = (JSONObject) o;

                String nombre = (String) usuarioJson.get("usuario");

                if (nombre != null && nombre.equals(usuario)) {
                    return true;

                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        //System.out.println("El usuario '" + usuario + "' no existe.");
        return false;
    }

    public static void agregarUsuarioAJSON(Usuario nuevoUsuario) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(JSON_FILE_PATH));
            Type typeOfT = new TypeToken<ArrayList<Usuario>>(){}.getType();
            ArrayList<Usuario> usuariosExistentes = gson.fromJson(reader, typeOfT);
            reader.close();

            usuariosExistentes.add(nuevoUsuario);

            FileWriter writer = new FileWriter(JSON_FILE_PATH);
            JsonArray jsonArray = gson.toJsonTree(usuariosExistentes).getAsJsonArray();
            writer.write(jsonArray.toString());
            writer.close();

            cargarUsuariosDesdeJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarUsuarios() {
        System.out.println("\n--- Lista de Usuarios ---");
        System.out.printf("| %-20s | %-20s | %-15s |\n", "Usuario", "Contraseña", "Perfil");
        for (Usuario u : usuarios) {
            System.out.printf("| %-20s | %-20s | %-15s |\n", u.getUsuario(), u.getPassword(), u.getPerfil());
        }
    }

    public static void modificarUsuario(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese el nombre de usuario a modificar ('q' para regresar): ");
            String usuarioModificar = scanner.nextLine();

            if (usuarioModificar.equalsIgnoreCase("q")) {
                return;
            }

            Usuario usuarioAModificar = null;
            for (Usuario u : usuarios) {
                if (u.getUsuario().equals(usuarioModificar)) {
                    usuarioAModificar = u;
                    break;
                }
            }

            if (usuarioAModificar == null) {
                System.out.println("No se encontró el usuario especificado.");
                continue;
            }

            while (true) {
                System.out.print("Ingrese la nueva contraseña (o deje en blanco para mantener la actual) ('q' para regresar): ");
                String nuevaContrasena = scanner.nextLine();
                if (nuevaContrasena.equalsIgnoreCase("q")) {
                    return;
                }
                if (!nuevaContrasena.isEmpty()) {
                    usuarioAModificar.setPassword(nuevaContrasena);
                }
                break;
            }

            while (true) {
                System.out.print("Ingrese el nuevo perfil (administrador, responsable, usuario) ('q' para regresar): ");
                String nuevoPerfil = scanner.nextLine();
                if (nuevoPerfil.equalsIgnoreCase("q")) {
                    return;
                }
                if (nuevoPerfil.equals("administrador") || nuevoPerfil.equals("responsable") || nuevoPerfil.equals("usuario")) {
                    usuarioAModificar.setPerfil(nuevoPerfil);

                    if (nuevoPerfil.equals("responsable")) {
                        while (true) {
                            System.out.print("Ingrese el id de la biblioteca ('q' para regresar): ");
                            String idBiblioteca = scanner.nextLine();
                            if (idBiblioteca.equalsIgnoreCase("q")) {
                                return;
                            }
                            if (existeBiblioteca(idBiblioteca)) {
                                usuarioAModificar.setIdBiblioteca(idBiblioteca);
                                break;
                            } else {
                                System.out.println("El id de biblioteca ingresado no existe. Por favor, intente nuevamente.");
                            }
                        }
                    } else {
                        usuarioAModificar.setIdBiblioteca(null); // Limpiar el idBiblioteca si no es responsable
                    }
                    break;
                } else {
                    System.out.println("Perfil no válido. Por favor, intente nuevamente.");
                }
            }

            guardarUsuariosEnJSON();
            System.out.println("Usuario modificado correctamente.");
            return;
        }
    }

    public static void eliminarUsuario(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese el nombre de usuario a eliminar ('q' para regresar): ");
            String usuarioEliminar = scanner.nextLine();

            if (usuarioEliminar.equalsIgnoreCase("q")) {
                return;
            }

            Iterator<Usuario> iterator = usuarios.iterator();
            boolean usuarioEncontrado = false;

            while (iterator.hasNext()) {
                Usuario u = iterator.next();
                if (u.getUsuario().equals(usuarioEliminar)) {
                    System.out.print("¿Está seguro que desea eliminar este usuario? (s/n): ");
                    String confirmacion = scanner.nextLine();
                    if (confirmacion.equalsIgnoreCase("s")) {
                        iterator.remove();
                        usuarioEncontrado = true;
                        guardarUsuariosEnJSON();
                        System.out.println("Usuario eliminado correctamente.");
                    } else {
                        System.out.println("Eliminación cancelada.");
                    }
                    break;
                }
            }

            if (!usuarioEncontrado) {
                System.out.println("No se encontró el usuario especificado.");
            }

            return;
        }
    }

    public static String obtenerIdBiblioteca(String usuario) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario)) {
                return u.getIdBiblioteca();
            }
        }
        return null;
    }

    public static String obtenerNombreBiblioteca(String idBiblioteca) {
        for (Map<String, String> biblioteca : bibliotecas) {
            if (biblioteca.get("id").equals(idBiblioteca)) {
                return biblioteca.get("nombre");
            }
        }
        return null;
    }
}