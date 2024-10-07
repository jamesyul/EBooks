package main.java.models;

public class Usuario {
    private String usuario;
    private String password;
    private String perfil;
    private String idBiblioteca; // Nuevo campo

    public Usuario(String usuario, String password, String perfil) {
        this.usuario = usuario;
        this.password = password;
        this.perfil = perfil;
    }

    public Usuario(String usuario, String password, String perfil, String idBiblioteca) {
        this.usuario = usuario;
        this.password = password;
        this.perfil = perfil;
        this.idBiblioteca = idBiblioteca;
    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(String idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }
}