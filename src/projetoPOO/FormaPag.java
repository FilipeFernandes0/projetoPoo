package projetoPOO;

import java.io.Serializable;

public class FormaPag implements Serializable {
	public static final String[] fp = {
			 "Dinheiro",
			 "Cartao",
			 "transferencia",
			 "MBway",
	};
   

    private String formaPagamento;
    public FormaPag(String fp){this.formaPagamento=fp;}
    public FormaPag(){this.formaPagamento="";}
    public String getFormaPagamento() {return formaPagamento;}

    public void setFormaPagamento(String fp) {this.formaPagamento = fp;}
    
    // Isto faz com que apareça algo em vez de código estranho
    @Override
    public String toString() {
        return formaPagamento;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FormaPag f = (FormaPag) obj;
        return formaPagamento != null && formaPagamento.equalsIgnoreCase(f.formaPagamento);
    }
}