package projetoPOO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

// AQUI ESTÁ O TRUQUE: "extends sceneController"
// Isto permite que esta página use a navegação que tu já criaste!
public class RelatoriosController extends sceneController implements Initializable {

	// --- 1. Vinculação com o FXML (Atenção aos fx:id) ---

    // Cartões Superiores
    @FXML private Label lblTaxaPoupanca;
    @FXML private Label lblMediaGastos;

    // Filtros de Data
    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFim;
    @FXML private Button btnGerarPDF;

    // Gráficos
    // Nota: <String, Number> significa Eixo X = Texto (Meses), Eixo Y = Números (Dinheiro)
    @FXML private LineChart<String, Number> lineChartSaldo;
    @FXML private PieChart pieChartDespesas;
    @FXML private StackedBarChart<String, Number> barChartReceitasDespesas;

    // --- 2. Método de Inicialização (Corre ao abrir a janela) ---
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Carregar dados de exemplo assim que a tela abre
        carregarDadosFicticios();
    }

    // --- 3. Métodos Auxiliares para preencher os gráficos ---

    private void carregarDadosFicticios() {
        // A. Preencher os Cartões
        lblMediaGastos.setText("45,00 €");

        // B. Preencher Gráfico de Linha (Evolução do Saldo)
        XYChart.Series<String, Number> seriesSaldo = new XYChart.Series<>();
        seriesSaldo.setName("Saldo Acumulado");
        seriesSaldo.getData().add(new XYChart.Data<>("Jan", 1000));
        seriesSaldo.getData().add(new XYChart.Data<>("Fev", 1500));
        seriesSaldo.getData().add(new XYChart.Data<>("Mar", 1300));
        seriesSaldo.getData().add(new XYChart.Data<>("Abr", 2000));
        seriesSaldo.getData().add(new XYChart.Data<>("Mai", 2500));
        seriesSaldo.getData().add(new XYChart.Data<>("Jun", 2800));
        
        lineChartSaldo.getData().add(seriesSaldo);

        // C. Preencher Gráfico de Pizza (Despesas por Categoria)
        ObservableList<PieChart.Data> dadosPizza = FXCollections.observableArrayList(
                new PieChart.Data("Alimentação", 400),
                new PieChart.Data("Transporte", 150),
                new PieChart.Data("Lazer", 200),
                new PieChart.Data("Habitação", 600)
        );
        pieChartDespesas.setData(dadosPizza);
        pieChartDespesas.setTitle("Categorias"); // Opcional

        // D. Preencher Gráfico de Barras Empilhadas (Receitas vs Despesas)
        XYChart.Series<String, Number> seriesReceitas = new XYChart.Series<>();
        seriesReceitas.setName("Receitas");
        seriesReceitas.getData().add(new XYChart.Data<>("2024", 25000));
        
        XYChart.Series<String, Number> seriesDespesas = new XYChart.Series<>();
        seriesDespesas.setName("Despesas");
        seriesDespesas.getData().add(new XYChart.Data<>("2024", 18000));

        barChartReceitasDespesas.getData().addAll(seriesReceitas, seriesDespesas);
    }

    // --- 4. Ação do Botão (Exemplo) ---
    @FXML
    public void onGerarPDF() {
        System.out.println("Botão clicado! Aqui vamos gerar o PDF...");
        // Futuramente: logica de exportação para iText ou JasperReports
    }
}