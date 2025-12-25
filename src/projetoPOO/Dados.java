package projetoPOO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;

public class Dados {

    // Lista principal que a aplicação toda vai usar (Observable para atualizar tabelas auto)
    private static ObservableList<Movimento> listaGlobal = FXCollections.observableArrayList();
    private static final String NOME_FICHEIRO = "dados_financeiros.txt";

    // --- MÉTODOS DE ACESSO ---
    
    public static ObservableList<Movimento> getListaGlobal() {
        return listaGlobal;
    }

    public static void adicionar(Movimento m) {
        listaGlobal.add(m);
        guardarFicheiro(); // Guarda logo que adiciona
    }

    public static void remover(Movimento m) {
        listaGlobal.remove(m);
        guardarFicheiro(); // Guarda logo que remove
    }

    // --- MÉTODOS DE FICHEIRO (Persistência) ---

    public static void guardarFicheiro() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_FICHEIRO))) {
            for (Movimento m : listaGlobal) {
                writer.write(m.paraLinhaFicheiro());
                writer.newLine();
            }
            System.out.println("Dados guardados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao guardar ficheiro: " + e.getMessage());
        }
    }

    public static void carregarFicheiro() {
        File f = new File(NOME_FICHEIRO);
        if (!f.exists()) return; // Se não existe ficheiro, não faz nada

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String linha;
            listaGlobal.clear(); // Limpa a lista atual antes de carregar
            while ((linha = reader.readLine()) != null) {
                if (!linha.isEmpty()) {
                    listaGlobal.add(Movimento.deLinhaFicheiro(linha));
                }
            }
            System.out.println("Dados carregados: " + listaGlobal.size() + " movimentos.");
        } catch (IOException e) {
            System.out.println("Erro ao ler ficheiro: " + e.getMessage());
        }
    }
}