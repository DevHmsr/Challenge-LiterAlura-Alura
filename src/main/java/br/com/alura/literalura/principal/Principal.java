package br.com.alura.literalura.principal;

import br.com.alura.literalura.DTO.Dados;
import br.com.alura.literalura.DTO.DadosAutor;
import br.com.alura.literalura.DTO.DadosLivro;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;
import br.com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Scanner;

public class Principal {

    @Autowired
    private final LivroRepository livroRepository;

    @Autowired
    private final AutorRepository autorRepository;

    private static final String URL_BASE ="https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private Scanner teclado = new Scanner(System.in);
    private String json;

    private String menu = """
    ------------
    Escolha o número da opção desejada:
    1 - Buscar livro pelo título
    2 - Listar livros registrados
    3 - Listar autores registrados
    4 - Listar autores vivos em um determinado ano
    5 - Listar livros em um determinado idioma
    0 - Sair
    Opção: """;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println(menu);
            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosEmDeterminadoAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> {
                    System.out.println("Até logo! Saindo...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        DadosLivro dadosLivro = obterDadosLivro();

        if (dadosLivro != null) {
            DadosAutor dadosAutor = dadosLivro.autor().get(0);
            Livro livro;
            Autor autorExistente = autorRepository.findByNome(dadosAutor.nome());

            if (autorExistente != null) {
                Optional<Livro> livroExistente = livroRepository.findByTituloAndAutor(dadosLivro.titulo(), autorExistente);
                if (livroExistente.isPresent()) {
                    System.out.println("Livro já cadastrado no banco de dados.");
                    return;
                }
                livro = new Livro(dadosLivro, autorExistente);
            } else {
                Autor novoAutor = new Autor(dadosAutor);
                autorRepository.save(novoAutor);
                livro = new Livro(dadosLivro, novoAutor);
            }

            livroRepository.save(livro);

            System.out.println("------ LIVRO ------");
            System.out.println("Titulo: " + livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor().getNome());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Número de downloads: " + livro.getNumeroDeDownloads());
            System.out.println("-------------------");
        } else {
            System.out.println("Livro não encontrado na API.");
        }
    }

    private void listarLivrosRegistrados() {
        var livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado no sistema.");
        } else {
            System.out.println("Livros registrados:");
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado no sistema.");
        } else {
            System.out.println("Autores registrados:");
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Digite o ano para verificar autores vivos:");
        String anoStr = teclado.nextLine();

        try {
            int ano = Integer.parseInt(anoStr);
            var autores = autorRepository.findAutoresVivosNoAno(ano);

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado no ano " + ano);
            } else {
                System.out.println("Autores vivos no ano " + ano + ":");
                autores.forEach(System.out::println);
            }

        } catch (NumberFormatException e) {
            System.out.println("Ano inválido, por favor digite um número inteiro.");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
        Insira o idioma para realizar a busca:
        es – espanhol
        fr – francês
        en – inglês
        it – italiano
        pt – português
        """);
        String idioma = teclado.nextLine();

        var livros = livroRepository.findByIdiomaIgnoreCase(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma: " + idioma);
        } else {
            System.out.println("Livros encontrados para o idioma " + idioma + ":");
            livros.forEach(System.out::println);
            System.out.println("Total de livros encontrados: " + livros.size());
        }
    }

    private DadosLivro obterDadosLivro() {
        System.out.println("Digite o nome do livro");
        var nomeDoLivro = teclado.nextLine();
        json = consumoAPI.obterDados(URL_BASE + "?search=" + nomeDoLivro.replace(" ", "+"));

        var dadosBusca = conversor.obeterDados(json, Dados.class);
        Optional<DadosLivro> dadosLivro = dadosBusca.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nomeDoLivro.toUpperCase()))
                .findFirst();

        return dadosLivro.orElse(null);
    }

}
