package test.java;

import org.junit.Test;
import static org.junit.Assert.*;

public class UsuarioTest {
    @Test
    public void testConsultarYModificarContenido() {
        // Crear un usuario
        Usuario usuario = new Usuario();

        // Prueba de consulta
        String contenido = usuario.consultarContenido("set_libros_1.json");
        assertNotNull(contenido);

        // Prueba de modificación
        boolean resultado = usuario.modificarContenido("set_libros_1.json", "nuevo contenido");
        assertTrue(resultado);
    }
}
