package io.github.danielcampossantos.service;


import io.github.danielcampossantos.domain.Livro;
import io.github.danielcampossantos.repository.LivroRepository;
import io.github.danielcampossantos.utils.LivroUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService service;

    @Mock
    private LivroRepository repository;

    private LivroUtils livroUtils = new LivroUtils();

    private List<Livro> livros;

    @BeforeEach
    void setUp(){
        livros = new ArrayList<>(livroUtils.criarListaNovaDeLivros());
    }

    @Test
    @DisplayName("buscarTodos() retorna lista com todos os livros quando sucesso")
    void buscarTodos_RetornaListaComTodosOsLivros_QuandoSucesso() {

        BDDMockito.when(repository.buscarTodos()).thenReturn(livroUtils.criarListaNovaDeLivros());

        Assertions.assertThat(service.buscarTodos())
                .isNotNull()
                .isNotEmpty()
                .containsAll(livros);


    }

//    public List<Livro> buscarTodos() {
//        return repository.buscarTodos();
//    }
//
//    public Optional<Livro> buscarPorId(Long id) {
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException("O ID deve ser positivo.");
//        }
//        return repository.buscarPorId(id);
//    }
//
//    public Livro salvar(Livro livro) {
//        if (livro.getId() != null && buscarPorId(livro.getId()).isPresent()) {
//            throw new LivroJaExisteException("Já existe um livro com o ID: " + livro.getId());
//        }
//        return repository.salvar(livro);
//    }
//
//    public Livro atualizarPorId(Long id, Livro livro) {
//        if (buscarPorId(id).isEmpty()) {
//            throw new LivroInexistenteException("Livro não encontrado.");
//        }
//        if (livro.getId() != null && !id.equals(livro.getId())) {
//            throw new IllegalArgumentException("O ID do livro não pode ser alterado.");
//        }
//        return repository.salvar(livro.toBuilder().id(id).build());
//    }
//
//    public void deletar(Long id) {
//        if (buscarPorId(id).isEmpty()) {
//            throw new LivroInexistenteException("Livro não encontrado.");
//        }
//        repository.deletar(id);
//    }


}