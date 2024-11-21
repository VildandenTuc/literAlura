package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Column(name = "nombre_autor")
    private String nombreAutor;


    private String idiomas;
    private Double numeroDeDescargas;

    public Libro() {}

    public Libro(DatosLibros datosLibros, Autor autor) {
        this.titulo = datosLibros.titulo();
        this.autor = autor;
        this.nombreAutor = autor.getNombre();
        setIdiomas(datosLibros.idiomas());
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public Long getId() {
        return Id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return Arrays.asList(idiomas.split(","));
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas =  String.join(",", idiomas);
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return  "\n--------------------------------------------\n" +
                "Libro: \n" +
                "Título = " + titulo + "\n" +
                "Autor = " + nombreAutor + "\n" +
                "Idioma = " + idiomas + "\n" +
                "Número de descargas = " + numeroDeDescargas +
                "\n--------------------------------------------\n";
    }
}
