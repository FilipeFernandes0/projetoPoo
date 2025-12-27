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

}