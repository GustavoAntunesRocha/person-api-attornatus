package br.com.antunes.gustavo.personapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.personapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository <Pessoa, Long>{
    
}
