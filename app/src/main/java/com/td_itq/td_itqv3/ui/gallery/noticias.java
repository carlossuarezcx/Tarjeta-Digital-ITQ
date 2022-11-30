package com.td_itq.td_itqv3.ui.gallery;

public class noticias {
    private String titulo, contenido, fecha,autor, imagen;
    private int id_noticia;

    public noticias(String titulo, String contenido, String fecha, String autor, String imagen, int id_noticia) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.autor = autor;
        this.imagen = imagen;
        this.id_noticia = id_noticia;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public String getAutor() {
        return autor;
    }

    public String getImagen() {
        return imagen;
    }

    public int getId_noticia() {
        return id_noticia;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setId_noticia(int id_noticia) {
        this.id_noticia = id_noticia;
    }

}
