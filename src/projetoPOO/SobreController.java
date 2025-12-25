package projetoPOO;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

// AQUI ESTÁ O TRUQUE: "extends sceneController"
// Isto permite que esta página use a navegação que tu já criaste!
public class SobreController extends sceneController implements Initializable {

    // Aqui vais ligar apenas as coisas desta página
    @FXML private Label lblTotalSobre; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Página de Sobre carregada com sucesso!");
        
    }
}