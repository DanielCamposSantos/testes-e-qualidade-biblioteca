
package io.github.danielcampossantos.service;

import io.github.danielcampossantos.domain.Livro;
import io.github.danielcampossantos.exceptions.LivroInexistenteException;
import io.github.danielcampossantos.exceptions.LivroJaExisteException;
import io.github.danielcampossantos.repository.LivroRepository;
import io.github.danielcampossantos.utils.LivroUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository repository;

    @InjectMocks
    private LivroService service;

    private LivroUtils utils;
    private List<Livro> livros;

    @BeforeEach
    void setUp() {
        utils = new LivroUtils();
        livros = utils.criarListaNovaDeLivros();
    }

    @Test
    @DisplayName("Buscar todos: retorna lista de livros quando a operação é bem-sucedida")
    void buscarTodos_RetornaListaDeLivros_QuandoSucesso() {
        BDDMockito.when(repository.buscarTodos()).thenReturn(livros);

        List<Livro> resultado = service.buscarTodos();

        Assertions.assertThat(resultado)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(livros);

        BDDMockito.verify(repository).buscarTodos();
    }

    @Test
    @DisplayName("Buscar por ID: retorna livro quando o ID existe")
    void buscarPorId_RetornaLivro_QuandoIdExistente() {
        Livro livro = utils.criarLivro();

        BDDMockito.when(repository.buscarPorId(livro.getId())).thenReturn(Optional.of(livro));

        Optional<Livro> resultado = service.buscarPorId(livro.getId());

        Assertions.assertThat(resultado)
                .isNotNull()
                .isPresent()
                .contains(livro);

        BDDMockito.verify(repository).buscarPorId(livro.getId());
    }

    @Test
    @DisplayName("Salvar: retorna livro salvo quando o livro não possui ID")
    void salvar_RetornaLivroSalvo_QuandoLivroSemId() {
        Livro livro = utils.criarLivroSemId();
        Livro salvo = utils.criarLivroComId(4L);

        BDDMockito.when(repository.salvar(livro)).thenReturn(salvo);

        Livro resultado = service.salvar(livro);

        Assertions.assertThat(resultado)
                .isNotNull()
                .isSameAs(salvo);

        Assertions.assertThat(resultado.getId())
                .isNotNull()
                .isEqualTo(salvo.getId());

        BDDMockito.verify(repository).salvar(livro);
    }

    @Test
    @DisplayName("Salvar: mantém ID informado quando o ID está disponível")
    void salvar_MantemIdInformado_QuandoIdDisponivel() {
        Livro livro = utils.criarLivroComId(4L);

        BDDMockito.when(repository.buscarPorId(livro.getId())).thenReturn(Optional.empty());
        BDDMockito.when(repository.salvar(livro)).thenReturn(livro);

        Livro resultado = service.salvar(livro);

        Assertions.assertThat(resultado)
                .isNotNull();

        Assertions.assertThat(resultado.getId())
                .isNotNull()
                .isEqualTo(livro.getId());

        BDDMockito.verify(repository).buscarPorId(livro.getId());
        BDDMockito.verify(repository).salvar(livro);
    }

    @Test
    @DisplayName("Atualizar por ID: atualiza dados e mantém ID quando o livro existe")
    void atualizarPorId_AtualizaDadosEMantemId_QuandoLivroExistente() {
        Livro original = livros.get(1);
        Livro alterado = utils.criarLivroComTitulo(original, "Dom Casmurro - Edição Revisada");

        BDDMockito.when(repository.buscarPorId(original.getId())).thenReturn(Optional.of(original));
        BDDMockito.when(repository.salvar(ArgumentMatchers.any(Livro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Livro resultado = service.atualizarPorId(original.getId(), alterado);

        Assertions.assertThat(resultado.getId())
                .isNotNull()
                .isEqualTo(original.getId());

        Assertions.assertThat(resultado.getTitulo())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(alterado.getTitulo());

        BDDMockito.verify(repository).buscarPorId(original.getId());
    }

    @Test
    @DisplayName("Atualizar por ID: define ID quando o livro não possui ID")
    void atualizarPorId_DefineId_QuandoLivroSemId() {
        Livro original = livros.get(1);
        Livro alterado = utils.criarLivroComTitulo(utils.criarLivroSemId(original), "Dom Casmurro Atualizado");

        BDDMockito.when(repository.buscarPorId(original.getId())).thenReturn(Optional.of(original));
        BDDMockito.when(repository.salvar(ArgumentMatchers.any(Livro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Livro resultado = service.atualizarPorId(original.getId(), alterado);

        Assertions.assertThat(resultado.getId())
                .isNotNull()
                .isEqualTo(original.getId());

        Assertions.assertThat(resultado.getTitulo())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(alterado.getTitulo());

        BDDMockito.verify(repository).buscarPorId(original.getId());
    }

    @Test
    @DisplayName("Deletar: solicita exclusão quando o livro existe")
    void deletar_SolicitaExclusao_QuandoLivroExistente() {
        Livro livro = utils.criarLivro();

        BDDMockito.when(repository.buscarPorId(livro.getId())).thenReturn(Optional.of(livro));

        service.deletar(livro.getId());

        BDDMockito.verify(repository).buscarPorId(livro.getId());
        BDDMockito.verify(repository).deletar(livro.getId());
    }

    @Test
    @DisplayName("Buscar por ID: retorna vazio quando o ID não existe")
    void buscarPorId_RetornaVazio_QuandoIdInexistente() {
        Long id = 999L;

        BDDMockito.when(repository.buscarPorId(id)).thenReturn(Optional.empty());

        Optional<Livro> resultado = service.buscarPorId(id);

        Assertions.assertThat(resultado)
                .isNotNull()
                .isEmpty();

        BDDMockito.verify(repository).buscarPorId(id);
    }

    @Test
    @DisplayName("Buscar por ID: lança IllegalArgumentException quando o ID é inválido")
    void buscarPorId_LancaIllegalArgumentException_QuandoIdInvalido() {
        Assertions.assertThatThrownBy(() -> service.buscarPorId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O ID deve ser positivo.");

        Assertions.assertThatThrownBy(() -> service.buscarPorId(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O ID deve ser positivo.");

        Assertions.assertThatThrownBy(() -> service.buscarPorId(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O ID deve ser positivo.");

        BDDMockito.verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Salvar: lança LivroJaExisteException quando o ID já existe")
    void salvar_LancaLivroJaExisteException_QuandoIdExistente() {
        Livro livro = utils.criarLivro();

        BDDMockito.when(repository.buscarPorId(livro.getId())).thenReturn(Optional.of(livro));

        Assertions.assertThatThrownBy(() -> service.salvar(livro))
                .isInstanceOf(LivroJaExisteException.class)
                .hasMessageContaining("Já existe um livro com o ID");

        BDDMockito.verify(repository).buscarPorId(livro.getId());
        BDDMockito.verify(repository, BDDMockito.never()).salvar(ArgumentMatchers.any(Livro.class));
    }

    @Test
    @DisplayName("Atualizar por ID: lança LivroInexistenteException quando o livro não existe")
    void atualizarPorId_LancaLivroInexistenteException_QuandoLivroInexistente() {
        Long id = 999L;
        Livro livro = utils.criarLivro();

        BDDMockito.when(repository.buscarPorId(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.atualizarPorId(id, livro))
                .isInstanceOf(LivroInexistenteException.class)
                .hasMessage("Livro não encontrado.");

        BDDMockito.verify(repository).buscarPorId(id);
        BDDMockito.verify(repository, BDDMockito.never()).salvar(ArgumentMatchers.any(Livro.class));
    }

    @Test
    @DisplayName("Atualizar por ID: lança IllegalArgumentException quando o ID do livro é diferente")
    void atualizarPorId_LancaIllegalArgumentException_QuandoIdDiferente() {
        Livro original = livros.getFirst();
        Livro alterado = livros.get(1);

        BDDMockito.when(repository.buscarPorId(original.getId())).thenReturn(Optional.of(original));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> service.atualizarPorId(original.getId(), alterado))
                .withMessage("O ID do livro não pode ser alterado.");

        BDDMockito.verify(repository).buscarPorId(original.getId());
        BDDMockito.verify(repository, BDDMockito.never()).salvar(ArgumentMatchers.any(Livro.class));
    }

    @Test
    @DisplayName("Deletar: lança LivroInexistenteException quando o livro não existe")
    void deletar_LancaLivroInexistenteException_QuandoLivroInexistente() {
        Long id = 999L;

        BDDMockito.when(repository.buscarPorId(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.deletar(id))
                .isInstanceOf(LivroInexistenteException.class)
                .hasMessage("Livro não encontrado.");

        BDDMockito.verify(repository).buscarPorId(id);
        BDDMockito.verify(repository, BDDMockito.never()).deletar(ArgumentMatchers.anyLong());
    }
}