package br.com.alura.literalura.model;

import br.com.alura.literalura.DTO.DadosAutor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.anoDeNascimento = parseAno(dadosAutor.anoDeNascimento());
        this.anoDeFalecimento = parseAno(dadosAutor.anoDeFalecimento());
    }

    private Integer parseAno(String ano) {
        return (ano != null && !ano.isBlank()) ? Integer.valueOf(ano) : null;
    }



    public Autor() {
    }

    public String getNome() { return nome;}

    public Long getId() {return id;}

    private List<String> getTitulosDosLivros() {
        return livros.stream().map(Livro :: getTitulo).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "\n Autor: " + nome +
                "\n Ano de nascimento: " + anoDeNascimento +
                "\n Ano de falecimento: " + anoDeFalecimento +
                "\n Livros: " + getTitulosDosLivros();
    }
}
