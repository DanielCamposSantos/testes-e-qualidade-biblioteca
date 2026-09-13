package io.github.danielcampossantos.repository;

import io.github.danielcampossantos.data.BibliotecaData;
import io.github.danielcampossantos.domain.Livro;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LivroRepository {
    private final Map<Long, Livro> livros = new BibliotecaData().getLivros();
    private long ultimoId = livros.keySet().stream().mapToLong(Long::longValue).max().orElse(0L);

    public List<Livro> buscarTodos() {
        return List.copyOf(livros.values());
    }

    public Optional<Livro> buscarPorId(Long id) {
        return Optional.ofNullable(livros.get(id));
    }

    public Livro salvar(Livro livro) {
        if (livro.getId() == null) {
            livro = livro.toBuilder().id(Math.incrementExact(ultimoId)).build();
        }
        livros.put(livro.getId(), livro);
        ultimoId = Math.max(ultimoId, livro.getId());
        return livro;
    }

    public void deletar(Long id) {
        livros.remove(id);
    }
}