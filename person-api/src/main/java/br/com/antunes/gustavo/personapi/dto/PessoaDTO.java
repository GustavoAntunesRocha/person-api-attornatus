package br.com.antunes.gustavo.personapi.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PessoaDTO {
    
    private long id;

    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate dataNascimento;

    private List<EnderecoDTO> enderecoDTOs;

    public PessoaDTO() {
    }

    public PessoaDTO(long id, String nome, LocalDate dataNascimento, List<EnderecoDTO> enderecoDTOs) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecoDTOs = enderecoDTOs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<EnderecoDTO> getEnderecoDTOs() {
        return enderecoDTOs;
    }

    public void setEnderecoDTO(List<EnderecoDTO> enderecoDTOs) {
        this.enderecoDTOs = enderecoDTOs;
    }
}
