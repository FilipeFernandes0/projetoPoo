package projetoPOO;

import java.io.*;

public class GestorFicheiros {
	
	public static final String NOME_FICHEIRO = "dados_financas.bin";
	
	
	public static void guardarDados(FinancasPoo financas)
	{
		try {
			
			FileOutputStream fileOut = new FileOutputStream(NOME_FICHEIRO);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(financas); // Grava a carteira toda de uma vez!
            
            out.close();
            fileOut.close();
            System.out.println("Dados gravados com sucesso em " + NOME_FICHEIRO);
        } catch (IOException e) {
            System.out.println("Erro ao gravar ficheiro: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	public static FinancasPoo carregarDados() {
        FinancasPoo dadosCarregados = null;
        try {
            FileInputStream fileIn = new FileInputStream(NOME_FICHEIRO);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            
            dadosCarregados = (FinancasPoo) in.readObject();
            
            in.close();
            fileIn.close();
            System.out.println("Dados carregados com sucesso!");
            
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado. A criar nova carteira...");
            return new FinancasPoo(); // Se não existir, devolve vazio
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler ficheiro: " + e.getMessage());
            return new FinancasPoo(); // Se der erro, devolve vazio para não crashar
        }
        return dadosCarregados;
    }
	
	
}
	
	


