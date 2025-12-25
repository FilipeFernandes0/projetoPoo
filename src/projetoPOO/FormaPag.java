package projetoPOO;

public class FormaPag {
    public String d="Dinheiro";
    public String c="Cartao";
    public String t="transferencia";
    public String m="MBway";

    public String formaPagamento;
    public FormaPag(String fp){this.formaPagamento=fp;}
    public FormaPag(){this.formaPagamento="";}

}