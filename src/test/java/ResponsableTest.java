package test.java;

import org.junit.Test;
import static org.junit.Assert.*;


public class ResponsableTest {
    @Test
    public void testCRUDContenido() {
        // Crear un responsable
        Responsable responsable = new Responsable();

        // Prueba de creación
        boolean resultadoCreacion = responsable.crearContenido("set_libros_1.json", "contenido");
        assertTrue(resultadoCreacion);

        // Prueba de consulta
        String contenido = responsable.consultarContenido("set_libros_1.json");
        assertEquals("contenido", contenido);

        // Prueba de modificación
        boolean resultadoModificacion = responsable.modificarContenido("set_libros_1.json", "nuevo contenido");
        assertTrue(resultadoModificacion);

        // Prueba de eliminación
        boolean resultadoEliminacion = responsable.eliminarContenido("set_libros_1.json");
        assertTrue(resultadoEliminacion);
    }
}