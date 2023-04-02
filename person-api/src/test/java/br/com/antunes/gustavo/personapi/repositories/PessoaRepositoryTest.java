package br.com.antunes.gustavo.personapi.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.antunes.gustavo.personapi.model.Endereco;
import br.com.antunes.gustavo.personapi.model.Pessoa;
import br.com.antunes.gustavo.personapi.repository.PessoaRepository;

@SpringBootTest
public class PessoaRepositoryTest {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Test
	public void testSavePessoa() {
		Endereco endereco = new Endereco(0L, "rua 3", "74000-00", "5", "Goiania", true);
		Pessoa pessoa = new Pessoa(0L, "Gustavo", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		assertNotNull(pessoaSalva.getId());
	}

	@Test
	public void testUpdatePessoa() {
		Endereco endereco = new Endereco(0L, "rua 3", "74000-00", "5", "Goiania", true);
		Pessoa pessoa = new Pessoa(0L, "Gustavo", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		pessoaSalva.setNome("Rodrigo");

		Pessoa pessoaAtual = pessoaRepository.save(pessoaSalva);

		assertEquals("Rodrigo", pessoaAtual.getNome());
	}

	@Test
	public void testGetPessoa() {
		Endereco endereco = new Endereco(0L, "rua 3", "74000-00", "5", "Goiania", true);
		Pessoa pessoa = new Pessoa(0L, "Gustavo", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		Pessoa pessoaBuscada = pessoaRepository.findById(pessoaSalva.getId()).get();

		assertThat(pessoaSalva).usingRecursiveComparison().ignoringFields("enderecos").isEqualTo(pessoaBuscada);
	}

	@Test
	public void testDeletePessoa() {
		Endereco endereco = new Endereco(0L, "rua 3", "74000-00", "5", "Goiania", true);
		Pessoa pessoa = new Pessoa(0L, "Gustavo", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		pessoaRepository.delete(pessoaSalva);

		Optional<Pessoa> pessoaBuscada = pessoaRepository.findById(pessoaSalva.getId());

		assertThrows(NoSuchElementException.class, () -> pessoaBuscada.get());
	}
}
