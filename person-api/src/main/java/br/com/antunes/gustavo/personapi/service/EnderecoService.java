package br.com.antunes.gustavo.personapi.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.personapi.dto.EnderecoDTO;
import br.com.antunes.gustavo.personapi.model.Endereco;
import br.com.antunes.gustavo.personapi.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    
    private final ModelMapper modelMapper;

    public EnderecoService(EnderecoRepository enderecoRepository, ModelMapper modelMapper) {
        this.enderecoRepository = enderecoRepository;
        this.modelMapper = modelMapper;
    }

    public Endereco create(Endereco endereco) {
        return enderecoRepository.save(endereco);

    }

    public Endereco getById(long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado com id: " + id));
    }

    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public void update(Endereco endereco) {
        enderecoRepository.findById(endereco.getId())
                .orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado com id: " + endereco.getId()));
        
        enderecoRepository.save(endereco);
    }

    public void delete(long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado com id: " + id));
        enderecoRepository.delete(endereco);
    }
    
    public EnderecoDTO convertToDTO(Endereco endereco) {
		return modelMapper.map(endereco, EnderecoDTO.class);
	}
	
	public Endereco convertToEntity(EnderecoDTO enderecoDTO) {
		return modelMapper.map(enderecoDTO, Endereco.class);
	}
}