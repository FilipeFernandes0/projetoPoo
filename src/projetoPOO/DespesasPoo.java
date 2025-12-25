package projetoPOO;
import java.io.Serializable;
import java.time.LocalDate;

public final class DespesasPoo extends FinancasPoo implements Serializable{
    private LocalDate data;
    private double valor;//quanto foi gasto
    private Categoria c;//metodos
    private FormaPag f;
    //lembrar dar print no fixas
    private boolean fixas=false;//despesa fixa=true, despesa nao fixa =false

    public DespesasPoo(LocalDate d, double v,Categoria categoria, FormaPag f,boolean fx)throws ValorNegativo, DataFutura,FormaPagamento{
        if (d.isAfter(LocalDate.now())) {
        throw new DataFutura("A data não pode ser futura.");
        }else{ this.data=d;}
       
        if (c != null) {
            this.c = categoria;
        } else {this.c = new Categoria();}
        //this.f=f;
        setF(f);
        setValor(v);
        this.fixas=fx;
    }
    public DespesasPoo(LocalDate d,double v)throws ValorNegativo{
        this.data=d;
        setValor(v);
        this.c = new Categoria();
        this.f = new FormaPag(); 
    }
    //getters
    public LocalDate getData(){return data;}
    public double getValor(){return valor;}
    public Categoria getC(){return c;}
    public FormaPag getF(){return f;}//maneira como pagou
    public boolean getFixas() {return fixas;}
    //setters
    public void setData(LocalDate d){this.data=d;}
    public void setF(FormaPag forma)throws  FormaPagamento{
        if(forma!=null){this.f=forma;}
        else{throw new FormaPagamento("Forma de pagamento invalido");}
    }
    public void setCategoria(Categoria c){this.c=c;}
    public void setValor(double valor)throws ValorNegativo {
        if(valor < 0) throw new ValorNegativo("Valor não pode ser negativo.");
        else{this.valor = valor;}
    }
    public void setFixa(boolean f) {this.fixas= f;}
    /*metodo clone*/
    public DespesasPoo copia() throws ValorNegativo, DataFutura, FormaPagamento {
        return new DespesasPoo(this.data, this.valor, this.c, this.f, this.fixas);
    }
    public boolean igual(Object o){
        boolean i=false;
        if (o!=null&&o.getClass()==this.getClass()){
            DespesasPoo x=(DespesasPoo)o;
            i=i&&this.data==x.data&&this.valor==x.valor&&this.fixas==x.fixas&&this.c.equals(x.c)&&this.f.equals(x.f);
        }return i;
    }
    @Override
    public String toString() {
        return "Despesa [data=" + data + ", valor=" + valor + ", categoria=" + c.nome +
               ", formaPagamento=" + f.formaPagamento + "]";/*nome e formaPagameto vem das classes respetivas */
    }
}