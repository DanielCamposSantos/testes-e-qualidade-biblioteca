package io.github.danielcampossantos.service;

import io.github.danielcampossantos.domain.Livro;
import io.github.danielcampossantos.exceptions.LivroInexistenteException;
import io.github.danielcampossantos.exceptions.LivroJaExisteException;
import io.github.danielcampossantos.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository repository;

    public List<Livro> buscarTodos() {
        return repository.buscarTodos();
    }

    public Optional<Livro> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID deve ser positivo.");
        }
        return repository.buscarPorId(id);
    }

    public Livro salvar(Livro livro) {
        if (livro.getId() != null && buscarPorId(livro.getId()).isPresent()) {
            throw new LivroJaExisteException("Já existe um livro com o ID: " + livro.getId());
        }
        return repository.salvar(livro);
    }

    public Livro atualizarPorId(Long id, Livro livro) {
        if (buscarPorId(id).isEmpty()) {
            throw new LivroInexistenteException("Livro não encontrado.");
        }
        if (livro.getId() != null && !id.equals(livro.getId())) {
            throw new IllegalArgumentException("O ID do livro não pode ser alterado.");
        }
        return repository.salvar(livro.toBuilder().id(id).build());
    }

    public void deletar(Long id) {
        if (buscarPorId(id).isEmpty()) {
            throw new LivroInexistenteException("Livro não encontrado.");
        }
        repository.deletar(id);
    }
}
