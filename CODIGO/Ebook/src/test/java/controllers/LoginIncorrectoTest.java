package test.java.controllers;

import main.java.controllers.UsuarioController;
import org.junit.Test;
import static org.junit.Assert.assertFalse;

public class LoginIncorrectoTest {
    @Test
    public void testLoginIncorrecto() {
        boolean loginIncorrecto = UsuarioController.autenticarUsuario("admin", "wrongpassword");
        assertFalse("Login Incorrecto debería fallar", loginIncorrecto);
    }

    @Test
    public void testLoginUsuarioInexistente() {
        boolean loginIncorrecto = UsuarioController.autenticarUsuario("nonexistent", "nope");
        assertFalse("Login de Usuario Inexistente debería fallar", loginIncorrecto);
    }
}

