package projetoPOO;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HomeController extends sceneController implements Initializable {

    // --- LIGAÇÕES COM O SCENE BUILDER ---
    @FXML private Label lblSaldo;
    @FXML private Label lblReceitas;
    @FXML private Label lblDespesas;

    // --- GRÁFICOS ---
    @FXML private PieChart pieChartHome;
    @FXML private BarChart<String, Number> barChartHome; // Agora é BarChart normal

    // --- TABELA SIMPLIFICADA ---
    @FXML private TableView<Transacao> tabelaMovimentos;
    @FXML private TableColumn<Transacao, LocalDate> colData;
    @FXML private TableColumn<Transacao, String> colDescricao;
    @FXML private TableColumn<Transacao, Double> colValor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("A iniciar o Dashboard...");
        
        atualizarValoresFicticios(); 

        try {
            // --- 1. DEFINIR COLUNAS DA TABELA ---
            colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
            colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
            colValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));

            // Formatação de Cores da Tabela
            colValor.setCellFactory(column -> new TableCell<Transacao, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); 
                    } else {
                        setText(String.format("%.2f €", item));
                        Transacao t = getTableRow().getItem();
                        if (t != null) {
                            if (t instanceof Receita) setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                            else if (t instanceof DespesasPoo) setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                        }
                    }
                }
            });

            // Carregar dados
            carregarDashboard();
            carregarGraficos(); 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void carregarDashboard() {
        if (financas == null) return;
        tabelaMovimentos.getItems().clear();
        ObservableList<Transacao> listaVisual = FXCollections.observableArrayList(financas.transacoes);
        tabelaMovimentos.setItems(listaVisual);
    }

    // --- POPULAR GRÁFICOS ---
    private void carregarGraficos() {
        if (financas == null) return;

        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();

        // 1. PIE CHART
        Map<String, Double> gastosCategoria = new HashMap<>();
        for (DespesasPoo d : financas.despesas) {
            if (d.getData().getMonthValue() == mesAtual && d.getData().getYear() == anoAtual) {
                String cat = d.getC().getNome();
                gastosCategoria.put(cat, gastosCategoria.getOrDefault(cat, 0.0) + d.getValor());
            }
        }
        pieChartHome.getData().clear();
        for (Map.Entry<String, Double> entry : gastosCategoria.entrySet()) {
            pieChartHome.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // 2. BAR CHART (Lado a Lado)
        double totalReceitaMes = 0;
        double totalDespesaMes = 0;

        for (Transacao t : financas.transacoes) {
            if (t.getData().getMonthValue() == mesAtual && t.getData().getYear() == anoAtual) {
                if (t instanceof Receita) totalReceitaMes += t.getValor();
                else if (t instanceof DespesasPoo) totalDespesaMes += t.getValor();
            }
        }

        barChartHome.getData().clear();
        
        // SÉRIE 1: RECEITAS (Verde)
        XYChart.Series<String, Number> seriesR = new XYChart.Series<>();
        seriesR.setName("Receitas");
        // Usamos string vazia ou espaço para agrupar
        seriesR.getData().add(new XYChart.Data<>("", totalReceitaMes));

        // SÉRIE 2: DESPESAS (Vermelho)
        XYChart.Series<String, Number> seriesD = new XYChart.Series<>();
        seriesD.setName("Despesas");
        seriesD.getData().add(new XYChart.Data<>("", totalDespesaMes));
        
        barChartHome.getData().addAll(seriesR, seriesD);
    }

    private void atualizarValoresFicticios() {
        if (financas == null) return;

        double totalReceita = financas.getTotalReceitas();
        double totalDespesa = financas.getTotalDespesas();
        double saldo = financas.getSaldo();
        
        lblSaldo.setText(String.format("%.2f €", saldo));
        lblReceitas.setText(String.format("%.2f €", totalReceita));
        lblDespesas.setText(String.format("%.2f €", totalDespesa));
        
        if (saldo >= 0) lblSaldo.setStyle("-fx-text-fill: #2ecc71;");
        else lblSaldo.setStyle("-fx-text-fill: #ff5252;");
        
        lblReceitas.setStyle("-fx-text-fill: white;");
        lblDespesas.setStyle("-fx-text-fill: white;");
    }

    @FXML
    void onNovaReceitaClick() {
        abrirFormulario("Receita");
        carregarDashboard();
        carregarGraficos();
        atualizarValoresFicticios();
    }

    @FXML
    void onNovaDespesaClick() {
        abrirFormulario("Despesa");
        carregarDashboard();
        carregarGraficos();
        atualizarValoresFicticios();
    }
}