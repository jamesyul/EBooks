package test.java.controllers;

import main.java.controllers.UsuarioController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginCorrectoTest {
    private int testsPasados = 0;
    private int testsTotales = 0;

    @Before
    public void setUp() {
        UsuarioController.cargarUsuariosDesdeJSON();
        System.out.println("Iniciando prueba...");
    }

    @Test
    public void testLoginAdministrador() {
        System.out.println("Probando login de Administrador...");
        assertTrue("Login Administrador 1 falló", UsuarioController.autenticarUsuario("admin1", "admin1234"));
        assertTrue("Login Administrador 2 falló", UsuarioController.autenticarUsuario("admin2", "admin5678"));
        assertEquals("Perfil incorrecto para Administrador", "administrador", UsuarioController.obtenerPerfilUsuario("admin1", "admin1234"));
        testsPasados++;
    }

    @Test
    public void testLoginResponsable() {
        System.out.println("Probando login de Responsable...");
        assertTrue("Login Responsable 1 falló", UsuarioController.autenticarUsuario("responsable1", "resp1234"));
        assertTrue("Login Responsable 2 falló", UsuarioController.autenticarUsuario("responsable2", "resp5678"));
        assertEquals("Perfil incorrecto para Responsable", "responsable", UsuarioController.obtenerPerfilUsuario("responsable1", "resp1234"));
        testsPasados++;
    }

    @Test
    public void testLoginUsuario() {
        System.out.println("Probando login de Usuario...");
        assertTrue("Login Usuario 1 falló", UsuarioController.autenticarUsuario("usuario1", "user1234"));
        assertTrue("Login Usuario 2 falló", UsuarioController.autenticarUsuario("usuario2", "user5678"));
        assertEquals("Perfil incorrecto para Usuario", "usuario", UsuarioController.obtenerPerfilUsuario("usuario1", "user1234"));
        testsPasados++;
    }

    @Test
    public void testLoginUsuarioConBiblioteca() {
        System.out.println("Probando login de Usuario con Biblioteca...");
        assertTrue("Login Usuario con Biblioteca falló", UsuarioController.autenticarUsuario("galahad", "12345"));
        assertEquals("Perfil incorrecto para Usuario con Biblioteca", "responsable", UsuarioController.obtenerPerfilUsuario("galahad", "12345"));
        assertEquals("ID de Biblioteca incorrecto", "7181G", UsuarioController.obtenerIdBiblioteca("galahad"));
        testsPasados++;
    }

    @Test
    public void testLoginFallido() {
        System.out.println("Probando login fallido...");
        assertFalse("Login con credenciales incorrectas debería fallar", UsuarioController.autenticarUsuario("usuarioInexistente", "contraseñaIncorrecta"));
        testsPasados++;
    }

    @After
    public void tearDown() {
        testsTotales++;
        System.out.println("Prueba finalizada.");
    }

    @After
    public void printSummary() {
        System.out.println("Resumen de pruebas:");
        System.out.println("Tests pasados: " + testsPasados + " de " + testsTotales);
    }
}