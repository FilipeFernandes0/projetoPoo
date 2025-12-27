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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DespesasController extends sceneController implements Initializable {

    @FXML private Label lblTotalDespesas;
    @FXML private TableView<DespesasPoo> tabelaDespesas;

    // --- LIGAÇÕES DAS COLUNAS ---
    @FXML private TableColumn<DespesasPoo, LocalDate> colData;
    @FXML private TableColumn<DespesasPoo, String> colDescricao;
    @FXML private TableColumn<DespesasPoo, String> colCategoria;
    @FXML private TableColumn<DespesasPoo, String> colPagamento;
    @FXML private TableColumn<DespesasPoo, String> colFixa; 
    @FXML private TableColumn<DespesasPoo, Double> colValor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Página de Despesas carregada!");
        
        try {
            // --- 1. CONFIGURAR COLUNAS ---
            colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
            colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
            
            // Usa os métodos auxiliares (toString) que criaste
            colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getC().getNome()));
            colPagamento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getF().getFormaPagamento()));
            colFixa.setCellValueFactory(cellData -> {
                boolean isFixa = cellData.getValue().getFixas();
                return new SimpleStringProperty(isFixa ? "Sim" : "Não");
            });
            
            colValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));

            // --- 2. FORMATAÇÃO E COR (Vermelho) ---
            colValor.setCellFactory(column -> new TableCell<DespesasPoo, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(String.format("%.2f €", item));
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                    }
                }
            });

            // --- 3. CARREGAR DADOS INICIAIS ---
            carregarDados();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- NOVO MÉTODO: RECARREGA A TABELA E O TOTAL ---
    private void carregarDados() {
    	tabelaDespesas.getItems().clear(); 
        
        if (financas == null) {
            System.out.println("Erro: Não há finanças ligadas à tabela.");
            return;
        }

        ObservableList<DespesasPoo> listaVisual = FXCollections.observableArrayList();
        
  
        listaVisual.addAll(financas.despesas); 
        
        tabelaDespesas.setItems(listaVisual);
        
        if (lblTotalDespesas != null) {
            // Opção A: Se já criaste o método getTotalReceitas() no FinancasPoo (Recomendado)
            double total = financas.getTotalDespesas();

            /* // Opção B: Se ainda não tens o método lá, calculas aqui:
            double total = 0;
            for (Receita r : financas.receitas) {
                total += r.getValor();
            }
            */

            // Atualiza o texto do Label
            lblTotalDespesas.setText(String.format("%.2f €", total));
        }
    }

    @FXML
    void onNovaDespesaClick() {
        abrirFormulario("Despesa");
        
        carregarDados();
    }

    @FXML
    void onEditarClick() {
        editarGenerico(tabelaDespesas, "Despesa");
      
        carregarDados();
    }

    @FXML
    void onEliminarClick() {
        // Podes usar o método genérico que criámos no sceneController
        eliminarGenerico(tabelaDespesas);
        
        // Recalcula o total e atualiza a tabela
        carregarDados();
    }
}