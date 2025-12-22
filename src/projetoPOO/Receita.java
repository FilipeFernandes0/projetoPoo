package projetoPOO;

import java.time.LocalDate;
import java.io.Serializable;

public class Receita implements Serializable{
	private double valor;
	private String descricao;
	private LocalDate data;
	private String categoria;
	
	public Receita(double valor, String descricao, String categoria, LocalDate data) {
		this.valor=valor;
		this.descricao=descricao;
		this.categoria=categoria;
		this.data=data;
	}
	
	//Getters
	public double getValor() {
		return valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public LocalDate getData() {
		return data;
	}
	public String getCategoria() {
		return categoria;
	}
	
	//Setters
	public void setValor(double valor) {
		this.valor=valor;
	}
	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}
	public void setData(LocalDate data) {
		this.data=data;
	}
	public void setCategoria(String categoria) {
		this.categoria=categoria;
	}
	
	//toString
	@Override
	public String toString() {
		return "Receita ( " + "Valor: " + valor + " | Data: " + data + " | Categoria: " + categoria + " | Descricao: " + descricao + " )";
	}
	
	//equals
	@Override
	public boolean equals(Object obj) {
		if(obj != null && this.getClass() == obj.getClass()) {
			Receita r = (Receita) obj;
			return this.valor == r.valor &&
				   this.descricao.equals(r.descricao) &&
				   this.categoria.equals(r.categoria) &&
				   this.data.equals(r.data);
		}
		return false;
	}
	
	//clone
	@Override
	public Receita clone() {
		return new Receita(this.valor, this.descricao, this.categoria, this.data);
	}
}