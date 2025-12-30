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

    // --- FILTROS (Novos IDs do FXML) ---
    @FXML private DatePicker dpFiltroData;
    @FXML private TextField txtFiltroPesquisa;
    @FXML private ComboBox<String> cbFiltroCategoria;
    @FXML private ComboBox<String> cbFiltroPagamento;

    // Lista especial para filtragem
    private FilteredList<DespesasPoo> listaFiltrada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Página de Despesas carregada!");
        
        try {
            configurarColunas();
            configurarFiltros();
            carregarDados();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarColunas() {
        colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
        colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getC().getNome()));
        colPagamento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getF().getFormaPagamento()));
        colFixa.setCellValueFactory(cellData -> {
            boolean isFixa = cellData.getValue().getFixas();
            return new SimpleStringProperty(isFixa ? "Sim" : "Não");
        });
        
        colValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));

        colValor.setCellFactory(column -> new TableCell<DespesasPoo, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null); setStyle("");
                } else {
                    setText(String.format("%.2f €", item));
                    setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                }
            }
        });
    }

    private void configurarFiltros() {
        // Preenche as ComboBoxes com as opções, se existirem no FXML
        if(cbFiltroCategoria != null) {
            cbFiltroCategoria.getItems().add("Todas");
            cbFiltroCategoria.getItems().addAll(Categoria.categoria);
            cbFiltroCategoria.getSelectionModel().selectFirst();
        }
        
        if(cbFiltroPagamento != null) {
            cbFiltroPagamento.getItems().add("Todos");
            cbFiltroPagamento.getItems().addAll(FormaPag.fp);
            cbFiltroPagamento.getSelectionModel().selectFirst();
        }
    }

    // --- CARREGAR DADOS COM LISTA FILTRADA ---
    private void carregarDados() {
        if (financas == null || financas.despesas == null) {
            System.out.println("Erro: Não há finanças ligadas à tabela.");
            return;
        }

        // 1. Criar a lista base
        ObservableList<DespesasPoo> listaBase = FXCollections.observableArrayList(financas.despesas);
        
        // 2. Criar a lista filtrada envolvendo a base (inicialmente mostra tudo)
        listaFiltrada = new FilteredList<>(listaBase, p -> true);
        
        // 3. Ligar à tabela
        tabelaDespesas.setItems(listaFiltrada);
        
        // 4. Calcular Total
        atualizarTotal();
    }

    // --- LÓGICA DO BOTÃO FILTRAR ---
    @FXML
    void onFiltrar() {
        if (listaFiltrada == null) return;

        listaFiltrada.setPredicate(despesa -> {
            // 1. Filtro de Data
            if (dpFiltroData.getValue() != null) {
                if (!despesa.getData().equals(dpFiltroData.getValue())) return false;
            }

            // 2. Filtro de Texto (Descrição)
            String texto = txtFiltroPesquisa.getText();
            if (texto != null && !texto.isEmpty()) {
                if (!despesa.getDescricao().toLowerCase().contains(texto.toLowerCase())) return false;
            }

            // 3. Filtro de Categoria
            if (cbFiltroCategoria != null) {
                String cat = cbFiltroCategoria.getValue();
                if (cat != null && !cat.equals("Todas")) {
                    if (!despesa.getC().getNome().equals(cat)) return false;
                }
            }

            // 4. Filtro de Pagamento
            if (cbFiltroPagamento != null) {
                String pag = cbFiltroPagamento.getValue();
                if (pag != null && !pag.equals("Todos")) {
                    if (!despesa.getF().getFormaPagamento().equals(pag)) return false;
                }
            }

            return true; // Se passar tudo, mostra
        });
        
        atualizarTotal();
    }

    // Calcula o total apenas das linhas visíveis na tabela
    private void atualizarTotal() {
        double total = 0;
        // Percorre apenas os itens filtrados
        for (DespesasPoo d : tabelaDespesas.getItems()) {
            total += d.getValor();
        }
        
        if (lblTotalDespesas != null) {
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
    	DespesasPoo selecionada = tabelaDespesas.getSelectionModel().getSelectedItem();
        
        // 2. Chama a mãe para apagar (retorna true se apagou)
        if (eliminarGenerico(selecionada)) {
            // 3. Recarrega a tabela do zero para atualizar a visualização
            carregarDados();
        }
    }
}