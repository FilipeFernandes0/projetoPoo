package projetoPOO;

import java.io.Serializable;

public class Categoria implements Serializable {

	public static final String[] categoria = { "Alimentação", "Saude", "Transporte", "Ferias", "Vestuario", "Casa",
			"Subscrições", "Outros", };

	private String nome;

	public Categoria(String nome) {
		this.nome = nome;
	}

	public Categoria() {
		this.nome = "";
	}

	// getters e setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// Isto faz com que apareça "Alimentacao" em vez de código estranho
	@Override
	public String toString() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Categoria c = (Categoria) obj;
		return nome != null && nome.equalsIgnoreCase(c.nome);
	}
}