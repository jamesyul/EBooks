package test.java.controllers;

import main.java.controllers.LibroController;
import main.java.controllers.UsuarioController;
import main.java.models.Libro;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccesoSegunPerfilTest {
    @Test
    public void testAccesoAdmin() {
        if (UsuarioController.autenticarUsuario("admin", "admin123")) {
            assertTrue("Administrador debería ver métricas", UsuarioController.obtenerPerfilUsuario("admin", "admin123").equals("administrador"));
            assertTrue("Administrador debería poder crear libro", testCrearLibro());
        }
    }

    @Test
    public void testAccesoResponsable() {
        if (UsuarioController.autenticarUsuario("responsable", "resp123")) {
            assertTrue("Responsable debería poder crear libro", testCrearLibro());
            assertTrue("Responsable debería poder consultar libros", testConsultarLibros());
            assertTrue("Responsable debería poder modificar libro", testModificarLibro());
            assertTrue("Responsable debería poder eliminar libro", testEliminarLibro());
        }
    }

    @Test
    public void testAccesoUsuario() {
        if (UsuarioController.autenticarUsuario("usuario", "user123")) {
            assertTrue("Usuario debería poder consultar libros", testConsultarLibros());
            assertTrue("Usuario debería poder modificar libro", testModificarLibro());
            assertFalse("Usuario no debería poder crear libro", testCrearLibro());
            assertFalse("Usuario no debería poder eliminar libro", testEliminarLibro());
        }
    }

    private boolean testCrearLibro() {
        try {
            Libro libro = new Libro(
                    "12345",                 // id_BNE
                    "Autor Ejemplo",         // autor_personas
                    "Entidad Ejemplo",       // autor_entidades
                    "Título Ejemplo",        // titulo
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
            System.err.println("Error al crear libro: " + e.getMessage());
            return false;
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
}

