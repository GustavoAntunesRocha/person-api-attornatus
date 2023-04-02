package br.com.antunes.gustavo.personapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.personapi.model.Endereco;
import br.com.antunes.gustavo.personapi.model.Pessoa;
import br.com.antunes.gustavo.personapi.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;
    
    private final EnderecoService enderecoService;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoService enderecoService) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoService = enderecoService;
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
        pessoaRepository.findById(pessoa.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id: " + pessoa.getId()));
        
        return pessoaRepository.save(pessoa);
    }

    public void delete(long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id: " + id));
        pessoaRepository.delete(pessoa);
    }

    public void addEndereco(long pessoaId, Endereco endereco) {
    	Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id: " + pessoaId));
    	
    	//Verifica se o endereco passado foi setado como sendo o principal e chama o metodo para atualizar os outros
    	if(endereco.getPrincipal()) {
    		setEnderecoPrincipal(endereco.getId(), pessoaId);
    	}
    	pessoa.getEnderecos().add(endereco);
    	pessoaRepository.save(pessoa);
    }
    
    //Varre todos os enderecos da pessoa para atualizar qual é o principal
    public void setEnderecoPrincipal(long enderecoId, long pessoaId) {
    	Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id: " + pessoaId));
    	List<Endereco> enderecos = pessoa.getEnderecos();
    	for (Endereco endereco : enderecos) {
			if(endereco.getPrincipal()) {
				endereco.setPrincipal(false);
			}
		}
    	Endereco endereco = enderecoService.getById(enderecoId);
    	endereco.setPrincipal(true);
    	enderecoService.update(endereco);
    }
}
