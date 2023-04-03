package br.com.antunes.gustavo.personapi.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.antunes.gustavo.personapi.dto.EnderecoDTO;
import br.com.antunes.gustavo.personapi.dto.PessoaDTO;
import br.com.antunes.gustavo.personapi.model.Endereco;
import br.com.antunes.gustavo.personapi.model.Pessoa;
import br.com.antunes.gustavo.personapi.service.EnderecoService;
import br.com.antunes.gustavo.personapi.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/api/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	
	@Operation(description = "Endpoint para buscar uma pessoa pelo id ou pelo nome", responses = {
			@ApiResponse(responseCode = "200", description = "Pessoa recuperada com sucesso", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
			@ApiResponse(responseCode = "404", description = "Nenhuma pessoa encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<?> getPessoa(@RequestParam(required = false) Long id, @RequestParam(required = false) String nome){
		try {
			if(id != null) {
				return ResponseEntity.ok(pessoaService.convertToDTO(pessoaService.getById(id)));
			} else if(nome != null) {
				List<Pessoa> pessoas = pessoaService.findByName(nome);
				List<PessoaDTO> pessoaDTOs = new ArrayList<>();
				for (Pessoa pessoa : pessoas) {
					pessoaDTOs.add(pessoaService.convertToDTO(pessoa));
				}
				return ResponseEntity.ok(pessoaDTOs);
			}
			List<Pessoa> pessoas = pessoaService.findAll();
			List<PessoaDTO> pessoaDTOs = new ArrayList<>();
			for (Pessoa pessoa : pessoas) {
				pessoaDTOs.add(pessoaService.convertToDTO(pessoa));
			}
			return ResponseEntity.ok(pessoaDTOs);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Endpoint para cadastrar uma pessoa", responses = {
			@ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
			@ApiResponse(responseCode = "404", description = "Nenhuma pessoa encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PostMapping
	public ResponseEntity<?> createPessoa(@RequestBody PessoaDTO pessoaDTO){
		try {
			Pessoa pessoa = pessoaService.create(pessoaService.convertToEntity(pessoaDTO));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.convertToDTO(pessoa));
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Endpoint para atualizar uma pessoa", responses = {
			@ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
			@ApiResponse(responseCode = "404", description = "Nenhuma pessoa encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PutMapping
	public ResponseEntity<?> updatePessoa(@RequestBody PessoaDTO pessoaDTO){
		try {
			return ResponseEntity.ok(pessoaService.convertToDTO(pessoaService.update(pessoaService.convertToEntity(pessoaDTO))));
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Endpoint para deletar o cadastro de uma pessoa", responses = {
			@ApiResponse(responseCode = "200", description = "Pessoa deletada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Nenhuma pessoa encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@DeleteMapping
	public ResponseEntity<?> deletePessoa(@RequestParam Long id){
		try {
			pessoaService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Endpoint para criar um endereço para uma pessoa", responses = {
			@ApiResponse(responseCode = "200", description = "Endereço adicionado com sucesso", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
			@ApiResponse(responseCode = "404", description = "Nenhuma pessoa encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PostMapping(value = "/addEndereco")
	public ResponseEntity<?> addEndereco(@RequestParam Long idPessoa, @RequestBody EnderecoDTO enderecoDTO){
		try {
			pessoaService.addEndereco(idPessoa, enderecoService.convertToEntity(enderecoDTO));
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Endpoint para buscar os endereços de uma pessoa pelo id", responses = {
			@ApiResponse(responseCode = "200", description = "Endereços recuperados com sucesso", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
			@ApiResponse(responseCode = "404", description = "Nenhuma pessoa encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@GetMapping(value = "/enderecos")
	public ResponseEntity<?> getEnderecos(@RequestParam Long idPessoa){
		try {
			List<Endereco> enderecos = pessoaService.getEnderecos(idPessoa);
			List<EnderecoDTO> enderecoDTOs = new ArrayList<>();
			
			for (Endereco endereco : enderecos) {
				enderecoDTOs.add(enderecoService.convertToDTO(endereco));
			}
			return ResponseEntity.ok(enderecoDTOs);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Endpoint para setar um endereço de uma pessoa como sendo o principal", responses = {
			@ApiResponse(responseCode = "200", description = "Endereço setado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Nenhum endereco encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PutMapping(value = "/enderecoPrincipal")
	public ResponseEntity<?> enderecoPrincipal(@RequestParam Long idEndereco, @RequestParam Long idPessoa){
		try {
			pessoaService.setEnderecoPrincipal(idEndereco, idPessoa);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	public ApiErrorResponse handleCustomException(RuntimeException e, HttpStatus status) {
		LocalDateTime timestamp = LocalDateTime.now();
		ApiErrorResponse errorResponse = new ApiErrorResponse(status.toString(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp), e.getMessage());
		return errorResponse;
	}

}
