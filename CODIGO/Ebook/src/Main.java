import java.util.Scanner;
import java.util.List;
import java.util.Map;
import main.java.models.Libro;
import main.java.models.Usuario;
import main.java.controllers.LibroController;
import main.java.controllers.UsuarioController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import main.java.controllers.UsuarioController;

import static main.java.controllers.UsuarioController.*;

public class Main {
    public static void main(String[] args) {
        mostrarMenuInicial();
    }

    private static void mostrarMenuInicial() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Inicial ---");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Ingrese una opción: ");

            String input=scanner.nextLine();
            int opcion;
            //scanner.nextLine();

            try{
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:
                        iniciarSesion(scanner);
                        break;
                    case 2:
                        registrarUsuario(scanner);
                        break;
                    case 3:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            }catch(NumberFormatException e){
                System.out.println("Entrada inválida. Por favor, ingrese un número entre 1 y 3 segun las opciones.");
            }
        }
    }


    private static void iniciarSesion(Scanner scanner) {
        boolean volverAlMenu = false;

        while (!volverAlMenu) {
            System.out.print("Ingrese su nombre de usuario ('q' para volver al menú principal): ");
            String usuario = scanner.nextLine();

            if (usuario.equalsIgnoreCase("q")) {
                volverAlMenu = true;
            } else if (UsuarioController.existeUsuario(usuario)) {
                boolean contraseniaValida = false;
                while (!contraseniaValida) {
                    System.out.print("Ingrese su contraseña ('q' para volver a ingresar usuario): ");
                    String password = scanner.nextLine();

                    if (password.equalsIgnoreCase("q")) {
                        break; // Vuelve al bucle exterior para ingresar el usuario de nuevo
                    }

                    if (UsuarioController.autenticarUsuario(usuario, password)) {
                        contraseniaValida = true;
                        String perfil = UsuarioController.obtenerPerfilUsuario(usuario, password);
                        if (perfil.equals("responsable")) {
                            String idBiblioteca = UsuarioController.obtenerIdBiblioteca(usuario);
                            String nombreBiblioteca = UsuarioController.obtenerNombreBiblioteca(idBiblioteca);
                            System.out.println("\n--------[" + nombreBiblioteca + "]--------");
                            System.out.println("Bienvenido, " + usuario + ". Tu perfil es: " + perfil);
                        } else {
                            System.out.println("Bienvenido, " + usuario + ". Tu perfil es: " + perfil);
                        }

                        switch (perfil) {
                            case "administrador":
                                menuAdministrador();
                                break;
                            case "responsable":
                                menuResponsable();
                                break;
                            case "usuario":
                                menuUsuario();
                                break;
                            default:
                                System.out.println("Perfil no válido.");
                        }
                        volverAlMenu = true;
                    } else {
                        System.out.println("Contraseña incorrecta. Intente nuevamente.");
                    }
                }
            } else {
                System.out.println("El usuario '" + usuario + "' no existe. Intente nuevamente.");
            }
        }
    }

    private static void registrarUsuario(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Registro de Usuario ---");
            System.out.println("1. Usuario");
            System.out.println("2. Responsable");
            System.out.println("3. Administrador");
            System.out.println("4. Regresar al menú anterior");

            int opcion = obtenerOpcionValida(scanner, 1, 4);

            switch (opcion) {
                case 1:
                    if (!registrarUsuarioNormal(scanner)) continue;
                    break;
                case 2:
                    if (!registrarResponsable(scanner)) continue;
                    break;
                case 3:
                    registrarAdministrador(scanner);
                    break;
                case 4:
                    System.out.println("Regresando al menú anterior...");
                    return;
            }

            // Si llegamos aquí, significa que se completó un registro o se eligió volver
            // Preguntamos si quiere registrar otro usuario o volver al menú principal
            System.out.print("¿Desea registrar otro usuario? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                return; // Volver al menú principal
            }
            // Si la respuesta es "s", el ciclo continuará y mostrará el menú de nuevo
        }
    }

    private static int obtenerOpcionValida(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("Ingrese su opción: ");
            String entrada = scanner.nextLine();

            try {
                int opcion = Integer.parseInt(entrada);
                if (opcion >= min && opcion <= max) {
                    return opcion;
                } else {
                    System.out.printf("Por favor, ingrese un número entre %d y %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private static boolean registrarUsuarioNormal(Scanner scanner) {
        String usuario = obtenerEntrada(scanner, "Ingrese el nombre de usuario ('q' para regresar): ");
        if (usuario == null) return false;

        String password = obtenerEntrada(scanner, "Ingrese la contraseña ('q' para regresar): ");
        if (password == null) return false;

        Usuario nuevoUsuario = new Usuario(usuario, password, "usuario");
        UsuarioController.agregarUsuarioAJSON(nuevoUsuario);
        System.out.println("Usuario registrado correctamente.");
        return true;
    }

    private static boolean registrarResponsable(Scanner scanner) {
        String usuario = obtenerEntrada(scanner, "Ingrese el nombre de usuario ('q' para regresar): ");
        if (usuario == null) return false;

        String password = obtenerEntrada(scanner, "Ingrese la contraseña ('q' para regresar): ");
        if (password == null) return false;

        String idBiblioteca = obtenerEntrada(scanner, "Ingrese el ID de la biblioteca ('q' para regresar): ");
        if (idBiblioteca == null) return false;

        if (existeBiblioteca(idBiblioteca)) {
            Usuario nuevoUsuario = new Usuario(usuario, password, "responsable", idBiblioteca);
            UsuarioController.agregarUsuarioAJSON(nuevoUsuario);
            System.out.println("Responsable registrado correctamente.");
            return true;
        } else {
            System.out.println("El ID de la biblioteca ingresado no es válido.");
            return false;
        }
    }

    private static String obtenerEntrada(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            if (entrada.equalsIgnoreCase("q")) {
                System.out.println("Regresando al menú anterior...");
                return null;
            }
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("La entrada no puede estar vacía. Intente de nuevo.");
        }
    }

    private static void registrarAdministrador(Scanner scanner) {
        System.out.println("Para registrarse como administrador, envía un correo a EbookLibrary@ebooklibrary.com");
    }

    private static void administrarUsuarios(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Administrar Usuarios ---");
            System.out.println("1. Mostrar usuarios");
            System.out.println("2. Modificar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Volver al menú principal");
            System.out.print("Ingrese una opción: ");

            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("Por favor, ingrese una opción.");
                continue;
            }

            try {
                int opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1:
                        mostrarUsuarios();
                        break;
                    case 2:
                        modificarUsuario(scanner);
                        break;
                    case 3:
                        eliminarUsuario(scanner);
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese un número entre 1 y 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número entre 1 y 4.");
            }
        }
    }

    private static void menuAdministrador() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Administrador ---");
            System.out.println("1. Operaciones CRUD");
            System.out.println("2. Mostrar métrica de editoriales");
            System.out.println("3. Administrar usuarios");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opción: ");

            String input = scanner.nextLine();
            int opcion;

            try {
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:
                        // Código existente para Operaciones CRUD
                        boolean salirCRUD = false;
                        while (!salirCRUD) {
                            System.out.println("\n--- Operaciones CRUD ---");
                            System.out.println("1. Crear libro");
                            System.out.println("2. Obtener libros");
                            System.out.println("3. Actualizar libro");
                            System.out.println("4. Eliminar libro");
                            System.out.println("5. Volver al menú principal");
                            System.out.print("Ingrese una opción: ");

                            try{
                                int opcionCRUD = Integer.parseInt(scanner.nextLine());

                                if (opcionCRUD < 1 || opcionCRUD > 5) {
                                    System.out.println("Por favor, ingrese un número entre 1 y 5.");
                                    continue;
                                }

                                switch (opcionCRUD) {
                                    case 1:
                                        System.out.println("-------Datos del nuevo libro-------");
                                        Libro nuevoLibro = new Libro();
                                        boolean cancelarCreacion = false;

                                        String[] campos = {"ID", "Autor Personas", "Autor Entidades", "Título", "Editorial",
                                                "Lugar Publicación", "Fecha Publicación", "Descripción Física",
                                                "Descripción Notas", "Género Forma", "Lugar Relacionado",
                                                "País Publicación", "Lengua Publicación", "Tipo Documento",
                                                "Signatura", "Versión Digital", "Texto OCR"};

                                        for (String campo : campos) {
                                            System.out.print(campo + " ('q' para volver): ");
                                            String valor = scanner.nextLine();

                                            if (valor.equalsIgnoreCase("q")) {
                                                cancelarCreacion = true;
                                                break;
                                            }

                                            switch (campo) {
                                                case "ID":
                                                    nuevoLibro.setId_BNE(valor);
                                                    break;
                                                case "Autor Personas":
                                                    nuevoLibro.setAutor_personas(valor);
                                                    break;
                                                case "Autor Entidades":
                                                    nuevoLibro.setAutor_entidades(valor);
                                                    break;
                                                case "Título":
                                                    nuevoLibro.setTitulo(valor);
                                                    break;
                                                case "Editorial":
                                                    nuevoLibro.setEditorial(valor);
                                                    break;
                                                case "Lugar Publicación":
                                                    nuevoLibro.setLugar_publicacion(valor);
                                                    break;
                                                case "Fecha Publicación":
                                                    nuevoLibro.setFecha_publicacion(valor);
                                                    break;
                                                case "Descripción Física":
                                                    nuevoLibro.setDescripcion_fisica(valor);
                                                    break;
                                                case "Descripción Notas":
                                                    nuevoLibro.setDescripcion_notas(valor);
                                                    break;
                                                case "Género Forma":
                                                    nuevoLibro.setGenero_forma(valor);
                                                    break;
                                                case "Lugar Relacionado":
                                                    nuevoLibro.setLugar_relacionado(valor);
                                                    break;
                                                case "País Publicación":
                                                    nuevoLibro.setPais_publicacion(valor);
                                                    break;
                                                case "Lengua Publicación":
                                                    nuevoLibro.setLengua_publicacion(valor);
                                                    break;
                                                case "Tipo Documento":
                                                    nuevoLibro.setTipo_documento(valor);
                                                    break;
                                                case "Signatura":
                                                    nuevoLibro.setSignatura(valor);
                                                    break;
                                                case "Versión Digital":
                                                    nuevoLibro.setVersion_digital(valor);
                                                    break;
                                                case "Texto OCR":
                                                    nuevoLibro.setTexto_OCR(valor);
                                                    break;
                                            }
                                        }

                                        if (!cancelarCreacion) {
                                            try {
                                                LibroController.crearLibro(nuevoLibro);
                                                System.out.println("Libro creado correctamente.");
                                                System.out.println("Aquí están los detalles del libro que has agregado:");
                                                System.out.println("| ID                  | " + nuevoLibro.getId_BNE() + " |");
                                                System.out.println("| Autor Personas      | " + nuevoLibro.getAutor_personas() + " |");
                                                System.out.println("| Autor Entidades     | " + nuevoLibro.getAutor_entidades() + " |");
                                                System.out.println("| Título              | " + nuevoLibro.getTitulo() + " |");
                                                System.out.println("| Editorial           | " + nuevoLibro.getEditorial() + " |");
                                                System.out.println("| Lugar Publicación   | " + nuevoLibro.getLugar_publicacion() + " |");
                                                System.out.println("| Fecha Publicación   | " + nuevoLibro.getFecha_publicacion() + " |");
                                                System.out.println("| Descripción Física  | " + nuevoLibro.getDescripcion_fisica() + " |");
                                                System.out.println("| Descripción Notas   | " + nuevoLibro.getDescripcion_notas() + " |");
                                                System.out.println("| Género Forma        | " + nuevoLibro.getGenero_forma() + " |");
                                                System.out.println("| Lugar Relacionado   | " + nuevoLibro.getLugar_relacionado() + " |");
                                                System.out.println("| País Publicación    | " + nuevoLibro.getPais_publicacion() + " |");
                                                System.out.println("| Lengua Publicación  | " + nuevoLibro.getLengua_publicacion() + " |");
                                                System.out.println("| Tipo Documento      | " + nuevoLibro.getTipo_documento() + " |");
                                                System.out.println("| Signatura           | " + nuevoLibro.getSignatura() + " |");
                                                System.out.println("| Versión Digital     | " + nuevoLibro.getVersion_digital() + " |");
                                                System.out.println("| Texto OCR           | " + nuevoLibro.getTexto_OCR() + " |");
                                            } catch (Exception e) {
                                                System.out.println("Ocurrió un error al crear el libro: " + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("Creación de libro cancelada. Volviendo al menú CRUD...");
                                        }
                                        break;
                                    case 2:

                                        List<Libro> libros = LibroController.obtenerLibros();
                                        System.out.println("\n--- Lista de Libros ---");
                                        System.out.printf("| %-10s | %-30s | %-30s | %-30s |\n", "ID", "Editorial", "Autor Personas", "Autor Entidades");
                                        for (Libro libro : libros) {
                                            System.out.printf("| %-10s | %-30s | %-30s | %-30s |\n", libro.getId_BNE(), libro.getEditorial(), libro.getAutor_personas(), libro.getAutor_entidades());
                                        }

                                        System.out.print("\nIngrese el id_BNE del libro a consultar: ");
                                        String idBNEConsultar = scanner.nextLine();
                                        Libro libroConsultado = LibroController.obtenerLibroPorId(idBNEConsultar);
                                        if (libroConsultado != null) {
                                            System.out.println("\n--- Detalles del Libro ---");
                                            System.out.println(libroConsultado);
                                        } else {
                                            System.out.println("No se encontró un libro con ese id_BNE.");
                                        }
                                        break;
                                    case 3:

                                        System.out.print("Ingrese el id_BNE del libro a modificar ('q' para volver): ");
                                        String idBNE = scanner.nextLine();

                                        if (idBNE.equalsIgnoreCase("q")) {
                                            System.out.println("Operación cancelada. Volviendo al menú CRUD...");
                                            break;
                                        }

                                        Libro libroExistente = LibroController.obtenerLibroPorId(idBNE);
                                        if (libroExistente == null) {
                                            System.out.println("No se encontró un libro con ese ID.");
                                            break;
                                        }

                                        System.out.println("Libro encontrado. Ingrese los nuevos datos (presione Enter para mantener el valor actual):");

                                        String[] camposActualizar = {"Autor Personas", "Autor Entidades", "Título", "Editorial",
                                                "Lugar Publicación", "Fecha Publicación", "Descripción Física",
                                                "Descripción Notas", "Género Forma", "Lugar Relacionado",
                                                "País Publicación", "Lengua Publicación", "Tipo Documento",
                                                "Signatura", "Versión Digital", "Texto OCR"};

                                        for (String campo : camposActualizar) {
                                            System.out.print(campo + " (Enter para mantener, 'q' para volver): ");
                                            String nuevoValor = scanner.nextLine().trim();

                                            if (nuevoValor.equalsIgnoreCase("q")) {
                                                System.out.println("Actualización cancelada. Volviendo al menú CRUD...");
                                                return;
                                            }

                                            // Si nuevoValor está vacío, no hacemos nada y pasamos al siguiente campo
                                            if (!nuevoValor.isEmpty()) {
                                                switch (campo) {
                                                    case "Autor Personas":
                                                        libroExistente.setAutor_personas(nuevoValor);
                                                        break;
                                                    case "Autor Entidades":
                                                        libroExistente.setAutor_entidades(nuevoValor);
                                                        break;
                                                    case "Título":
                                                        libroExistente.setTitulo(nuevoValor);
                                                        break;
                                                    case "Editorial":
                                                        libroExistente.setEditorial(nuevoValor);
                                                        break;
                                                    case "Lugar Publicación":
                                                        libroExistente.setLugar_publicacion(nuevoValor);
                                                        break;
                                                    case "Fecha Publicación":
                                                        libroExistente.setFecha_publicacion(nuevoValor);
                                                        break;
                                                    case "Descripción Física":
                                                        libroExistente.setDescripcion_fisica(nuevoValor);
                                                        break;
                                                    case "Descripción Notas":
                                                        libroExistente.setDescripcion_notas(nuevoValor);
                                                        break;
                                                    case "Género Forma":
                                                        libroExistente.setGenero_forma(nuevoValor);
                                                        break;
                                                    case "Lugar Relacionado":
                                                        libroExistente.setLugar_relacionado(nuevoValor);
                                                        break;
                                                    case "País Publicación":
                                                        libroExistente.setPais_publicacion(nuevoValor);
                                                        break;
                                                    case "Lengua Publicación":
                                                        libroExistente.setLengua_publicacion(nuevoValor);
                                                        break;
                                                    case "Tipo Documento":
                                                        libroExistente.setTipo_documento(nuevoValor);
                                                        break;
                                                    case "Signatura":
                                                        libroExistente.setSignatura(nuevoValor);
                                                        break;
                                                    case "Versión Digital":
                                                        libroExistente.setVersion_digital(nuevoValor);
                                                        break;
                                                    case "Texto OCR":
                                                        libroExistente.setTexto_OCR(nuevoValor);
                                                        break;
                                                }
                                            }
                                        }

                                        try {
                                            LibroController.actualizarLibro(libroExistente);
                                            System.out.println("Libro actualizado correctamente.");

                                            // Mostrar detalles del libro actualizado con manejo seguro de nulos
                                            System.out.println("\n--- Detalles del Libro Actualizado ---");
                                            System.out.println("|---------------------|---------------------------------------|");
                                            System.out.println("| ID                  | " + (libroExistente.getId_BNE() != null ? libroExistente.getId_BNE() : "No disponible") + " |");
                                            System.out.println("| Autor Personas      | " + (libroExistente.getAutor_personas() != null ? libroExistente.getAutor_personas() : "No disponible") + " |");
                                            System.out.println("| Autor Entidades     | " + (libroExistente.getAutor_entidades() != null ? libroExistente.getAutor_entidades() : "No disponible") + " |");
                                            System.out.println("| Título              | " + (libroExistente.getTitulo() != null ? libroExistente.getTitulo() : "No disponible") + " |");
                                            System.out.println("| Editorial           | " + (libroExistente.getEditorial() != null ? libroExistente.getEditorial() : "No disponible") + " |");
                                            System.out.println("| Lugar Publicación   | " + (libroExistente.getLugar_publicacion() != null ? libroExistente.getLugar_publicacion() : "No disponible") + " |");
                                            System.out.println("| Fecha Publicación   | " + (libroExistente.getFecha_publicacion() != null ? libroExistente.getFecha_publicacion() : "No disponible") + " |");
                                            System.out.println("| Descripción Física  | " + (libroExistente.getDescripcion_fisica() != null ? libroExistente.getDescripcion_fisica() : "No disponible") + " |");
                                            System.out.println("| Descripción Notas   | " + (libroExistente.getDescripcion_notas() != null ? libroExistente.getDescripcion_notas() : "No disponible") + " |");
                                            System.out.println("| Género Forma        | " + (libroExistente.getGenero_forma() != null ? libroExistente.getGenero_forma() : "No disponible") + " |");
                                            System.out.println("| Lugar Relacionado   | " + (libroExistente.getLugar_relacionado() != null ? libroExistente.getLugar_relacionado() : "No disponible") + " |");
                                            System.out.println("| País Publicación    | " + (libroExistente.getPais_publicacion() != null ? libroExistente.getPais_publicacion() : "No disponible") + " |");
                                            System.out.println("| Lengua Publicación  | " + (libroExistente.getLengua_publicacion() != null ? libroExistente.getLengua_publicacion() : "No disponible") + " |");
                                            System.out.println("| Tipo Documento      | " + (libroExistente.getTipo_documento() != null ? libroExistente.getTipo_documento() : "No disponible") + " |");
                                            System.out.println("| Signatura           | " + (libroExistente.getSignatura() != null ? libroExistente.getSignatura() : "No disponible") + " |");
                                            System.out.println("| Versión Digital     | " + (libroExistente.getVersion_digital() != null ? libroExistente.getVersion_digital() : "No disponible") + " |");
                                            System.out.println("| Texto OCR           | " + (libroExistente.getTexto_OCR() != null ? libroExistente.getTexto_OCR() : "No disponible") + " |");
                                            System.out.println("|---------------------|---------------------------------------|");
                                        } catch (Exception e) {
                                            System.out.println("Ocurrió un error al actualizar o mostrar el libro: " + e.getMessage());
                                            e.printStackTrace();
                                        }
                                        break;
                                    case 4:
                                        // Eliminar libro

                                        try {
                                            //List<Libro> libros = LibroController.obtenerLibros();
                                            libros = LibroController.obtenerLibros();
                                            if (libros == null || libros.isEmpty()) {
                                                System.out.println("No hay libros en la base de datos para eliminar.");
                                                break;
                                            }

                                            System.out.println("\n--- Lista de Libros Disponibles ---");
                                            System.out.printf("| %-10s | %-30s | %-30s | %-30s |\n", "ID", "Título", "Autor Personas", "Editorial");
                                            System.out.println("|------------|--------------------------------|--------------------------------|--------------------------------|");
                                            for (Libro libro : libros) {
                                                System.out.printf("| %-10s | %-30s | %-30s | %-30s |\n",
                                                        truncateString(libro.getId_BNE(), 10),
                                                        truncateString(libro.getTitulo(), 30),
                                                        truncateString(libro.getAutor_personas(), 30),
                                                        truncateString(libro.getEditorial(), 30)
                                                );
                                            }
                                            System.out.println();

                                            int intentos = 0;
                                            final int MAX_INTENTOS = 3;
                                            while (intentos < MAX_INTENTOS) {
                                                System.out.print("Ingrese el id_BNE del libro a eliminar ('q' para volver): ");
                                                String idBNEEliminar = scanner.nextLine().trim();

                                                if (idBNEEliminar.equalsIgnoreCase("q")) {
                                                    System.out.println("Operación cancelada. Volviendo al menú CRUD...");
                                                    break;
                                                }

                                                if (idBNEEliminar.isEmpty()) {
                                                    System.out.println("El ID no puede estar vacío. Por favor, intente de nuevo.");
                                                    continue;
                                                }

                                                Libro libroAEliminar = LibroController.obtenerLibroPorId(idBNEEliminar);
                                                if (libroAEliminar == null) {
                                                    System.out.println("No se encontró un libro con ese ID.");
                                                    intentos++;
                                                    if (intentos < MAX_INTENTOS) {
                                                        System.out.println("Intentos restantes: " + (MAX_INTENTOS - intentos));
                                                    }
                                                    continue;
                                                }

                                                // Mostrar detalles del libro antes de eliminar
                                                System.out.println("\n--- Detalles del Libro a Eliminar ---");
                                                System.out.println("ID: " + libroAEliminar.getId_BNE());
                                                System.out.println("Título: " + (libroAEliminar.getTitulo() != null ? libroAEliminar.getTitulo() : "No disponible"));
                                                System.out.println("Autor: " + (libroAEliminar.getAutor_personas() != null ? libroAEliminar.getAutor_personas() : "No disponible"));
                                                System.out.println("Editorial: " + (libroAEliminar.getEditorial() != null ? libroAEliminar.getEditorial() : "No disponible"));

                                                System.out.print("¿Está seguro de que desea eliminar este libro? (s/n): ");
                                                String confirmacion = scanner.nextLine().trim().toLowerCase();

                                                if (confirmacion.equals("s")) {
                                                    LibroController.eliminarLibro(idBNEEliminar);
                                                    System.out.println("Libro eliminado correctamente.");
                                                } else {
                                                    System.out.println("Eliminación cancelada.");
                                                }

                                                break;
                                            }

                                            if (intentos == MAX_INTENTOS) {
                                                System.out.println("Se ha excedido el número máximo de intentos. Volviendo al menú CRUD...");
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Ocurrió un error al procesar la operación: " + e.getMessage());
                                            e.printStackTrace();
                                        }
                                        break;
                                    case 5:
                                        System.out.println("Volviendo al menú principal...");
                                        salirCRUD = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida.");
                                }
                            }catch (NumberFormatException e){
                                System.out.println("Entrada inválida. Por favor, ingrese un número entre 1 y 5 según las opciones.");
                            }

                        }
                        break;
                    case 2:
                        Map<String, Integer> metricaEditorial = LibroController.obtenerMetricaEditorial();
                        System.out.println("\n--- Métrica de Ediadmioriales ---");
                        for (Map.Entry<String, Integer> entry : metricaEditorial.entrySet()) {
                            System.out.println(entry.getKey() + ": " + entry.getValue());
                        }
                        break;
                    case 3:
                        administrarUsuarios(scanner);
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entre 1 y 4 según las opciones.");
            }
        }
    }

    private static void menuResponsable() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Responsable ---");
            System.out.println("1. Operaciones CRUD");
            System.out.println("2. Salir");
            System.out.print("Ingrese una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    boolean salirCRUD = false;
                    while (!salirCRUD) {
                        System.out.println("\n--- Operaciones CRUD ---");
                        System.out.println("1. Crear libro");
                        System.out.println("2. Obtener libros");
                        System.out.println("3. Actualizar libro");
                        System.out.println("4. Eliminar libro");
                        System.out.println("5. Volver al menú principal");
                        System.out.print("Ingrese una opción: ");
                        int opcionCRUD = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea

                        switch (opcionCRUD) {
                            case 1:
                                System.out.println("-------Datos del nuevo libro-------");
                                System.out.print("ID: ");
                                String id = scanner.nextLine();
                                System.out.print("Autor Personas: ");
                                String autorPersonas = scanner.nextLine();
                                System.out.print("Autor Entidades: ");
                                String autorEntidades = scanner.nextLine();
                                System.out.print("Título: ");
                                String titulo = scanner.nextLine();
                                System.out.print("Editorial: ");
                                String editorial = scanner.nextLine();
                                System.out.print("Lugar Publicación: ");
                                String lugarPublicacion = scanner.nextLine();
                                System.out.print("Fecha Publicación: ");
                                String fechaPublicacion = scanner.nextLine();
                                System.out.print("Descripción Física: ");
                                String descripcionFisica = scanner.nextLine();
                                System.out.print("Descripción Notas: ");
                                String descripcionNotas = scanner.nextLine();
                                System.out.print("Género Forma: ");
                                String generoForma = scanner.nextLine();
                                System.out.print("Lugar Relacionado: ");
                                String lugarRelacionado = scanner.nextLine();
                                System.out.print("País Publicación: ");
                                String paisPublicacion = scanner.nextLine();
                                System.out.print("Lengua Publicación: ");
                                String lenguaPublicacion = scanner.nextLine();
                                System.out.print("Tipo Documento: ");
                                String tipoDocumento = scanner.nextLine();
                                System.out.print("Signatura: ");
                                String signatura = scanner.nextLine();
                                System.out.print("Versión Digital: ");
                                String versionDigital = scanner.nextLine();
                                System.out.print("Texto OCR: ");
                                String textoOCR = scanner.nextLine();
                                try {
                                    Libro nuevoLibro = new Libro();
                                    nuevoLibro.setId_BNE(id);
                                    nuevoLibro.setAutor_personas(autorPersonas);
                                    nuevoLibro.setAutor_entidades(autorEntidades);
                                    nuevoLibro.setTitulo(titulo);
                                    nuevoLibro.setEditorial(editorial);
                                    nuevoLibro.setLugar_publicacion(lugarPublicacion);
                                    nuevoLibro.setFecha_publicacion(fechaPublicacion);
                                    nuevoLibro.setDescripcion_fisica(descripcionFisica);
                                    nuevoLibro.setDescripcion_notas(descripcionNotas);
                                    nuevoLibro.setGenero_forma(generoForma);
                                    nuevoLibro.setLugar_relacionado(lugarRelacionado);
                                    nuevoLibro.setPais_publicacion(paisPublicacion);
                                    nuevoLibro.setLengua_publicacion(lenguaPublicacion);
                                    nuevoLibro.setTipo_documento(tipoDocumento);
                                    nuevoLibro.setSignatura(signatura);
                                    nuevoLibro.setVersion_digital(versionDigital);
                                    nuevoLibro.setTexto_OCR(textoOCR);

                                    LibroController.crearLibro(nuevoLibro);
                                    System.out.println("Libro creado correctamente.");
                                    System.out.println("Aquí están los detalles del libro que has agregado:");
                                    System.out.println("| ID                  | " + nuevoLibro.getId_BNE() + " |");
                                    System.out.println("| Autor Personas      | " + nuevoLibro.getAutor_personas() + " |");
                                    System.out.println("| Autor Entidades     | " + nuevoLibro.getAutor_entidades() + " |");
                                    System.out.println("| Título              | " + nuevoLibro.getTitulo() + " |");
                                    System.out.println("| Editorial           | " + nuevoLibro.getEditorial() + " |");
                                    System.out.println("| Lugar Publicación   | " + nuevoLibro.getLugar_publicacion() + " |");
                                    System.out.println("| Fecha Publicación   | " + nuevoLibro.getFecha_publicacion() + " |");
                                    System.out.println("| Descripción Física  | " + nuevoLibro.getDescripcion_fisica() + " |");
                                    System.out.println("| Descripción Notas   | " + nuevoLibro.getDescripcion_notas() + " |");
                                    System.out.println("| Género Forma        | " + nuevoLibro.getGenero_forma() + " |");
                                    System.out.println("| Lugar Relacionado   | " + nuevoLibro.getLugar_relacionado() + " |");
                                    System.out.println("| País Publicación    | " + nuevoLibro.getPais_publicacion() + " |");
                                    System.out.println("| Lengua Publicación  | " + nuevoLibro.getLengua_publicacion() + " |");
                                    System.out.println("| Tipo Documento      | " + nuevoLibro.getTipo_documento() + " |");
                                    System.out.println("| Signatura           | " + nuevoLibro.getSignatura() + " |");
                                    System.out.println("| Versión Digital     | " + nuevoLibro.getVersion_digital() + " |");
                                    System.out.println("| Texto OCR           | " + nuevoLibro.getTexto_OCR() + " |");
                                } catch (Exception e) {
                                    System.out.println("Ocurrió un error al crear el libro: " + e.getMessage());
                                }
                                break;

                            case 2:
                                List<Libro> libros = LibroController.obtenerLibros();
                                System.out.println("\n--- Lista de Libros ---");
                                System.out.printf("| %-10s | %-30s | %-30s | %-30s |\n", "ID", "Editorial", "Autor Personas", "Autor Entidades");
                                for (Libro libro : libros) {
                                    System.out.printf("| %-10s | %-30s | %-30s | %-30s |\n", libro.getId_BNE(), libro.getEditorial(), libro.getAutor_personas(), libro.getAutor_entidades());
                                }

                                System.out.print("\nIngrese el id_BNE del libro a consultar: ");
                                String idBNEConsultar = scanner.nextLine();
                                Libro libroConsultado = LibroController.obtenerLibroPorId(idBNEConsultar);
                                if (libroConsultado != null) {
                                    System.out.println("\n--- Detalles del Libro ---");
                                    System.out.println(libroConsultado);
                                } else {
                                    System.out.println("No se encontró un libro con ese id_BNE.");
                                }
                                break;
                            case 3:
                                System.out.print("Ingrese el id_BNE del libro a modificar: ");
                                String idBNEActualizar = scanner.nextLine();
                                Libro libroActualizar = LibroController.obtenerLibroPorId(idBNEActualizar);
                                if (libroActualizar != null) {
                                    System.out.println("\n");
                                    System.out.println("1. Título");
                                    System.out.println("2. Autor");
                                    System.out.println("3. Editorial");
                                    System.out.println("4. Lugar de publicación");
                                    System.out.println("5. Fecha de publicación");
                                    System.out.println("6. Descripción física");
                                    System.out.println("7. Notas de descripción");
                                    System.out.println("8. Género/Forma");
                                    System.out.println("9. Lugar relacionado");
                                    System.out.println("10. País de publicación");
                                    System.out.println("11. Lengua de publicación");
                                    System.out.println("12. Tipo de documento");
                                    System.out.println("13. Signatura");
                                    System.out.println("14. Versión digital");
                                    System.out.println("15. Texto OCR");
                                    System.out.print("Seleccione el campo que desea modificar:");
                                    String opcionMenu = scanner.nextLine();
                                    switch (opcionMenu) {
                                        case "1":
                                            System.out.print("Ingrese el nuevo título (o deje en blanco para mantener el actual): ");
                                            String nuevoTitulo = scanner.nextLine();
                                            if (!nuevoTitulo.isEmpty()) {
                                                libroActualizar.setTitulo(nuevoTitulo);
                                            }
                                            break;
                                        case "2":
                                            System.out.print("Ingrese el nuevo autor (o deje en blanco para mantener el actual): ");
                                            String nuevoAutor = scanner.nextLine();
                                            if (!nuevoAutor.isEmpty()) {
                                                libroActualizar.setAutor_personas(nuevoAutor);
                                            }
                                            break;
                                        case "3":
                                            System.out.print("Ingrese la nueva editorial (o deje en blanco para mantener la actual): ");
                                            String nuevaEditorial = scanner.nextLine();
                                            if (!nuevaEditorial.isEmpty()) {
                                                libroActualizar.setEditorial(nuevaEditorial);
                                            }
                                            break;
                                        case "4":
                                            System.out.print("Ingrese el nuevo lugar de publicación (o deje en blanco para mantener el actual): ");
                                            String nuevoLugarPublicacion = scanner.nextLine();
                                            if (!nuevoLugarPublicacion.isEmpty()) {
                                                libroActualizar.setLugar_publicacion(nuevoLugarPublicacion);
                                            }
                                            break;
                                        case "5":
                                            System.out.print("Ingrese la nueva fecha de publicación (o deje en blanco para mantener la actual): ");
                                            String nuevaFechaPublicacion = scanner.nextLine();
                                            if (!nuevaFechaPublicacion.isEmpty()) {
                                                libroActualizar.setFecha_publicacion(nuevaFechaPublicacion);
                                            }
                                            break;
                                        case "6":
                                            System.out.print("Ingrese la nueva descripción física (o deje en blanco para mantener la actual): ");
                                            String nuevaDescripcionFisica = scanner.nextLine();
                                            if (!nuevaDescripcionFisica.isEmpty()) {
                                                libroActualizar.setDescripcion_fisica(nuevaDescripcionFisica);
                                            }
                                            break;
                                        case "7":
                                            System.out.print("Ingrese las nuevas notas de descripción (o deje en blanco para mantener las actuales): ");
                                            String nuevaDescripcionNotas = scanner.nextLine();
                                            if (!nuevaDescripcionNotas.isEmpty()) {
                                                libroActualizar.setDescripcion_notas(nuevaDescripcionNotas);
                                            }
                                            break;
                                        case "8":
                                            System.out.print("Ingrese el nuevo género/forma (o deje en blanco para mantener el actual): ");
                                            String nuevoGeneroForma = scanner.nextLine();
                                            if (!nuevoGeneroForma.isEmpty()) {
                                                libroActualizar.setGenero_forma(nuevoGeneroForma);
                                            }
                                            break;
                                        case "9":
                                            System.out.print("Ingrese el nuevo lugar relacionado (o deje en blanco para mantener el actual): ");
                                            String nuevoLugarRelacionado = scanner.nextLine();
                                            if (!nuevoLugarRelacionado.isEmpty()) {
                                                libroActualizar.setLugar_relacionado(nuevoLugarRelacionado);
                                            }
                                            break;
                                        case "10":
                                            System.out.print("Ingrese el nuevo país de publicación (o deje en blanco para mantener el actual): ");
                                            String nuevoPaisPublicacion = scanner.nextLine();
                                            if (!nuevoPaisPublicacion.isEmpty()) {
                                                libroActualizar.setPais_publicacion(nuevoPaisPublicacion);
                                            }
                                            break;
                                        case "11":
                                            System.out.print("Ingrese la nueva lengua de publicación (o deje en blanco para mantener la actual): ");
                                            String nuevaLenguaPublicacion = scanner.nextLine();
                                            if (!nuevaLenguaPublicacion.isEmpty()) {
                                                libroActualizar.setLengua_publicacion(nuevaLenguaPublicacion);
                                            }
                                            break;
                                        case "12":
                                            System.out.print("Ingrese el nuevo tipo de documento (o deje en blanco para mantener el actual): ");
                                            String nuevoTipoDocumento = scanner.nextLine();
                                            if (!nuevoTipoDocumento.isEmpty()) {
                                                libroActualizar.setTipo_documento(nuevoTipoDocumento);
                                            }
                                            break;
                                        case "13":
                                            System.out.print("Ingrese la nueva signatura (o deje en blanco para mantener la actual): ");
                                            String nuevaSignatura = scanner.nextLine();
                                            if (!nuevaSignatura.isEmpty()) {
                                                libroActualizar.setSignatura(nuevaSignatura);
                                            }
                                            break;
                                        case "14":
                                            System.out.print("Ingrese la nueva versión digital (o deje en blanco para mantener la actual): ");
                                            String nuevaVersionDigital = scanner.nextLine();
                                            if (!nuevaVersionDigital.isEmpty()) {
                                                libroActualizar.setVersion_digital(nuevaVersionDigital);
                                            }
                                            break;
                                        case "15":
                                            System.out.print("Ingrese el nuevo texto OCR (o deje en blanco para mantener el actual): ");
                                            String nuevoTextoOCR = scanner.nextLine();
                                            if (!nuevoTextoOCR.isEmpty()) {
                                                libroActualizar.setTexto_OCR(nuevoTextoOCR);
                                            }
                                            break;
                                        default:
                                            System.out.println("Opción no válida.");
                                            break;
                                    }
                                    LibroController.actualizarLibro(libroActualizar);
                                    LibroController.guardarLibrosEnJSON();
                                    System.out.println("Libro actualizado correctamente.");
                                } else {
                                    System.out.println("No se encontró un libro con ese id_BNE.");
                                }
                                break;
                            case 4:
                                // Eliminar libro
                                System.out.print("Ingrese el id_BNE del libro a eliminar: ");
                                String idBNEEliminar = scanner.nextLine();
                                LibroController.eliminarLibro(idBNEEliminar);
                                System.out.println("Libro eliminado correctamente.");
                                break;
                            case 5:
                                salirCRUD = true;
                                break;
                            default:
                                System.out.println("Opción no válida.");
                        }

                    }
                    break;
                case 2:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }


    private static void consultarLibro(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese el id_BNE del libro a consultar ('q' para cancelar y volver al menú anterior): ");
            String idBNEConsultar = scanner.nextLine();
            if (idBNEConsultar.equalsIgnoreCase("q")) {
                System.out.println("Operación cancelada. Volviendo al menú anterior...");
                return;
            }
            System.out.println("Buscando...");
            try {
                Thread.sleep(1000); // Pausa de 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Libro libroConsultado = LibroController.obtenerLibroPorId(idBNEConsultar);
            if (libroConsultado != null) {
                System.out.println("\n--- Detalles del Libro ---");
                System.out.println(libroConsultado);
                return;
            } else {
                System.out.println("No se encontró un libro con ese id_BNE. Por favor, intente de nuevo.");
            }
        }
    }

    private static void modificarLibro(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese el id_BNE del libro a modificar ('q' para cancelar y volver al menú anterior): ");
            String idBNEActualizar = scanner.nextLine();
            if (idBNEActualizar.equalsIgnoreCase("q")) {
                System.out.println("Operación cancelada. Volviendo al menú anterior...");
                return;
            }
            Libro libroActualizar = LibroController.obtenerLibroPorId(idBNEActualizar);
            if (libroActualizar != null) {
                boolean campoSeleccionado = false;
                while (!campoSeleccionado) {
                    System.out.println("\n");
                    System.out.println("1. Título");
                    System.out.println("2. Autor");
                    System.out.println("3. Editorial");
                    System.out.println("4. Lugar de publicación");
                    System.out.println("5. Fecha de publicación");
                    System.out.println("6. Descripción física");
                    System.out.println("7. Notas de descripción");
                    System.out.println("8. Género/Forma");
                    System.out.println("9. Lugar relacionado");
                    System.out.println("10. País de publicación");
                    System.out.println("11. Lengua de publicación");
                    System.out.println("12. Tipo de documento");
                    System.out.println("13. Signatura");
                    System.out.println("14. Versión digital");
                    System.out.println("15. Texto OCR");
                    System.out.print("Seleccione el campo que desea modificar ('q' para regresar al menú anterior): ");
                    String opcionMenu = scanner.nextLine();

                    if (opcionMenu.equalsIgnoreCase("q")) {
                        System.out.println("Volviendo al menú anterior...");
                        return;
                    }
                    try {
                        int opcion = Integer.parseInt(opcionMenu);
                        if (opcion < 1 || opcion > 15) {
                            throw new NumberFormatException();
                        }
                        switch (opcionMenu) {
                            case "1":
                                System.out.print("Ingrese el nuevo título (o deje en blanco para mantener el actual): ");
                                String nuevoTitulo = scanner.nextLine();
                                if (!nuevoTitulo.isEmpty()) {
                                    libroActualizar.setTitulo(nuevoTitulo);
                                }
                                break;
                            case "2":
                                System.out.print("Ingrese el nuevo autor (o deje en blanco para mantener el actual): ");
                                String nuevoAutor = scanner.nextLine();
                                if (!nuevoAutor.isEmpty()) {
                                    libroActualizar.setAutor_personas(nuevoAutor);
                                }
                                break;
                            case "3":
                                System.out.print("Ingrese la nueva editorial (o deje en blanco para mantener la actual): ");
                                String nuevaEditorial = scanner.nextLine();
                                if (!nuevaEditorial.isEmpty()) {
                                    libroActualizar.setEditorial(nuevaEditorial);
                                }
                                break;
                            case "4":
                                System.out.print("Ingrese el nuevo lugar de publicación (o deje en blanco para mantener el actual): ");
                                String nuevoLugarPublicacion = scanner.nextLine();
                                if (!nuevoLugarPublicacion.isEmpty()) {
                                    libroActualizar.setLugar_publicacion(nuevoLugarPublicacion);
                                }
                                break;
                            case "5":
                                System.out.print("Ingrese la nueva fecha de publicación (o deje en blanco para mantener la actual): ");
                                String nuevaFechaPublicacion = scanner.nextLine();
                                if (!nuevaFechaPublicacion.isEmpty()) {
                                    libroActualizar.setFecha_publicacion(nuevaFechaPublicacion);
                                }
                                break;
                            case "6":
                                System.out.print("Ingrese la nueva descripción física (o deje en blanco para mantener la actual): ");
                                String nuevaDescripcionFisica = scanner.nextLine();
                                if (!nuevaDescripcionFisica.isEmpty()) {
                                    libroActualizar.setDescripcion_fisica(nuevaDescripcionFisica);
                                }
                                break;
                            case "7":
                                System.out.print("Ingrese las nuevas notas de descripción (o deje en blanco para mantener las actuales): ");
                                String nuevaDescripcionNotas = scanner.nextLine();
                                if (!nuevaDescripcionNotas.isEmpty()) {
                                    libroActualizar.setDescripcion_notas(nuevaDescripcionNotas);
                                }
                                break;
                            case "8":
                                System.out.print("Ingrese el nuevo género/forma (o deje en blanco para mantener el actual): ");
                                String nuevoGeneroForma = scanner.nextLine();
                                if (!nuevoGeneroForma.isEmpty()) {
                                    libroActualizar.setGenero_forma(nuevoGeneroForma);
                                }
                                break;
                            case "9":
                                System.out.print("Ingrese el nuevo lugar relacionado (o deje en blanco para mantener el actual): ");
                                String nuevoLugarRelacionado = scanner.nextLine();
                                if (!nuevoLugarRelacionado.isEmpty()) {
                                    libroActualizar.setLugar_relacionado(nuevoLugarRelacionado);
                                }
                                break;
                            case "10":
                                System.out.print("Ingrese el nuevo país de publicación (o deje en blanco para mantener el actual): ");
                                String nuevoPaisPublicacion = scanner.nextLine();
                                if (!nuevoPaisPublicacion.isEmpty()) {
                                    libroActualizar.setPais_publicacion(nuevoPaisPublicacion);
                                }
                                break;
                            case "11":
                                System.out.print("Ingrese la nueva lengua de publicación (o deje en blanco para mantener la actual): ");
                                String nuevaLenguaPublicacion = scanner.nextLine();
                                if (!nuevaLenguaPublicacion.isEmpty()) {
                                    libroActualizar.setLengua_publicacion(nuevaLenguaPublicacion);
                                }
                                break;
                            case "12":
                                System.out.print("Ingrese el nuevo tipo de documento (o deje en blanco para mantener el actual): ");
                                String nuevoTipoDocumento = scanner.nextLine();
                                if (!nuevoTipoDocumento.isEmpty()) {
                                    libroActualizar.setTipo_documento(nuevoTipoDocumento);
                                }
                                break;
                            case "13":
                                System.out.print("Ingrese la nueva signatura (o deje en blanco para mantener la actual): ");
                                String nuevaSignatura = scanner.nextLine();
                                if (!nuevaSignatura.isEmpty()) {
                                    libroActualizar.setSignatura(nuevaSignatura);
                                }
                                break;
                            case "14":
                                System.out.print("Ingrese la nueva versión digital (o deje en blanco para mantener la actual): ");
                                String nuevaVersionDigital = scanner.nextLine();
                                if (!nuevaVersionDigital.isEmpty()) {
                                    libroActualizar.setVersion_digital(nuevaVersionDigital);
                                }
                                break;
                            case "15":
                                System.out.print("Ingrese el nuevo texto OCR (o deje en blanco para mantener el actual): ");
                                String nuevoTextoOCR = scanner.nextLine();
                                if (!nuevoTextoOCR.isEmpty()) {
                                    libroActualizar.setTexto_OCR(nuevoTextoOCR);
                                }
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                        campoSeleccionado = true;
                        LibroController.actualizarLibro(libroActualizar);
                        System.out.println("Libro actualizado correctamente.");
                    } catch (NumberFormatException e) {
                        System.out.println("Opción inválida. Por favor ingrese una opción entre 1 y 15 ('q' para regresar al menú anterior).");
                    }
            }
            return;
            }else {
                System.out.println("No se encontró un libro con ese id_BNE. Por favor, intente de nuevo.");
            }
        }
    }

    private static void menuUsuario() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Usuario ---");
            System.out.println("1. Consultar libros");
            System.out.println("2. Modificar libro");
            System.out.println("3. Salir");
            System.out.print("Ingrese una opción: ");

            String input = scanner.nextLine();
            int opcion;

            try{
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:
                        consultarLibro(scanner);
                        break;
                    case 2:
                        modificarLibro(scanner);
                        break;
                    case 3:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            }catch(NumberFormatException e){
                System.out.println("Entrada inválida. Por favor, ingrese un número entre 1 y 3 según las opciones.");
            }
        }
    }

    private static String truncateString(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }
}