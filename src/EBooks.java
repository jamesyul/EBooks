import java.util.Scanner;
import java.util.List;
import java.util.Map;
import main.java.models.Libro;
import main.java.models.Usuario;
import main.java.controllers.LibroController;
import main.java.controllers.UsuarioController;

public class EBooks {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su nombre de usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        if (UsuarioController.autenticarUsuario(usuario, password)) {
            String perfil = UsuarioController.obtenerPerfilUsuario(usuario, password);
            System.out.println("Bienvenido, " + usuario + ". Tu perfil es: " + perfil);

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
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    private static void menuAdministrador() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Administrador ---");
            System.out.println("1. Operaciones CRUD");
            System.out.println("2. Mostrar métrica de editoriales");
            System.out.println("3. Salir");
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
                                // Crear libro
                                System.out.print("Ingrese los datos del libro (separados por comas): ");
                                String datosLibro = scanner.nextLine();
                                String[] datos = datosLibro.split(",");
                                Libro nuevoLibro = new Libro(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], datos[8], datos[9], datos[10], datos[11], datos[12], datos[13], datos[14], datos[15], datos[16]);
                                LibroController.crearLibro(nuevoLibro);
                                System.out.println("Libro creado correctamente.");
                                break;
                            case 2:
                                // Obtener libros
                                List<Libro> libros = LibroController.obtenerLibros();
                                System.out.println("\n--- Lista de Libros ---");
                                for (Libro libro : libros) {
                                    System.out.println(libro);
                                }
                                break;
                            case 3:
                                // Actualizar libro
                                System.out.print("Ingrese el id_BNE del libro a actualizar: ");
                                String idBNEActualizar = scanner.nextLine();
                                System.out.print("Ingrese los nuevos datos del libro (separados por comas): ");
                                String datosLibroActualizar = scanner.nextLine();
                                String[] datosActualizar = datosLibroActualizar.split(",");
                                Libro libroActualizado = new Libro(datosActualizar[0], datosActualizar[1], datosActualizar[2], datosActualizar[3], datosActualizar[4], datosActualizar[5], datosActualizar[6], datosActualizar[7], datosActualizar[8], datosActualizar[9], datosActualizar[10], datosActualizar[11], datosActualizar[12], datosActualizar[13], datosActualizar[14], datosActualizar[15], datosActualizar[16]);
                                libroActualizado.setId_BNE(idBNEActualizar);
                                LibroController.actualizarLibro(libroActualizado);
                                System.out.println("Libro actualizado correctamente.");
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
                    Map<String, Integer> metricaEditorial = LibroController.obtenerMetricaEditorial();
                    System.out.println("\n--- Métrica de Editoriales ---");
                    for (Map.Entry<String, Integer> entry : metricaEditorial.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
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
                                // Crear libro
                                System.out.print("Ingrese los datos del libro (separados por comas): ");
                                String datosLibro = scanner.nextLine();
                                String[] datos = datosLibro.split(",");
                                Libro nuevoLibro = new Libro(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], datos[8], datos[9], datos[10], datos[11], datos[12], datos[13], datos[14], datos[15], datos[16]);
                                LibroController.crearLibro(nuevoLibro);
                                System.out.println("Libro creado correctamente.");
                                break;
                            case 2:
                                // Obtener libros
                                List<Libro> libros = LibroController.obtenerLibros();
                                System.out.println("\n--- Lista de Libros ---");
                                for (Libro libro : libros) {
                                    System.out.println(libro);
                                }
                                break;
                            case 3:
                                // Actualizar libro
                                System.out.print("Ingrese el id_BNE del libro a actualizar: ");
                                String idBNEActualizar = scanner.nextLine();
                                System.out.print("Ingrese los nuevos datos del libro (separados por comas): ");
                                String datosLibroActualizar = scanner.nextLine();
                                String[] datosActualizar = datosLibroActualizar.split(",");
                                Libro libroActualizado = new Libro(datosActualizar[0], datosActualizar[1], datosActualizar[2], datosActualizar[3], datosActualizar[4], datosActualizar[5], datosActualizar[6], datosActualizar[7], datosActualizar[8], datosActualizar[9], datosActualizar[10], datosActualizar[11], datosActualizar[12], datosActualizar[13], datosActualizar[14], datosActualizar[15], datosActualizar[16]);
                                libroActualizado.setId_BNE(idBNEActualizar);
                                LibroController.actualizarLibro(libroActualizado);
                                System.out.println("Libro actualizado correctamente.");
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

    private static void menuUsuario() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Usuario ---");
            System.out.println("1. Consultar libros");
            System.out.println("2. Modificar libro");
            System.out.println("3. Salir");
            System.out.print("Ingrese una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Consultar libros
                    List<Libro> libros = LibroController.obtenerLibros();
                    System.out.println("\n--- Lista de Libros ---");
                    for (Libro libro : libros) {
                        System.out.println(libro);
                    }
                    break;
                case 2:
                    // Modificar libro
                    System.out.print("Ingrese el id_BNE del libro a modificar: ");
                    String idBNEActualizar = scanner.nextLine();
                    System.out.print("Ingrese los nuevos datos del libro (separados por comas): ");
                    String datosLibroActualizar = scanner.nextLine();
                    String[] datosActualizar = datosLibroActualizar.split(",");
                    Libro libroActualizado = new Libro(datosActualizar[0], datosActualizar[1], datosActualizar[2], datosActualizar[3], datosActualizar[4], datosActualizar[5], datosActualizar[6], datosActualizar[7], datosActualizar[8], datosActualizar[9], datosActualizar[10], datosActualizar[11], datosActualizar[12], datosActualizar[13], datosActualizar[14], datosActualizar[15], datosActualizar[16]);
                    libroActualizado.setId_BNE(idBNEActualizar);
                    LibroController.actualizarLibro(libroActualizado);
                    System.out.println("Libro actualizado correctamente.");
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

}