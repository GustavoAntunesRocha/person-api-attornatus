package br.com.antunes.gustavo.personapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.personapi.model.Endereco;
import br.com.antunes.gustavo.personapi.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
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
}