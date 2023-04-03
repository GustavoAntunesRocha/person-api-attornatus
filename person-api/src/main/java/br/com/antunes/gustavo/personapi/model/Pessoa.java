package br.com.antunes.gustavo.personapi.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Pessoa {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    @JsonProperty("enderecoDTOs")
    private List<Endereco> enderecos;

    public Pessoa() {
    }

    public Pessoa(long id, String nome, LocalDate dataNascimento, List<Endereco> enderecos) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

	@Override
	public int hashCode() {
		return Objects.hash(dataNascimento, enderecos, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(enderecos, other.enderecos)
				&& id == other.id && Objects.equals(nome, other.nome);
	}

}
