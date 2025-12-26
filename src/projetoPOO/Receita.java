package projetoPOO;

import java.time.LocalDate;
import java.io.Serializable;

public class Receita extends Transacao implements Serializable{
	private TipoReceita tp;
	
	public Receita(LocalDate data, double valor, TipoReceita tp) {
		super(data, valor);
		this.tp=tp;
		
	}
	
	public TipoReceita getTp() {
		return tp;
	}

	public void setTp(TipoReceita tp) {
		this.tp = tp;
	}
	
	
	//toString
	@Override
	public String toString() {
		return super.toString() + "Tipo Receita" + tp;
	}
	
	//equals
	@Override
	public boolean equals(Object obj) {
		if(obj != null && this.getClass() == obj.getClass()) {
			Receita r = (Receita) obj;
			return tp.equals(r.tp);
				   
				   
				   
		}
		return false;
	}
	
	//clone
	@Override
	public Receita clone() {
		 return new Receita(
		            this.getData(),   
		            this.getValor(),  
		            this.tp        
		        );    
	}

}