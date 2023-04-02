package br.com.antunes.gustavo.personapi.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.personapi.dto.EnderecoDTO;
import br.com.antunes.gustavo.personapi.dto.PessoaDTO;
import br.com.antunes.gustavo.personapi.model.Endereco;
import br.com.antunes.gustavo.personapi.model.Pessoa;
import br.com.antunes.gustavo.personapi.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    
    private final EnderecoService enderecoService;
    
    private final ModelMapper modelMapper;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoService enderecoService, ModelMapper modelMapper) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoService = enderecoService;
        this.modelMapper = modelMapper;
    }

    public Pessoa create(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);

    }

    public Pessoa getById(long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id: " + id));
    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa update(Pessoa pessoa) {
        Pessoa pessoaRetrieved = pessoaRepository.findById(pessoa.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id: " + pessoa.getId()));
        if(pessoa.getEnderecos() == null) {
        	pessoa.setEnderecos(pessoaRetrieved.getEnderecos());
        } else {
        	List<Endereco> enderecos = pessoa.getEnderecos();
        	for (Endereco endereco : enderecos) {
				enderecoService.update(endereco);
			}
        }
        pessoa.setEnderecos(pessoaRetrieved.getEnderecos());
        return pessoaRepository.save(pessoa);
    }

    public void delete(long id) {
    	Pessoa pessoa = getById(id);
        pessoaRepository.delete(pessoa);
    }

    public void addEndereco(long pessoaId, Endereco endereco) {
    	Pessoa pessoa = getById(pessoaId);
    	
    	List<Endereco> enderecos = pessoa.getEnderecos();
    	enderecos.add(endereco);
    	pessoaRepository.save(pessoa);
    	//Verifica se o endereco passado foi setado como sendo o principal e chama o metodo para atualizar os outros
    	if(endereco.getPrincipal()) {
    		setEnderecoPrincipal(enderecos.get(enderecos.size() - 1).getId(), pessoaId);
    	}
    }
    
    //Varre todos os enderecos da pessoa para atualizar qual é o principal
    public void setEnderecoPrincipal(long enderecoId, long pessoaId) {
    	Pessoa pessoa = getById(pessoaId);
    	List<Endereco> enderecos = pessoa.getEnderecos();
    	for (Endereco endereco : enderecos) {
			if(endereco.getPrincipal()) {
				endereco.setPrincipal(false);
			}
		}
    	Endereco endereco = enderecoService.getById(enderecoId);
    	endereco.setPrincipal(true);
    	
    	pessoa.setEnderecos(enderecos);
    	pessoaRepository.save(pessoa);
    }
    
    public List<Pessoa> findByName(String nome) {
    	List<Pessoa> pessoas = pessoaRepository.findByNomeContainingIgnoreCase(nome);
    	if(pessoas.isEmpty()) {
    		throw new EntityNotFoundException("Nenhuma pessoa encontrada com o nome: " + nome);
    	}
    	return pessoas;
    }
    
    public List<Endereco> getEnderecos(long pessoaId){
    	Pessoa pessoa = getById(pessoaId);
    	return pessoa.getEnderecos();
    }
    
    public PessoaDTO convertToDTO(Pessoa pessoa) {
    	PessoaDTO pessoaDTO = modelMapper.map(pessoa, PessoaDTO.class);
    	List<EnderecoDTO> enderecoDTOs = new ArrayList<>();
    	for (Endereco endereco : pessoa.getEnderecos()) {
			enderecoDTOs.add(enderecoService.convertToDTO(endereco));
		}
    	pessoaDTO.setEnderecoDTO(enderecoDTOs);
		return pessoaDTO;
	}
	
	public Pessoa convertToEntity(PessoaDTO pessoaDTO) {
		Pessoa pessoa = modelMapper.map(pessoaDTO, Pessoa.class);
    	List<Endereco> enderecos = new ArrayList<>();
    	if(pessoaDTO.getEnderecoDTOs() != null) {
	    	for (EnderecoDTO enderecoDTO : pessoaDTO.getEnderecoDTOs()) {
	    		enderecos.add(enderecoService.convertToEntity(enderecoDTO));
			}
	    	pessoa.setEnderecos(enderecos);
    	}
		return pessoa;
	}
}
