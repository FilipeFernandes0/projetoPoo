package projetoPOO;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class FormularioController {

	// --- LIGAÇÕES COM O SCENEBUILDER (fx:id) ---
	@FXML
	private Label lblTitulo;
	@FXML
	private TextField txtDescricao;
	@FXML
	private TextField txtValor;
	@FXML
	private DatePicker dpData;
	@FXML
	private ComboBox<String> cbCategoria;
	@FXML
	private ComboBox<String> cbPagamento;

	private String tipoMovimento;

	// <--- NOVO: Variável para guardar o movimento que estamos a editar (se houver)
	private Movimento movimentoEmEdicao = null;

	@FXML
	public void initialize() {
		dpData.setValue(LocalDate.now()); // Data de hoje por defeito

		cbPagamento.getItems().addAll("Dinheiro", "Cartão", "MBWay", "Transferência");
		cbPagamento.getSelectionModel().selectFirst();
	}

	public void setTipo(String tipo) {
        this.tipoMovimento = tipo;
        lblTitulo.setText("Nova " + tipo);
        
        // Limpa o que lá estava
        cbCategoria.getItems().clear();
        
        // --- LISTA ÚNICA PARA TUDO (Receitas e Despesas) ---
        // Como pediste, carregamos sempre as mesmas opções:
        cbCategoria.getItems().addAll(
            "Geral",
            "Alimentação",
            "Transporte",
            "Salário",        // Serve para Receita
            "Investimentos",  // Serve para os dois
            "Lazer",
            "Saúde",
            "Casa",
            "Educação",
            "Outros"
        );

        /* --- CÓDIGO FUTURO (DO BACKEND) ---
           Quando o teu amigo entregar a classe, apagas a lista de cima 
           e usas este bloco:
           
        try {
            // import myinputs.Categoria;
            myinputs.Categoria cat = new myinputs.Categoria(); 
            cbCategoria.getItems().addAll(cat.a, cat.s, cat.t, "Outros"); 
        } catch (Exception e) {
            System.out.println("Classe Categoria ainda não existe.");
        }
        */
        
        // Seleciona o primeiro por defeito para não ir vazio
        cbCategoria.getSelectionModel().selectFirst();
    }
        
        
    

	@FXML
	private void onSalvar() {
		try {
			String descricao = txtDescricao.getText();

			// Verificação extra para não dar erro se o valor estiver vazio
			if (txtValor.getText().isEmpty()) {
				mostrarAlerta("O valor é obrigatório.");
				return;
			}

			double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
			LocalDate data = dpData.getValue();
			String categoria = cbCategoria.getValue();
			String pagamento = cbPagamento.getValue();

			if (descricao.isEmpty() || categoria == null || pagamento == null) {
				mostrarAlerta("Preencha todos os campos obrigatórios!");
				return;
			}

			// Criar o objeto atualizado
			Movimento novoMovimento = new Movimento(descricao, valor, data, categoria, pagamento, tipoMovimento);

			// --- LÓGICA DE PERSISTÊNCIA (Gravar no Ficheiro) ---

			// 1. Se estivermos a EDITAR, removemos primeiro o antigo da lista
			if (movimentoEmEdicao != null) {
				Dados.remover(movimentoEmEdicao);
			}

			// 2. Adicionamos o novo à lista global (e a classe Dados grava no ficheiro
			// automaticamente)
			Dados.adicionar(novoMovimento);

			System.out.println("Guardado com sucesso: " + novoMovimento);

			fecharJanela();

		} catch (NumberFormatException e) {
			mostrarAlerta("O valor deve ser um número válido (ex: 12.50)");
		} catch (Exception e) {
			mostrarAlerta("Erro ao salvar: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void onCancelar() {
		fecharJanela();
	}

	private void fecharJanela() {
		Stage stage = (Stage) txtDescricao.getScene().getWindow();
		stage.close();
	}

	private void mostrarAlerta(String msg) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Atenção");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	// Método para encher os campos quando clicamos em Editar
	public void setMovimento(Movimento m) {
		this.movimentoEmEdicao = m; // <--- GUARDAR A REFERÊNCIA DO OBJETO ANTIGO

		txtDescricao.setText(m.getDescricao());
		txtValor.setText(String.valueOf(m.getValor()));

		cbCategoria.setValue(m.getCategoria());
		cbPagamento.setValue(m.getFormaPagamento());
		dpData.setValue(m.getData());

		lblTitulo.setText("Editar " + tipoMovimento);
	}
}