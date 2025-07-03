package com.challenge.demo;

import com.challenge.demo.model.Autor;
import com.challenge.demo.model.DadosAutor;
import com.challenge.demo.model.DadosLivro;
import com.challenge.demo.model.Idioma;
import com.challenge.demo.model.Livro;
import com.challenge.demo.repository.AutorRepository;
import com.challenge.demo.repository.LivroRepository;
import com.challenge.demo.repository.IdiomaRepository;
import com.challenge.demo.service.ConsumoAPI;
import com.challenge.demo.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private final String ENDERECO = "http://gutendex.com/books/?search=";

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;
    private IdiomaRepository idiomaRepository;
    private ConsumoAPI consumoAPI;
    private ConverteDados conversor;

    @Autowired
    public Principal(LivroRepository livroRepository, AutorRepository autorRepository,
                     IdiomaRepository idiomaRepository, ConsumoAPI consumoAPI, ConverteDados conversor) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.idiomaRepository = idiomaRepository;
        this.consumoAPI = consumoAPI;
        this.conversor = conversor;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                
                ********** LITERALURA **********
                
                [1] BUSCAR LIVRO
                [2] LISTAR TODOS OS LIVROS
                [3] LISTAR AUTORES
                [4] LISTAR AUTORES VIVOS EM DETERMINADO ANO
                [5] LISTAR LIVROS POR IDIOMA
                
                [0] PARA SAIR
                """);
            System.out.print("Escolha a opção ==> ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 0:
                    System.out.println("\nSaindo da aplicação...");
                    break;
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                default:
                    System.out.println("\nOpção inválida!\n");
                    break;
            }
        }
    }

    private void buscarLivro() {
        System.out.print("\nInforme o título do livro: ");
        var nomeLivro = scanner.nextLine();

        var jsonResponse = consumoAPI.consumo(ENDERECO + nomeLivro.replace(" ", "%20"));

        try {
            JsonNode rootNode = conversor.objeto(jsonResponse);
            JsonNode resultsNode = rootNode.get("results");

            if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
                DadosLivro dados = conversor.obterDados(resultsNode.get(0).toString(), DadosLivro.class);

                System.out.println("\n========== DADOS DO LIVRO ==========");
                System.out.println("Título: " + dados.titulo());
                if (!dados.autor().isEmpty()) {
                    System.out.println("Autor: " + dados.autor().get(0).nome());
                } else {
                    System.out.println("Autor: N/A");
                }
                if (!dados.idioma().isEmpty()) {
                    System.out.println("Idioma: " + dados.idioma().get(0));
                } else {
                    System.out.println("Idioma: N/A");
                }
                System.out.println("Total Downloads: " + dados.numeroDownloads());
                System.out.println("====================================\n");

                salvarLivroEAutor(dados);
            } else {
                System.out.println("\nLivro não encontrado ou sem resultados para o título informado.");
            }
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado ao buscar o livro: " + e.getMessage());
        }
    }

    private void salvarLivroEAutor(DadosLivro dados) {
        if (dados.autor().isEmpty()) {
            System.out.println("Não foi possível salvar: Dados do autor não encontrados.");
            return;
        }

        DadosAutor dadosPrimeiroAutor = dados.autor().get(0);
        Autor autor;

        Optional<Autor> autorExistente = autorRepository.findByNome(dadosPrimeiroAutor.nome());

        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
        } else {
            autor = new Autor();
            autor.setNome(dadosPrimeiroAutor.nome());
            autor.setDataNascimento(dadosPrimeiroAutor.dataNascimento());
            autor.setDataMorte(dadosPrimeiroAutor.dataMorte());
            autor = autorRepository.save(autor);
        }

        Optional<Livro> livroExistente = livroRepository.findByTitulo(dados.titulo());
        if (livroExistente.isPresent()) {
            System.out.println("Livro já cadastrado: " + livroExistente.get().getTitulo());
            return;
        }

        Livro livro = new Livro();
        livro.setTitulo(dados.titulo());
        livro.setNumeroDownloads(dados.numeroDownloads());
        livro.setAutor(autor);

        List<Idioma> idiomasDoLivro = new ArrayList<>();
        for (String abreviacaoIdioma : dados.idioma()) {
            Optional<Idioma> idiomaExistente = idiomaRepository.findByAbreviacao(abreviacaoIdioma);
            Idioma idioma;
            if (idiomaExistente.isPresent()) {
                idioma = idiomaExistente.get();
            } else {
                idioma = new Idioma(abreviacaoIdioma);
                idioma = idiomaRepository.save(idioma);
            }
            idiomasDoLivro.add(idioma);
        }
        livro.setIdioma(idiomasDoLivro);

        autor.getLivro().add(livro);
        livroRepository.save(livro);
        System.out.println("\nLivro e Autor salvos com sucesso!");
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro cadastrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor cadastrado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivos() {
        System.out.print("\nInforme o ano para pesquisa: ");
        var ano = scanner.nextLine();

        List<Autor> autoresVivos = autorRepository.findByAutorVivo(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("\nNenhum autor vivo encontrado no ano " + ano + ".");
        } else {
            System.out.println("\nAutores vivos em " + ano + ":");
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                
                Idiomas disponíveis:
                [EN] Inglês
                [ES] Espanhol
                [FR] Francês
                [PT] Português
                """);
        System.out.print("Digite o idioma para pesquisa: ");
        String linguagem = scanner.nextLine().toLowerCase();

        List<Livro> livros = livroRepository.findByIdioma(linguagem);

        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro encontrado para o idioma '" + linguagem + "'.");
        } else {
            System.out.println("\nLivros com o idioma '" + linguagem + "':");
            livros.forEach(System.out::println);
        }
    }
}