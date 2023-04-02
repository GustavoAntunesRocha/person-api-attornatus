package br.com.antunes.gustavo.personapi.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.antunes.gustavo.personapi.model.Endereco;
import br.com.antunes.gustavo.personapi.model.Pessoa;
import br.com.antunes.gustavo.personapi.service.PessoaService;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class PessoaServiceTest {

	@Autowired
	private PessoaService pessoaService;
	
	@Test
	public void testSavePessoa() {
		Endereco endereco = new Endereco(0L, "rua 3", "74000-00", "5", "Goiania", true);
		Pessoa pessoa = new Pessoa(0L, "Gustavo", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));

		Pessoa pessoaSalva = pessoaService.create(pessoa);

		assertNotNull(pessoaSalva.getId());
	}

	@Test
	public void testUpdatePessoa() {
		Endereco endereco = new Endereco(0L, "rua 3", "74000-00", "5", "Goiania", true);
		Pessoa pessoa = new Pessoa(0L, "Gustavo", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));

		Pessoa pessoaSalva = pessoaService.create(pessoa);

		pessoaSalva.setNome("Rodrigo");

		Pessoa pessoaAtual = pessoaService.update(pessoaSalva);

		assertEquals("Rodrigo", pessoaAtual.getNome());
	}

	@Test
	public void testGetPessoa() {
		Endereco endereco = new Endereco(0L, "rua 3", "74000-00", "5", "Goiania", true);
		Pessoa pessoa = new Pessoa(0L, "Gustavo", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));

		Pessoa pessoaSalva = pessoaService.create(pessoa);

		Pessoa pessoaBuscada = pessoaService.getById(pessoaSalva.getId());

		assertThat(pessoaSalva).usingRecursiveComparison().ignoringFields("enderecos").isEqualTo(pessoaBuscada);
	}

	@Test
	public void testDeletePessoa() {
		Endereco endereco = new Endereco(0L, "rua 3", "74000-00", "5", "Goiania", true);
		Pessoa pessoa = new Pessoa(0L, "Gustavo", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));

		Pessoa pessoaSalva = pessoaService.create(pessoa);

		pessoaService.delete(pessoaSalva.getId());

		assertThrows(EntityNotFoundException.class, () -> pessoaService.getById(pessoaSalva.getId()));
	}
}
