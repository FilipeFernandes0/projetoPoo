package projetoPOO;

import java.net.URL;
import java.util.ResourceBundle;

// Importações necessárias para os controlos visuais
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class HomeController extends sceneController implements Initializable {

    // --- LIGAÇÕES COM O SCENE BUILDER (fx:id) ---
    // Certifica-te que estes nomes são IGUAIS aos que puseste no fx:id
    @FXML private Label lblSaldo;
    @FXML private Label lblReceitas;
    @FXML private Label lblDespesas;
    
    // Tabela (ainda genérica <?> porque não criámos a classe Movimento)
    @FXML private TableView<?> tabelaMovimentos; 

    // --- MÉTODO DE ARRANQUE ---
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Este código corre assim que a janela abre
        System.out.println("A iniciar o Dashboard...");
        
        // Chama a função para mudar os números
        atualizarValoresFicticios();
    }

    // Método auxiliar para testar se os labels funcionam
    private void atualizarValoresFicticios() {
        lblSaldo.setText("1.500,00 €");
        lblReceitas.setText("2.000,00 €");
        lblDespesas.setText("500,00 €");
    }

    // --- AÇÕES DOS BOTÕES (On Action) ---
    
    @FXML
    public void abrirNovaDespesa() {
        // Cria uma janela pop-up de informação
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Nova Despesa");
        alerta.setHeaderText(null); // Sem cabeçalho para ficar mais limpo
        alerta.setContentText("Aqui vai abrir o formulário de Despesas!");
        
        // Mostra a janela e espera que feches
        alerta.showAndWait();
    }

    @FXML
    public void abrirNovaReceita() {
        // Cria uma janela pop-up de informação
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Nova Receita");
        alerta.setHeaderText(null);
        alerta.setContentText("Aqui vai abrir o formulário de Receitas!");
        
        alerta.showAndWait();
    }
}