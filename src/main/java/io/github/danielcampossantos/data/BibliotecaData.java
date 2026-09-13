package io.github.danielcampossantos.data;

import io.github.danielcampossantos.domain.Livro;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class BibliotecaData {
    @lombok.Getter
    private final Map<Long, Livro> livros = new LinkedHashMap<>();

    public BibliotecaData() {

        adicionar(Livro.builder()
                .id(1L)
                .titulo("O Senhor dos Anéis")
                .autor("J.R.R. Tolkien")
                .isbn("9788551005217")
                .paginas(1200)
                .dataPublicacao(LocalDate.of(1954, 7, 29))
                .editora("HarperCollins")
                .build());

        adicionar(Livro.builder()
                .id(2L)
                .titulo("1984")
                .autor("George Orwell")
                .isbn("9788535914849")
                .paginas(336)
                .dataPublicacao(LocalDate.of(1949, 6, 8))
                .editora("Companhia das Letras")
                .build());

        adicionar(Livro.builder()
                .id(3L)
                .titulo("O Dom Quixote")
                .autor("Miguel de Cervantes")
                .isbn("9788582850015")
                .paginas(864)
                .dataPublicacao(LocalDate.of(1605, 1, 16))
                .editora("Penguin Companhia")
                .build());

        adicionar(Livro.builder()
                .id(4L)
                .titulo("O Pequeno Príncipe")
                .autor("Antoine de Saint-Exupéry")
                .isbn("9788522031436")
                .paginas(96)
                .dataPublicacao(LocalDate.of(1943, 4, 6))
                .editora("Agir")
                .build());

        adicionar(Livro.builder()
                .id(5L)
                .titulo("Cem Anos de Solidão")
                .autor("Gabriel García Márquez")
                .isbn("9788501012074")
                .paginas(448)
                .dataPublicacao(LocalDate.of(1967, 5, 30))
                .editora("Record")
                .build());

        adicionar(Livro.builder()
                .id(6L)
                .titulo("Orgulho e Preconceito")
                .autor("Jane Austen")
                .isbn("9788595082342")
                .paginas(424)
                .dataPublicacao(LocalDate.of(1813, 1, 28))
                .editora("HarperCollins")
                .build());

        adicionar(Livro.builder()
                .id(7L)
                .titulo("O Hobbit")
                .autor("J.R.R. Tolkien")
                .isbn("9788595084742")
                .paginas(336)
                .dataPublicacao(LocalDate.of(1937, 9, 21))
                .editora("HarperCollins")
                .build());

        adicionar(Livro.builder()
                .id(8L)
                .titulo("A Revolução dos Bichos")
                .autor("George Orwell")
                .isbn("9788535909555")
                .paginas(152)
                .dataPublicacao(LocalDate.of(1945, 8, 17))
                .editora("Companhia das Letras")
                .build());

        adicionar(Livro.builder()
                .id(9L)
                .titulo("Crime e Castigo")
                .autor("Fiódor Dostoiévski")
                .isbn("9788573262087")
                .paginas(592)
                .dataPublicacao(LocalDate.of(1866, 1, 1))
                .editora("Editora 34")
                .build());

        adicionar(Livro.builder()
                .id(10L)
                .titulo("O Alquimista")
                .autor("Paulo Coelho")
                .isbn("9788525061614")
                .paginas(208)
                .dataPublicacao(LocalDate.of(1988, 5, 1))
                .editora("Paralela")
                .build());
    }
    private void adicionar(Livro livro) {
        livros.put(livro.getId(), livro);
    }

}