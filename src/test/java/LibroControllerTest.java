package test.java;
import main.java.controllers.LibroController;
import main.java.models.Libro;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class LibroControllerTest {
        @Test
        public void testCrearLibro() {
            Libro libro = new Libro("id_BNE", "autor_personas", "autor_entidades", "titulo", "editorial", "lugar_publicacion", "fecha_publicacion", "descripcion_fisica", "descripcion_notas", "genero_forma", "lugar_relacionado", "pais_publicacion", "lengua_publicacion", "tipo_documento", "signatura", "version_digital", "texto_OCR");
            LibroController.crearLibro(libro);
            List<Libro> libros = LibroController.obtenerLibros();
            assertTrue(libros.contains(libro));
        }
        @Test
        public void testActualizarLibro() {
            // Crear un libro y añadirlo a la lista de libros
            Libro libro = new Libro("id_BNE", "autor_personas", "autor_entidades", "titulo", "editorial", "lugar_publicacion", "fecha_publicacion", "descripcion_fisica", "descripcion_notas", "genero_forma", "lugar_relacionado", "pais_publicacion", "lengua_publicacion", "tipo_documento", "signatura", "version_digital", "texto_OCR");
            LibroController.crearLibro(libro);

            // Actualizar el libro
            libro.setTitulo("nuevo_titulo");
            LibroController.actualizarLibro(libro);

            // Comprobar que el libro se actualizó correctamente
            List<Libro> libros = LibroController.obtenerLibros();
            for (Libro l : libros) {
                if (l.getId_BNE().equals(libro.getId_BNE())) {
                    assertEquals("nuevo_titulo", l.getTitulo());
                }
            }
        }

        @Test
        public void testEliminarLibro() {
            // Crear un libro y añadirlo a la lista de libros
            Libro libro = new Libro("id_BNE", "autor_personas", "autor_entidades", "titulo", "editorial", "lugar_publicacion", "fecha_publicacion", "descripcion_fisica", "descripcion_notas", "genero_forma", "lugar_relacionado", "pais_publicacion", "lengua_publicacion", "tipo_documento", "signatura", "version_digital", "texto_OCR");
            LibroController.crearLibro(libro);

            // Eliminar el libro
            LibroController.eliminarLibro(libro.getId_BNE());

            // Comprobar que el libro se eliminó correctamente
            List<Libro> libros = LibroController.obtenerLibros();
            assertFalse(libros.contains(libro));
        }
}
