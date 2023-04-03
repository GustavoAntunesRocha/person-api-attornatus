package br.com.antunes.gustavo.personapi.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.antunes.gustavo.personapi.model.Endereco;
import br.com.antunes.gustavo.personapi.model.Pessoa;
import br.com.antunes.gustavo.personapi.repository.PessoaRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PessoaRepository pessoaRepository;

//	@MockBean
//	private PessoaService pessoaService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetAllPessoas() throws Exception {
		Endereco endereco = new Endereco(1L, "rua 51", "74000", "362", "Goiania", true);
		Endereco endereco2 = new Endereco(2L, "rua 5", "74000", "223", "Goiania", true);
		List<Pessoa> pessoas = Arrays.asList(new Pessoa(1L, "Gustavo Antunes", LocalDate.of(1991, 7, 5), Arrays.asList(endereco)),
				new Pessoa(0L, "Joao Roberto", LocalDate.of(1999, 9, 9), Arrays.asList(endereco2)));
		
		
		String json = objectMapper.writeValueAsString(pessoas);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		
		when(pessoaRepository.findAll()).thenReturn(pessoas);

		mockMvc.perform(get("/api/pessoa")).andExpect(status().isOk()).andExpect(
				content().string(containsString(json)));
	}

	@Test
	public void testGetPessoaById() throws Exception {
		Endereco endereco = new Endereco(1L, "rua 5", "74000", "223", "Goiania", true);
		Pessoa pessoa = new Pessoa(1L, "Gustavo Antunes", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));
		
		String json = objectMapper.writeValueAsString(pessoa);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

		mockMvc.perform(get("/api/pessoa").param("id", "1"))
	       .andExpect(status().isOk())
	       .andExpect(content().json(json));

	}

    @Test
    public void testCreatePessoa() throws Exception {
    	Endereco endereco = new Endereco(1L, "rua 5", "74000", "223", "Goiania", true);
		Pessoa pessoa = new Pessoa(1L, "Gustavo Antunes", LocalDate.of(1991, 7, 5), Arrays.asList(endereco));
		
		String json = objectMapper.writeValueAsString(pessoa);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        mockMvc.perform(post("/api/pessoa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().json(json));
    }

}
