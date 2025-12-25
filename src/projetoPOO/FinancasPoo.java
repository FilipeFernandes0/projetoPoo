package projetoPOO;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class FinancasPoo implements Serializable{
    public ArrayList<DespesasPoo> despesas;
    public ArrayList<Receita> receitas;

    public FinancasPoo(){
        despesas=new ArrayList<>();
        receitas=new ArrayList<>();    
    }
        //Adicionar uma receita
	public void adicionarReceita(Receita r) {
		receitas.add(r);
	}
	//Adicionar uma despesa
	public void adicionarDespesa(DespesasPoo d) {
		despesas.add(d);
	}
	//Remover uma receita
	public void eliminarReceita(Receita r) {
		if(receitas.remove(r)) {
		}
	}
	//Remover uma despesa
	public void eliminarDespesa(DespesasPoo d) {
		despesas.remove(d);
	}
	
	//Atualizar uma receita (mudar o que lá está)
	public void atualizarReceita(Receita antiga, Receita nova) {
		int posicao = receitas.indexOf(antiga);
		if(posicao>=0) {
			receitas.set(posicao, nova);
		}
	}
	//Atualizar uma Despesa (mudar o que lá está)
	public void atualizarDespesa(DespesasPoo antiga, DespesasPoo nova) {
		int posicao = despesas.indexOf(antiga);
		if(posicao >= 0) {
			despesas.set(posicao, nova);
		}
	}//
       public double getSaldo(int ano) {
        double saldoR = 0.0;
        double saldoD=0.0;
        for (int i = 0; i < receitas.size(); i++) {
            if(receitas.get(i).getData().getYear()==ano){
            saldoR = saldoR + receitas.get(i).getValor();
            }
        }
        for (int i = 0; i < despesas.size(); i++) {
            if(despesas.get(i).getData().getYear()==ano){
            saldoD = saldoD + despesas.get(i).getValor();}
        }return saldoR-saldoD;
    }
        public double getSaldo() {///quadrado laranja
        double saldoR = 0.0;
        double saldoD=0.0;
        for (int i = 0; i < receitas.size(); i++) {
            saldoR = saldoR + receitas.get(i).getValor();
        }
        for (int i = 0; i < despesas.size(); i++) {
            saldoD = saldoD + despesas.get(i).getValor();
        }return saldoR-saldoD;
    }
     public double totalPorDia(LocalDate dia) {
        double total = 0.0;
        for (int i = 0; i < despesas.size(); i++) {
            if (despesas.get(i).getData().equals(dia)) {
                total = total + despesas.get(i).getValor();
            }  
        }return total;
    }
      public double totalPorMes(int ano, int mes) {
        double total = 0.0;
        for (int i = 0; i < despesas.size(); i++) {
            if (despesas.get(i).getData().getYear() == ano && despesas.get(i).getData().getMonthValue() == mes) {
               total = total + despesas.get(i).getValor();
            }
            }return total;
        }
         public double totalPorCategoria(Categoria c) {
        double total = 0.0;
        if (c == null){ return 0.0;}
        for (int i = 0; i < despesas.size(); i++) {
              if (despesas.get(i).getC().getNome().equalsIgnoreCase(c.getNome())) {
                total =total+ despesas.get(i).getValor();
                }
             }return total;
        } 
    
    public double totalPorCategoriaEMes(Categoria c, int ano, int mes) {
    double total = 0.0;
    if (c == null){ return 0.0;}

    for (int i = 0; i < despesas.size(); i++) {
            if (despesas.get(i).getC().equals(c) && despesas.get(i).getData().getYear()==ano && despesas.get(i).getData().getMonthValue()==mes) {
                total = total + despesas.get(i).getValor();
            }
        } return total;
    }
    public double totalCatMesDia(Categoria c, int ano, int mes, int dia){
        double total=0.0;
        if(c==null){return 0.0;}
        for (int i = 0; i < despesas.size(); i++) {
            if(despesas.get(i).getC().equals(c) && despesas.get(i).getData().getYear()==ano && despesas.get(i).getData().getMonthValue()==mes && despesas.get(i).getData().getDayOfMonth()==dia)
            {total=total+despesas.get(i).getValor();}
        }return total;
    }//despesas fixas
     public double despFixasAno(Categoria c, int ano){
        double total=0.0;
         if(c==null){return 0.0;}
        for(int i=0; i < despesas.size(); i++){
            if(despesas.get(i).getFixas()==true && despesas.get(i).getData().getYear()==ano &&despesas.get(i).getC().equals(c))
               { total=total+despesas.get(i).getValor();}
        }return total; 
    }//despesasFixasMes
    public double despFixasMes(Categoria c, int ano, int mes){
        double total=0.0;
         if(c==null){return 0.0;}
        for(int i=0; i < despesas.size(); i++){
            if(despesas.get(i).getFixas()==true && despesas.get(i).getData().getYear()==ano &&despesas.get(i).getData().getMonthValue()==mes &&despesas.get(i).getC().equals(c))
               { total=total+despesas.get(i).getValor();}
        }return total; 
    }
    public double medGastoDia(int mes, int ano){//
        double total=0.0;
        int x=0;
        for(int i=0;i<despesas.size();i++){
            if(despesas.get(i).getData().getMonthValue() == mes && despesas.get(i).getData().getYear() == ano){
            int diaRepetido = 0;

            for(int j=0;j<i;j++){
                if(despesas.get(j).getData().equals(despesas.get(i).getData())){
                diaRepetido++;
                }
            }
            if(diaRepetido == 0){ 
            total += despesas.get(i).getValor();
            x++;
            }
            }
        }if (x == 0){return 0.0;}
        return total/x;
    }
}
