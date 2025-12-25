package projetoPOO;

public class Categoria {
    public String a="Alimentacao";
    public String s="Saude";
    public String t="Transporte";
    public String f="Ferias";
    public String v="Vestuario";
    public String c="Casa";
    public String sbc="Subscricoes";
    
    public String nome;

    public Categoria(String nome) {this.nome = nome;}

    public Categoria() {this.nome = "";}
    //getters e setters
    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}
}