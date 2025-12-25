package projetoPOO;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

// AQUI ESTÁ O TRUQUE: "extends sceneController"
// Isto permite que esta página use a navegação que tu já criaste!
public class ReceitasController extends sceneController implements Initializable {

    // Aqui vais ligar apenas as coisas desta página
    @FXML private Label lblTotalReceitas; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Página de Receitas carregada com sucesso!");
        
        // Exemplo: Mudar o valor do cartão verde via código
        // lblTotalReceitas.setText("2.500,00 €");
    }
}