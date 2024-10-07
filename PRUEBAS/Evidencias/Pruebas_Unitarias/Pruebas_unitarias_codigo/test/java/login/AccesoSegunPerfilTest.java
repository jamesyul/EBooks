package test.java.login;
import org.junit.Test;
import static org.junit.Assert.*;

import main.java.models.Libro;
import main.java.controllers.LibroController;
import main.java.controllers.UsuarioController;

import java.util.List;

public class AccesoSegunPerfilTest {

    @Test
    public void testAccesoAdmin() {
        assertTrue("Autenticación fallida para administrador", UsuarioController.autenticarUsuario("admin1", "admin1234"));

        try {

            assertNotNull("Administrador debería ver métricas", LibroController.obtenerMetricaEditorial());


            assertTrue("Administrador debería poder crear libro", testCrearLibro());


            assertTrue("Administrador debería poder consultar libros", testConsultarLibros());


            assertTrue("Administrador debería poder modificar libro", testModificarLibro());


            assertTrue("Administrador debería poder eliminar libro", testEliminarLibro());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAccesoResponsable() {
        assertTrue("Autenticación fallida para responsable", UsuarioController.autenticarUsuario("responsable1", "resp1234"));

        try {

            assertTrue("Responsable debería poder crear libro", testCrearLibro());


            assertTrue("Responsable debería poder consultar libros", testConsultarLibros());


            assertTrue("Responsable debería poder modificar libro", testModificarLibro());


            assertTrue("Responsable debería poder eliminar libro", testEliminarLibro());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAccesoUsuario() {
        assertTrue("Autenticación fallida para usuario", UsuarioController.autenticarUsuario("usuario1", "user1234"));

        try {

            assertTrue("Usuario debería poder consultar libros", testConsultarLibros());


            assertTrue("Usuario debería poder modificar libro", testModificarLibro());


            assertFalse("Usuario no debería poder crear libro", testCrearLibroUsuario());


            assertFalse("Usuario no debería poder eliminar libro", testEliminarLibroUsuario());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private boolean testCrearLibro() {
        try {
            Libro libro = new Libro(
                    "12345",                 // id_BNE
                    "Autor Ejemplo",         // autor_personas
                    "Entidad Ejemplo",       // autor_entidades (nuevo campo)
                    "Título Ejemplo",        // titulo (nuevo campo)
                    "Editorial Ejemplo",     // editorial
                    "Madrid",                // lugar_publicacion
                    "2023",                  // fecha_publicacion
                    "200 páginas",           // descripcion_fisica
                    "Ninguna",               // descripcion_notas
                    "Ficción",               // genero_forma
                    "España",                // lugar_relacionado
                    "España",                // pais_publicacion
                    "Español",               // lengua_publicacion
                    "Libro",                 // tipo_documento
                    "FIC123",                // signatura
                    "No",                    // version_digital
                    "Texto de ejemplo"       // texto_OCR
            );
            LibroController.crearLibro(libro);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean testCrearLibroUsuario() {
        try {
            Libro libro = new Libro(
                    "12345",                 // id_BNE
                    "Autor Ejemplo",         // autor_personas
                    "Entidad Ejemplo",       // autor_entidades (nuevo campo)
                    "Título Ejemplo",        // titulo (nuevo campo)
                    "Editorial Ejemplo",     // editorial
                    "Madrid",                // lugar_publicacion
                    "2023",                  // fecha_publicacion
                    "200 páginas",           // descripcion_fisica
                    "Ninguna",               // descripcion_notas
                    "Ficción",               // genero_forma
                    "España",                // lugar_relacionado
                    "España",                // pais_publicacion
                    "Español",               // lengua_publicacion
                    "Libro",                 // tipo_documento
                    "FIC123",                // signatura
                    "No",                    // version_digital
                    "Texto de ejemplo"       // texto_OCR
            );
            LibroController.crearLibro(libro);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private boolean testConsultarLibros() {
        try {
            List<Libro> libros = LibroController.obtenerLibros();
            return libros != null && !libros.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean testModificarLibro() {
        try {
            Libro libro = new Libro(
                    "12345",                 // id_BNE
                    "Autor Modificado",      // autor_personas
                    "Entidad Modificada",    // autor_entidades (nuevo campo)
                    "Título Modificado",     // titulo (nuevo campo)
                    "Editorial Modificada",  // editorial
                    "Barcelona",             // lugar_publicacion (cambiado de Madrid)
                    "2024",                  // fecha_publicacion (cambiado de 2023)
                    "250 páginas",           // descripcion_fisica
                    "Edición revisada",      // descripcion_notas (cambiado de "Ninguna")
                    "No ficción",            // genero_forma (cambiado de "Ficción")
                    "Cataluña",              // lugar_relacionado (cambiado de "España")
                    "España",                // pais_publicacion
                    "Español",               // lengua_publicacion
                    "Libro",                 // tipo_documento
                    "NOF123",                // signatura (cambiado de "FIC123")
                    "Sí",                    // version_digital (cambiado de "No")
                    "Texto de ejemplo modificado" // texto_OCR
            );
            LibroController.actualizarLibro(libro);
            return true;
        } catch (Exception e) {
            System.err.println("Error al modificar libro: " + e.getMessage());
            return false;
        }
    }

    private boolean testEliminarLibro() {
        try {
            LibroController.eliminarLibro("12345");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean testEliminarLibroUsuario() {
        try {
            LibroController.eliminarLibro("12345");
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}