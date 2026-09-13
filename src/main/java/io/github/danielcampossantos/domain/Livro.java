package io.github.danielcampossantos.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class Livro {
    private final Long id;
    private final String titulo;
    private final String autor;
    private final String isbn;
    private final Integer paginas;
    private final LocalDate dataPublicacao;
    private final String editora;


    @Builder(toBuilder = true)
    public Livro(Long id, String titulo, String autor, String isbn, Integer paginas,
                 LocalDate dataPublicacao, String editora) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("O ID deve ser positivo.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título não pode ser vazio.");
        }
        if (paginas != null && paginas <= 0) {
            throw new IllegalArgumentException("O número de páginas não pode ser negativo ou igual a zero");
        }
        this.id = id;
        this.titulo = titulo.strip();
        this.autor = autor;
        this.isbn = isbn;
        this.paginas = paginas;
        this.dataPublicacao = dataPublicacao;
        this.editora = editora;
    }

}
