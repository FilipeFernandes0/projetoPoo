package projetoPOO;
import java.io.Serializable;
import java.time.LocalDate;

public class DespesasPoo extends Transacao implements Serializable{
    
    private FormaPag f;
    //lembrar dar print no fixas
    private Categoria c;
    private boolean fixas=false;//despesa fixa=true, despesa nao fixa =false

    public DespesasPoo(LocalDate data, double valor, Categoria c, FormaPag f, boolean fx) {
        super(data, valor);
        this.c = c;
        this.f = f;
        this.fixas=fx;
    }
   
    //getters
    public Categoria getC() {
		return c;
	}
	
    public FormaPag getF(){return f;}//maneira como pagou
    
    public boolean getFixas() {return fixas;}
    //setters
    public void setF(FormaPag forma){this.f=forma;}
     
    public void setC(Categoria c) {
		this.c = c;
	}
    public void setFixa(boolean f) {this.fixas= f;}
    
    
    /*metodo clone*/
    @Override
   public DespesasPoo clone() {
	   return new DespesasPoo(
	            this.getData(),   
	            this.getValor(),  
	            this.c,      
	            this.f,           
	            this.fixas        
	        );    
	   
   }
   
   @Override
    public boolean equals(Object o){
        if (o!=null&&this.getClass()==o.getClass()) {
            DespesasPoo x=(DespesasPoo)o;
            return fixas == x.fixas && f.equals(x.f)&&this.getC().equals(x.getC());
       }
        return false;
            
        	
    }
    @Override
    public String toString() {
        return  super.toString() + "formaPagamento=" + f.formaPagamento + "Despesa fixa" + fixas + "categoria" + c.getNome();/*nome e formaPagameto vem das classes respetivas */
    } 
}