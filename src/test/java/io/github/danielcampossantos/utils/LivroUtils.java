package io.github.danielcampossantos.utils;

import io.github.danielcampossantos.domain.Livro;

import java.time.LocalDate;
import java.util.List;

public class LivroUtils {
    public List<Livro> criarListaNovaDeLivros() {
        var livro1 = Livro.builder()
                .id(1L)
                .titulo("O Pequeno Príncipe")
                .autor("Antoine de Saint-Exupéry")
                .isbn("9783140464079")
                .paginas(96)
                .dataPublicacao(LocalDate.of(1943, 4, 6))
                .editora("Reynal & Hitchcock")
                .build();

        var livro2 = Livro.builder()
                .id(2L)
                .titulo("Dom Casmurro")
                .autor("Machado de Assis")
                .isbn("9788535931334")
                .paginas(256)
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .editora("Livraria Garnier")
                .build();

        var livro3 = Livro.builder()
                .id(3L)
                .titulo("1984")
                .autor("George Orwell")
                .isbn("9788535914849")
                .paginas(416)
                .dataPublicacao(LocalDate.of(1949, 6, 8))
                .editora("Secker & Warburg")
                .build();

        return List.of(livro1, livro2, livro3);
    }
}
