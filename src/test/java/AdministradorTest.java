package test.java;

import org.junit.Test;
import static org.junit.Assert.*;
import main.java.models.Usuario;


public class AdministradorTest {
    @Test
    public void testVerMetrica() {
        // Crear un administrador
        Administrador administrador = new Administrador();

        // Prueba de visualización de métrica
        String metrica = administrador.verMetrica();
        assertNotNull(metrica);
    }
}
