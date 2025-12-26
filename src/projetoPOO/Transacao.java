package projetoPOO;

import java.time.LocalDate;

public class Transacao {
	private LocalDate data;
	private double valor;
	
	public Transacao(LocalDate data, double valor) {
		this.data = data;
		this.valor = valor;
		
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
	
	
	@Override
    public String toString() {
        return "Data=" + data + ", Valor=" + valor + "€, Categoria=";
    }
	@Override
	 public boolean equals(Object o){
	        
	        if (o!=null&&this.getClass()==o.getClass()){
	            Transacao x=(Transacao)o;
	            return this.getData()==x.getData()&&this.getValor()==x.getValor();
	        }return false;
	    }

}
