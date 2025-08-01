package br.com.alura.literalura.model;

import br.com.alura.literalura.DTO.DadosLivro;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;
    private Double numeroDeDownloads;

    public Livro () {
    }

    public Livro(DadosLivro dadosLivro, Autor autor) {
        this.titulo = dadosLivro.titulo();
        this.autor = autor;
        this.idioma = dadosLivro.idiomas().get(0);
        this.numeroDeDownloads = dadosLivro.numeroDeDownloads();
    }

    public Long getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public Double getNumeroDeDownloads() {
        return numeroDeDownloads;
    }


    @Override
    public String toString() {
        return "------ LIVRO ------\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNome() : "Desconhecido") + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de downloads: " + numeroDeDownloads + "\n" +
                "-------------------\n";
    }

}
