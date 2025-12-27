package projetoPOO;

import java.io.Serializable;
import java.time.LocalDate;

public class Transacao implements Serializable {
	private LocalDate data;
	private double valor;
	private String descricao;
	private String tipo;
	
	public Transacao(LocalDate data, double valor, String descricao, String tipo) {
		this.data = data;
		this.valor = valor;
		this.descricao = descricao;
		this.tipo = tipo;
		
	}
	
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	@Override
    public String toString() {
        return "Data=" + data + ", Valor=" + valor + "€, Categoria=" + "descricao" + descricao;
    }
	@Override
	 public boolean equals(Object o){
	        
	        if (o!=null&&this.getClass()==o.getClass()){
	            Transacao x=(Transacao)o;
	            return this.getData()==x.getData()&&this.getValor()==x.getValor()&&this.getDescricao().equals(x.descricao)&&this.getTipo().equals(x.tipo);
	        }return false;
	    }



}
