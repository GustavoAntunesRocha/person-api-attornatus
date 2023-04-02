package br.com.antunes.gustavo.personapi.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Endereco {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String logradouro;

    private String cep;

    //Optei por utilizar String caso o endereco nao possua numero e a pessoa coloque s/n
    private String numero;

    private String cidade;

    private Boolean principal;

    public Endereco() {
    }

    public Endereco(long id, String logradouro, String cep, String numero, String cidade, Boolean principal) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.principal = principal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

	@Override
	public int hashCode() {
		return Objects.hash(cep, cidade, id, logradouro, numero, principal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(cep, other.cep) && Objects.equals(cidade, other.cidade) && id == other.id
				&& Objects.equals(logradouro, other.logradouro) && Objects.equals(numero, other.numero)
				&& Objects.equals(principal, other.principal);
	}
    
}
