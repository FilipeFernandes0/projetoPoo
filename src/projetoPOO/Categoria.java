package projetoPOO;

import java.io.Serializable;

public class Categoria implements Serializable {
	
	public static final String[] categoria = {
			"Alimentacao",
		    "Saude",
		    "Transporte",
		   "Ferias",
		    "Vestuario",
		    "Casa",
		    "Subscricoes",
		    "Outros",
	};
   
    
    private String nome;

    public Categoria(String nome) {this.nome = nome;}

    public Categoria() {this.nome = "";}
    //getters e setters
    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}
}