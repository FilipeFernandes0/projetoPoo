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

public class HomeController extends sceneController implements Initializable {

    // --- LIGAÇÕES COM O SCENE BUILDER ---
    @FXML private Label lblSaldo;
    @FXML private Label lblReceitas;
    @FXML private Label lblDespesas;

    // MUDANÇA: Agora usamos Transacao (o pai de todos)
    @FXML private TableView<Transacao> tabelaMovimentos;

    @FXML private TableColumn<Transacao, LocalDate> colData;
    @FXML private TableColumn<Transacao, String> colDescricao;
    @FXML private TableColumn<Transacao, String> colCategoria;
    @FXML private TableColumn<Transacao, String> colPagamento;
    @FXML private TableColumn<Transacao, Double> colValor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("A iniciar o Dashboard...");
        
        atualizarValoresFicticios(); 

        try {
            // --- 1. DEFINIR O QUE APARECE ---
            
            // Data e Descrição são comuns a todos (Transacao)
            colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
            colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
            colValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));

            // --- CATEGORIA (Só existe em Despesas) ---
            colCategoria.setCellValueFactory(cellData -> {
                Transacao t = cellData.getValue();
                if (t instanceof DespesasPoo) {
                    return new SimpleStringProperty(((DespesasPoo) t).getC().getNome());
                } else if (t instanceof Receita) {
                    return new SimpleStringProperty(((Receita) t).getTipo()); // Mostra o Tipo se for Receita
                }
                return new SimpleStringProperty("-");
            });

            // --- PAGAMENTO (Só existe em Despesas) ---
            colPagamento.setCellValueFactory(cellData -> {
                Transacao t = cellData.getValue();
                if (t instanceof DespesasPoo) {
                    return new SimpleStringProperty(((DespesasPoo) t).getF().getFormaPagamento());
                }
                return new SimpleStringProperty(""); // Receitas não têm pagamento, fica vazio
            });


            // --- 2. DEFINIR A COR E FORMATO ---
            colValor.setCellFactory(column -> new TableCell<Transacao, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); 
                    } else {
                        setText(String.format("%.2f €", item));

                        Transacao movimentoAtual = getTableRow().getItem();

                        if (movimentoAtual != null) {
                            if (movimentoAtual instanceof Receita) {
                                // VERDE
                                setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                            } else if (movimentoAtual instanceof DespesasPoo) {
                                // VERMELHO
                                setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                            }
                        }
                    }
                }
            });

            // Carregar os dados
            carregarDashboard();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void carregarDashboard() {
        // 1. Segurança: Se não houver carteira, sai
    	if (financas == null) {
            System.out.println("Erro: Não há finanças ligadas à tabela.");
            return;
        }

        // 2. Limpa a tabela para não duplicar dados
        tabelaMovimentos.getItems().clear();

        ObservableList<Transacao> listaVisual = FXCollections.observableArrayList(financas.transacoes);
        
        // 4. Mete na tabela
        tabelaMovimentos.setItems(listaVisual);
    }

    private void atualizarValoresFicticios() {
    	
    	double totalReceita = financas.getTotalReceitas();
    	
    	double totalDespesa = financas.getTotalDespesas();
    	
    	double saldo = financas.getSaldo();
    	
        lblSaldo.setText(String.format("%.2f €", saldo));
        lblReceitas.setText(String.format("%.2f €", totalReceita));
        lblDespesas.setText(String.format("%.2f €", totalDespesa));
        if (saldo >= 0) {
            // Verde se positivo
            lblSaldo.setStyle("-fx-text-fill: #2ecc71;"); 
        } else {
            // Vermelho se negativo
            lblSaldo.setStyle("-fx-text-fill: #e74c3c;"); 
        } 
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