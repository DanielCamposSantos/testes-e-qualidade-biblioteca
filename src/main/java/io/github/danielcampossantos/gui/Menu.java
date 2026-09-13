package io.github.danielcampossantos.gui;

import io.github.danielcampossantos.domain.Livro;
import io.github.danielcampossantos.exceptions.LivroInexistenteException;
import io.github.danielcampossantos.exceptions.LivroJaExisteException;
import io.github.danielcampossantos.repository.LivroRepository;
import io.github.danielcampossantos.service.LivroService;
import io.github.danielcampossantos.utils.ConsoleReader;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class Menu {
    private final LivroService service = new LivroService(new LivroRepository());
    private final ConsoleReader reader = new ConsoleReader();

    public void iniciar() {
        int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("""
                        
                        === BIBLIOTECA ===
                        1. Listar livros
                        2. Buscar por ID
                        3. Cadastrar livro
                        4. Atualizar livro
                        5. Excluir livro
                        0. Sair
                        """);
                opcao = reader.lerInteiro("Escolha uma opção: ");
                switch (opcao) {
                    case 1 -> listar();
                    case 2 -> buscar();
                    case 3 -> cadastrar();
                    case 4 -> atualizar();
                    case 5 -> excluir();
                    case 0 -> System.out.println("Programa encerrado.");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException | DateTimeParseException e) {
                System.out.println("Número ou data inválida. Tente novamente pelo menu.");
            } catch (IllegalArgumentException | LivroJaExisteException | LivroInexistenteException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                return;
            }
        }
    }

    private void listar() {
        var livros = service.buscarTodos();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        }
        livros.forEach(System.out::println);
    }

    private void buscar() {
        Long id = reader.lerLong("ID: ");
        var livro = service.buscarPorId(id);
        if (livro.isPresent()) {
            System.out.println(livro.get());
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private void cadastrar() {
        Livro livro = service.salvar(lerLivro());
        System.out.println("Livro cadastrado com ID: " + livro.getId());
    }

    private void atualizar() {
        Long id = reader.lerLong("ID: ");
        var encontrado = service.buscarPorId(id);
        if (encontrado.isEmpty()) {
            System.out.println("Livro não encontrado.");
            return;
        }
        Livro livro = encontrado.get();
        while (true) {
            System.out.println("\nAtualizando: " + livro);
            System.out.println("""
                    1. Título
                    2. Autor
                    3. ISBN
                    4. Páginas
                    5. Data de publicação
                    6. Editora
                    0. Voltar ao menu principal
                    """);
            try {
                int opcao = reader.lerInteiro("Qual campo deseja atualizar? ");
                var builder = livro.toBuilder();
                switch (opcao) {
                    case 1 -> builder.titulo(reader.lerTexto("Novo título: "));
                    case 2 -> builder.autor(reader.lerTexto("Novo autor: "));
                    case 3 -> builder.isbn(reader.lerTexto("Novo ISBN: "));
                    case 4 -> builder.paginas(reader.lerInteiro("Nova quantidade de páginas: "));
                    case 5 -> builder.dataPublicacao(reader.lerData(
                            "Nova publicação (dd/MM/aaaa, vazio para remover): "));
                    case 6 -> builder.editora(reader.lerTexto("Nova editora: "));
                    case 0 -> { return; }
                    default -> {
                        System.out.println("Opção inválida.");
                        continue;
                    }
                }
                livro = service.atualizarPorId(id, builder.build());
                System.out.println("Campo atualizado.");
            } catch (NumberFormatException | DateTimeParseException e) {
                System.out.println("Número ou data inválida. Tente novamente.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void excluir() {
        service.deletar(reader.lerLong("ID: "));
        System.out.println("Livro excluído.");
    }

    private Livro lerLivro() {
        return Livro.builder()
                .titulo(reader.lerTexto("Título: "))
                .autor(reader.lerTexto("Autor: "))
                .isbn(reader.lerTexto("ISBN: "))
                .paginas(reader.lerInteiro("Páginas: "))
                .dataPublicacao(reader.lerData("Publicação (dd/MM/aaaa, opcional): "))
                .editora(reader.lerTexto("Editora: "))
                .build();
    }
}
