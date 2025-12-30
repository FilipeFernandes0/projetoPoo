package projetoPOO;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ReceitasController extends sceneController implements Initializable {

    @FXML private Label lblTotalReceitas; 
    @FXML private TableView<Receita> tabelaReceitas; 

    // --- Colunas ---
    @FXML private TableColumn<Receita, LocalDate> colData;
    @FXML private TableColumn<Receita, String> colDescricao;
    @FXML private TableColumn<Receita, String> colTipoReceita;
    @FXML private TableColumn<Receita, Double> colValor;

    // --- FILTROS ---
    @FXML private DatePicker dpFiltroData;
    @FXML private TextField txtFiltroPesquisa;
    @FXML private ComboBox<String> cbFiltroTipo; 

    private FilteredList<Receita> listaFiltrada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        configurarFiltros();
        carregarDados();
    }

    private void configurarColunas() {
        colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
        colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        
        // Converte o tipo para String para exibir na tabela
        colTipoReceita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTp().toString()));
        
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
    }

    // --- CORREÇÃO AQUI ---
    private void configurarFiltros() {
        if(cbFiltroTipo != null) {
            cbFiltroTipo.getItems().add("Todos");
            
            // Usa a lista estática 'tpReceita' da tua classe TipoReceita
            // Em vez de .values()
            try {
                 cbFiltroTipo.getItems().addAll(TipoReceita.tpReceita);
            } catch (Exception e) {
                System.out.println("Aviso: Não foi possível carregar os tipos de receita (tpReceita).");
            }
            
            cbFiltroTipo.getSelectionModel().selectFirst();
        }
    }

    public void carregarDados() {
        if (financas == null) return;

        ObservableList<Receita> listaBase = FXCollections.observableArrayList(financas.receitas);
        listaFiltrada = new FilteredList<>(listaBase, p -> true);
        tabelaReceitas.setItems(listaFiltrada);
        
        onFiltrar(); 
        atualizarTotal();
    }

    @FXML
    void onFiltrar() {
        if (listaFiltrada == null) return;

        listaFiltrada.setPredicate(receita -> {
            // 1. Filtro de Data
            if (dpFiltroData.getValue() != null) {
                if (!receita.getData().equals(dpFiltroData.getValue())) return false;
            }

            // 2. Filtro de Texto
            String texto = txtFiltroPesquisa.getText();
            if (texto != null && !texto.isEmpty()) {
                if (!receita.getDescricao().toLowerCase().contains(texto.toLowerCase())) return false;
            }

            // 3. Filtro de Tipo (Corrigido para comparar Strings)
            if (cbFiltroTipo != null) {
                String tipoSelecionado = cbFiltroTipo.getValue();
                if (tipoSelecionado != null && !tipoSelecionado.equals("Todos")) {
                    // Compara o toString() do tipo da receita com o selecionado na combo
                    if (!receita.getTp().toString().equals(tipoSelecionado)) return false;
                }
            }
            return true;
        });
        
        atualizarTotal();
    }

    private void atualizarTotal() {
        double total = 0;
        for (Receita r : tabelaReceitas.getItems()) {
            total += r.getValor();
        }
        lblTotalReceitas.setText(String.format("%.2f €", total));
    }

    @FXML
    void onNovaReceitaClick() {
        abrirFormulario("Receita");
        carregarDados();
    }

    @FXML
    void onEditarClick() {
        editarGenerico(tabelaReceitas, "Receita");
        carregarDados();
    }

    @FXML
    void onEliminarClick() {
        // Usa o método genérico da mãe que já corrigimos
        Receita selecionada = tabelaReceitas.getSelectionModel().getSelectedItem();
        if (eliminarGenerico(selecionada)) {
            carregarDados();
        }
    }
}