package projetoPOO;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell; // Importante para as cores
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HomeController extends sceneController implements Initializable {

    // --- LIGAÇÕES COM O SCENE BUILDER ---
    @FXML private Label lblSaldo;
    @FXML private Label lblReceitas;
    @FXML private Label lblDespesas;

    @FXML private TableView<Movimento> tabelaMovimentos;

    @FXML private TableColumn<Movimento, LocalDate> colData;
    @FXML private TableColumn<Movimento, String> colDescricao;
    @FXML private TableColumn<Movimento, String> colCategoria;
    @FXML private TableColumn<Movimento, String> colPagamento;
    @FXML private TableColumn<Movimento, Double> colValor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("A iniciar o Dashboard...");
        
        // --- AQUI ESTÁ A ALTERAÇÃO: Voltamos aos valores fixos ---
        atualizarValoresFicticios(); 

        try {
            // --- 1. DEFINIR O QUE APARECE (O conteúdo) ---
            colData.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getData()));
            colDescricao.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescricao()));
            colCategoria.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria()));
            colPagamento.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFormaPagamento()));
            colValor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getValor()));

            // --- 2. DEFINIR A COR E FORMATO (A aparência) ---
            // Mantive isto porque deixa a tabela bonita (Verde/Vermelho)
            colValor.setCellFactory(column -> new TableCell<Movimento, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); 
                    } else {
                        // Formata para ter "€" e 2 casas decimais
                        setText(String.format("%.2f €", item));

                        // Descobre a linha inteira para saber se é Receita ou Despesa
                        Movimento movimentoAtual = getTableRow().getItem();

                        if (movimentoAtual != null) {
                            if (movimentoAtual.getTipo().equals("Receita")) {
                                // VERDE e Alinhado à direita
                                setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                            } else {
                                // VERMELHO e Alinhado à direita
                                setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                            }
                        }
                    }
                }
            });

            // Carregar os dados na tabela
            tabelaMovimentos.setItems(Dados.getListaGlobal());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODO COM VALORES FICTÍCIOS (Estáticos) ---
    private void atualizarValoresFicticios() {
        lblSaldo.setText("1.500,00 €");
        lblReceitas.setText("2.000,00 €");
        lblDespesas.setText("500,00 €");
        
        // Se quiseres podes forçar a cor branca aqui para garantir
        lblSaldo.setStyle("-fx-text-fill: white;"); 
    }

    @FXML
    void onNovaReceitaClick() {
        abrirFormulario("Receita");
    }

    @FXML
    void onNovaDespesaClick() {
        abrirFormulario("Despesa");
    }
}