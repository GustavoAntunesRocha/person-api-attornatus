package br.com.antunes.gustavo.personapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.personapi.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
    
}
