package projetoPOO;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ReceitasController extends sceneController implements Initializable {

    @FXML private Label lblTotalReceitas; 
    @FXML private TableView<Receita> tabelaReceitas; 

    @FXML private TableColumn<Receita, LocalDate> colData;
    @FXML private TableColumn<Receita, String> colDescricao;
    @FXML private TableColumn<Receita, String> colTipoReceita;
    @FXML private TableColumn<Receita, Double> colValor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Configurar Colunas
        colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
        colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        colTipoReceita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        colValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));

        // Formatação Verde
        colValor.setCellFactory(column -> new TableCell<Receita, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); } 
                else {
                    setText(String.format("%.2f €", item));
                    setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                }
            }
        });

        // 2. CARREGAR DADOS INICIAIS
        carregarDados();
    }

    // --- NOVO MÉTODO: RECARREGA A TABELA ---
    private void carregarDados() {
    	tabelaReceitas.getItems().clear(); 
        
        if (financas == null) {
            System.out.println("Erro: Não há finanças ligadas à tabela.");
            return;
        }

        ObservableList<Receita> listaVisual = FXCollections.observableArrayList();
        
  
        listaVisual.addAll(financas.receitas); 
        
        // 5. "Colar" a lista na tabela
        tabelaReceitas.setItems(listaVisual);
        
        if (lblTotalReceitas != null) {
            // Opção A: Se já criaste o método getTotalReceitas() no FinancasPoo (Recomendado)
            double total = financas.getTotalReceitas();

            /* // Opção B: Se ainda não tens o método lá, calculas aqui:
            double total = 0;
            for (Receita r : financas.receitas) {
                total += r.getValor();
            }
            */

            // Atualiza o texto do Label
            lblTotalReceitas.setText(String.format("%.2f €", total));
        }
    }

    @FXML
    void onNovaReceitaClick() {
        abrirFormulario("Receita");
        // DEPOIS DE FECHAR A JANELA, ATUALIZA A TABELA:
        carregarDados();
    }

    @FXML
    void onEditarClick() {
        editarGenerico(tabelaReceitas, "Receita");
        // O editarGenerico já faz refresh, mas se mudares valores que afetam filtros, é melhor:
        carregarDados();
    }

    @FXML
    void onEliminarClick() {
        eliminarGenerico(tabelaReceitas);
        // Garante que o total é recalculado
        carregarDados();
    }
}