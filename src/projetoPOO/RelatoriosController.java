package projetoPOO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RelatoriosController extends sceneController implements Initializable {

    // --- FILTROS ---
    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFim;
    // Botão removido daqui

    // --- CARTÕES (KPIs) ---
    @FXML private Label lblTaxaPoupanca; 
    @FXML private Label lblMediaGastos;

    // --- GRÁFICOS ---
    @FXML private LineChart<String, Number> lineChartSaldo;
    @FXML private PieChart pieChartDespesas;
    @FXML private StackedBarChart<String, Number> barChartReceitasDespesas;

    // --- TABELA ---
    @FXML private TableView<Transacao> tabelaMovimentos;
    @FXML private TableColumn<Transacao, LocalDate> colData;
    @FXML private TableColumn<Transacao, String> colDescricao;
    @FXML private TableColumn<Transacao, String> colCategoria;
    @FXML private TableColumn<Transacao, String> colPagamento;
    @FXML private TableColumn<Transacao, Double> colValor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabela();

        // Datas iniciais: Do dia 1 do mês atual até hoje
        dpInicio.setValue(LocalDate.now().withDayOfMonth(1));
        dpFim.setValue(LocalDate.now());

        // Atualizar automaticamente ao mudar datas
        dpInicio.valueProperty().addListener((obs, oldVal, newVal) -> atualizarRelatorios());
        dpFim.valueProperty().addListener((obs, oldVal, newVal) -> atualizarRelatorios());

        // Carregar a primeira vez
        atualizarRelatorios();
    }

    private void configurarTabela() {
        colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
        colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        colValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));

        colCategoria.setCellValueFactory(cellData -> {
            Transacao t = cellData.getValue();
            if (t instanceof DespesasPoo) return new SimpleStringProperty(((DespesasPoo) t).getC().getNome());
            if (t instanceof Receita) return new SimpleStringProperty(((Receita) t).getTp().toString());
            return new SimpleStringProperty("-");
        });

        colPagamento.setCellValueFactory(cellData -> {
            Transacao t = cellData.getValue();
            if (t instanceof DespesasPoo) return new SimpleStringProperty(((DespesasPoo) t).getF().getFormaPagamento());
            return new SimpleStringProperty("");
        });

        colValor.setCellFactory(column -> new TableCell<Transacao, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); } 
                else {
                    setText(String.format("%.2f €", item));
                    Transacao t = getTableRow().getItem();
                    if (t instanceof Receita) setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                    else setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                }
            }
        });
    }

    private void atualizarRelatorios() {
        if (financas == null) return;

        LocalDate inicio = dpInicio.getValue();
        LocalDate fim = dpFim.getValue();

        if (inicio == null || fim == null || inicio.isAfter(fim)) return;

        ObservableList<Transacao> listaFiltrada = FXCollections.observableArrayList();
        
        double totalReceitas = 0;
        double totalDespesas = 0;
        Map<String, Double> gastosPorCategoria = new HashMap<>();

        // 1. Filtrar e Calcular Totais
        for (Transacao t : financas.transacoes) {
            if (!t.getData().isBefore(inicio) && !t.getData().isAfter(fim)) {
                listaFiltrada.add(t);

                if (t instanceof Receita) {
                    totalReceitas += t.getValor();
                } else if (t instanceof DespesasPoo) {
                    totalDespesas += t.getValor();
                    String cat = ((DespesasPoo) t).getC().getNome();
                    gastosPorCategoria.put(cat, gastosPorCategoria.getOrDefault(cat, 0.0) + t.getValor());
                }
            }
        }

        // 2. Atualizar Tabela
        tabelaMovimentos.setItems(listaFiltrada);

        // 3. Atualizar Cartões (Médias)
        long dias = ChronoUnit.DAYS.between(inicio, fim) + 1;
        if (dias < 1) dias = 1;

        double mediaReceitas = totalReceitas / dias;
        double mediaDespesas = totalDespesas / dias;

        lblTaxaPoupanca.setText(String.format("%.2f €", mediaReceitas));
        lblMediaGastos.setText(String.format("%.2f €", mediaDespesas));

        // 4. Atualizar Gráficos
        atualizarGraficoPizza(gastosPorCategoria);
        atualizarGraficoBarras(totalReceitas, totalDespesas);
        atualizarGraficoLinha(listaFiltrada);
    }

    private void atualizarGraficoPizza(Map<String, Double> dados) {
        pieChartDespesas.getData().clear();
        for (Map.Entry<String, Double> entry : dados.entrySet()) {
            pieChartDespesas.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void atualizarGraficoBarras(double rec, double desp) {
        barChartReceitasDespesas.getData().clear();
        
        XYChart.Series<String, Number> seriesR = new XYChart.Series<>();
        seriesR.setName("Receitas");
        seriesR.getData().add(new XYChart.Data<>("Período Atual", rec));

        XYChart.Series<String, Number> seriesD = new XYChart.Series<>();
        seriesD.setName("Despesas");
        seriesD.getData().add(new XYChart.Data<>("Período Atual", desp));

        barChartReceitasDespesas.getData().addAll(seriesR, seriesD);
    }

    private void atualizarGraficoLinha(ObservableList<Transacao> lista) {
        lineChartSaldo.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Movimento Acumulado");

        lista.sort(Comparator.comparing(Transacao::getData));

        double saldoAcumulado = 0;
        
        for (Transacao t : lista) {
            if (t instanceof Receita) saldoAcumulado += t.getValor();
            else saldoAcumulado -= t.getValor();
            
            series.getData().add(new XYChart.Data<>(t.getData().toString(), saldoAcumulado));
        }

        lineChartSaldo.getData().add(series);
    }
}