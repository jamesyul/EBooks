package main.java.models;

public class Libro {
    private String id_BNE;
    private String autor_personas;
    private String autor_entidades;
    private String titulo;
    private String editorial;
    private String lugar_publicacion;
    private String fecha_publicacion;
    private String descripcion_fisica;
    private String descripcion_notas;
    private String genero_forma;
    private String lugar_relacionado;
    private String pais_publicacion;
    private String lengua_publicacion;
    private String tipo_documento;
    private String signatura;
    private String version_digital;
    private String texto_OCR;


    public Libro() {

    }

    public Libro(String id_BNE, String autor_personas, String autor_entidades, String titulo, String editorial,
                 String lugar_publicacion, String fecha_publicacion, String descripcion_fisica,
                 String descripcion_notas, String genero_forma, String lugar_relacionado,
                 String pais_publicacion, String lengua_publicacion, String tipo_documento,
                 String signatura, String version_digital, String texto_OCR) {
        this.id_BNE = id_BNE;
        this.autor_personas = autor_personas;
        this.autor_entidades = autor_entidades;
        this.titulo = titulo;
        this.editorial = editorial;
        this.lugar_publicacion = lugar_publicacion;
        this.fecha_publicacion = fecha_publicacion;
        this.descripcion_fisica = descripcion_fisica;
        this.descripcion_notas = descripcion_notas;
        this.genero_forma = genero_forma;
        this.lugar_relacionado = lugar_relacionado;
        this.pais_publicacion = pais_publicacion;
        this.lengua_publicacion = lengua_publicacion;
        this.tipo_documento = tipo_documento;
        this.signatura = signatura;
        this.version_digital = version_digital;
        this.texto_OCR = texto_OCR;
    }

    @Override
    public String toString() {
        return "\n" +
                "|---------------------|-------|\n" +
                "| ID                  | " + (id_BNE != null ? id_BNE : "No disponible") + " |\n" +
                "| Autor Personas      | " + (autor_personas != null ? autor_personas : "No disponible") + " |\n" +
                "| Autor Entidades     | " + (autor_entidades != null ? autor_entidades : "No disponible") + " |\n" +
                "| Título              | " + (titulo != null ? titulo : "No disponible") + " |\n" +
                "| Editorial           | " + (editorial != null ? editorial : "No disponible") + " |\n" +
                "| Lugar Publicación   | " + (lugar_publicacion != null ? lugar_publicacion : "No disponible") + " |\n" +
                "| Fecha Publicación   | " + (fecha_publicacion != null ? fecha_publicacion : "No disponible") + " |\n" +
                "| Descripción Física  | " + (descripcion_fisica != null ? descripcion_fisica : "No disponible") + " |\n" +
                "| Descripción Notas   | " + (descripcion_notas != null ? descripcion_notas : "No disponible") + " |\n" +
                "| Género Forma        | " + (genero_forma != null ? genero_forma : "No disponible") + " |\n" +
                "| Lugar Relacionado   | " + (lugar_relacionado != null ? lugar_relacionado : "No disponible") + " |\n" +
                "| País Publicación    | " + (pais_publicacion != null ? pais_publicacion : "No disponible") + " |\n" +
                "| Lengua Publicación  | " + (lengua_publicacion != null ? lengua_publicacion : "No disponible") + " |\n" +
                "| Tipo Documento      | " + (tipo_documento != null ? tipo_documento : "No disponible") + " |\n" +
                "| Signatura           | " + (signatura != null ? signatura : "No disponible") + " |\n" +
                "| Versión Digital     | " + (version_digital != null ? version_digital : "No disponible") + " |\n" +
                "| Texto OCR           | " + (texto_OCR != null ? texto_OCR : "No disponible") + " |\n";
    }

    public String getId_BNE() {
        return id_BNE;
    }

    public void setId_BNE(String id_BNE) {
        this.id_BNE = id_BNE;
    }

    public String getAutor_personas() {
        return autor_personas;
    }

    public void setAutor_personas(String autor_personas) {
        this.autor_personas = autor_personas;
    }

    public String getAutor_entidades() {
        return autor_entidades;
    }

    public void setAutor_entidades(String autor_entidades) {
        this.autor_entidades = autor_entidades;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getLugar_publicacion() {
        return lugar_publicacion;
    }

    public void setLugar_publicacion(String lugar_publicacion) {
        this.lugar_publicacion = lugar_publicacion;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getDescripcion_fisica() {
        return descripcion_fisica;
    }

    public void setDescripcion_fisica(String descripcion_fisica) {
        this.descripcion_fisica = descripcion_fisica;
    }

    public String getDescripcion_notas() {
        return descripcion_notas;
    }

    public void setDescripcion_notas(String descripcion_notas) {
        this.descripcion_notas = descripcion_notas;
    }

    public String getGenero_forma() {
        return genero_forma;
    }

    public void setGenero_forma(String genero_forma) {
        this.genero_forma = genero_forma;
    }

    public String getLugar_relacionado() {
        return lugar_relacionado;
    }

    public void setLugar_relacionado(String lugar_relacionado) {
        this.lugar_relacionado = lugar_relacionado;
    }

    public String getPais_publicacion() {
        return pais_publicacion;
    }

    public void setPais_publicacion(String pais_publicacion) {
        this.pais_publicacion = pais_publicacion;
    }

    public String getLengua_publicacion() {
        return lengua_publicacion;
    }

    public void setLengua_publicacion(String lengua_publicacion) {
        this.lengua_publicacion = lengua_publicacion;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getSignatura() {
        return signatura;
    }

    public void setSignatura(String signatura) {
        this.signatura = signatura;
    }

    public String getVersion_digital() {
        return version_digital;
    }

    public void setVersion_digital(String version_digital) {
        this.version_digital = version_digital;
    }

    public String getTexto_OCR() {
        return texto_OCR;
    }

    public void setTexto_OCR(String texto_OCR) {
        this.texto_OCR = texto_OCR;
    }
}