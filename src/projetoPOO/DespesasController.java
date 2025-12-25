package projetoPOO;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell; // Importante para pintar a célula
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DespesasController extends sceneController implements Initializable {

    @FXML private Label lblTotalDespesas;
    @FXML private TableView<Movimento> tabelaDespesas;

    // --- LIGAÇÕES DAS COLUNAS ---
    @FXML private TableColumn<Movimento, LocalDate> colData;
    @FXML private TableColumn<Movimento, String> colDescricao;
    @FXML private TableColumn<Movimento, String> colCategoria;
    @FXML private TableColumn<Movimento, String> colPagamento;
    @FXML private TableColumn<Movimento, Double> colValor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Página de Despesas carregada!");
        
        try {
            // --- 1. CONFIGURAR O QUE APARECE (Modo Seguro - Lambdas) ---
            colData.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getData()));
            colDescricao.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescricao()));
            colCategoria.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria()));
            colPagamento.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFormaPagamento()));
            colValor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getValor()));

            // --- 2. FORMATAÇÃO E COR (Vermelho) ---
            colValor.setCellFactory(column -> new TableCell<Movimento, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Formata com € e 2 casas decimais
                        setText(String.format("%.2f €", item));
                        // Pinta de VERMELHO (#e74c3c), Negrito e Alinhado à Direita
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                    }
                }
            });

            // --- 3. FILTRAR DADOS (Apenas Despesas) ---
            FilteredList<Movimento> listaFiltrada = new FilteredList<>(Dados.getListaGlobal(), m -> m.getTipo().equals("Despesa"));
            tabelaDespesas.setItems(listaFiltrada);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onNovaDespesaClick() {
        abrirFormulario("Despesa");
    }

    @FXML
    void onEditarClick() {
        editarGenerico(tabelaDespesas, "Despesa");
    }

    @FXML
    void onEliminarClick() {
        Movimento selecionado = tabelaDespesas.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atenção");
            alerta.setContentText("Selecione uma despesa para eliminar.");
            alerta.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Despesa");
        alert.setContentText("Tem a certeza que quer eliminar: " + selecionado.getDescricao() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Dados.remover(selecionado); 
        }
    }
}