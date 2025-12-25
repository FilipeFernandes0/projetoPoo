package projetoPOO;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

// IMPORTANTE: Importar a tua classe de categorias
//import myinputs.Categoria; 

public class DespesasController extends sceneController implements Initializable {

    // Ligações do FXML
    @FXML private Label lblTotalDespesas;
   // @FXML private ComboBox<String> cmbCategoria; // A caixa de seleção

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Página de Despesas aberta!");
        
        // --- CARREGAR AS CATEGORIAS DA TUA CLASSE ---
        // Vamos usar os valores que definiste na classe Categoria
        /*Categoria cat = new Categoria();
        
        cmbCategoria.getItems().addAll(
            cat.a,   // "Alimentacao"
            cat.s,   // "Saude"
            cat.t,   // "Transporte"
            cat.f,   // "Ferias"
            cat.v,   // "Vestuario"
            cat.c,   // "Casa"
            cat.sbc, // "Subscricoes"
            "Outros" // Podes adicionar extras se quiseres
        );
        */
        // Definir um valor de exemplo para o cartão vermelho
        lblTotalDespesas.setText("450,00 €");
    }
}