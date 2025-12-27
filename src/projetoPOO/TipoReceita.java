package projetoPOO;

import java.io.Serializable;

public class TipoReceita implements Serializable {
	public static final String[] tpReceita = {
	        "Ordenado", 
	        "Investimento", 
	        "Mesada",
	        "Outros"
	    };
    

	private String tipo;

    public TipoReceita(String tp) { this.tipo = tp; }
    public TipoReceita() { this.tipo = ""; }

    public String getTipo() { return tipo; }
    
    @Override
    public String toString() { return tipo;}

}
