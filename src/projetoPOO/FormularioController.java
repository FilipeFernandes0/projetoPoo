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
    
    @FXML 
    private ComboBox<String> cbtipoReceita; 
    
    @FXML 
    private CheckBox chkFixas; 
    
    private String tipoMovimento; 
    private Transacao movimentoEmEdicao = null;

    private FinancasPoo financas;

	@FXML
	public void initialize() {
		dpData.setValue(LocalDate.now()); // Data de hoje por defeito

		//cbPagamento.getItems().addAll("Dinheiro", "Cartão", "MBWay", "Transferência");
		//cbPagamento.getSelectionModel().selectFirst();
	}
	
	public void setModel(FinancasPoo f) {
        this.financas = f;
    }

	public void setTipo(String tipo) {
        this.tipoMovimento = tipo;
        lblTitulo.setText("Nova " + tipo);
        
        if (tipo.equalsIgnoreCase("Receita")) {
            configurarModoReceita();
        } else {
            configurarModoDespesa();
        }
    }
	
	private void configurarModoReceita() {
        // A. Esconder coisas de Despesa (com segurança!)
        safeSetVisible(chkFixas, false);
        safeSetVisible(cbPagamento, false);
        safeSetVisible(cbCategoria, false);
        
        // B. Mostrar e Carregar Tipo de Receita
        safeSetVisible(cbtipoReceita, true);
        
        if (cbtipoReceita != null) {
            cbtipoReceita.getItems().clear();
            cbtipoReceita.getItems().addAll(TipoReceita.tpReceita); 
            cbtipoReceita.getSelectionModel().selectFirst();
        }
    }
	
	private void configurarModoDespesa() {
        // A. Mostrar coisas de Despesa
        safeSetVisible(chkFixas, true);
        safeSetVisible(cbPagamento, true);
        safeSetVisible(cbCategoria, true);
        
        // B. Esconder Tipo de Receita
        safeSetVisible(cbtipoReceita, false);
        
        // C. Carregar Pagamentos (Estático)
        if (cbPagamento != null) {
            cbPagamento.getItems().clear();
            // Estou a assumir que tens FormaPag.OPCOES, senão usa o teu .addAll manual
            cbPagamento.getItems().addAll(FormaPag.fp);
            cbPagamento.getSelectionModel().selectFirst();
        }
        
        // D. Carregar Categorias (Estático)
        if (cbCategoria != null) {
            cbCategoria.getItems().clear();
            // Estou a assumir que tens Categoria.OPCOES ou usas a lista manual
            cbCategoria.getItems().addAll(
                Categoria.categoria
            );
            cbCategoria.getSelectionModel().selectFirst();
        }
    }

    // --- 3. O MÉTODO SALVADOR (SEGURANÇA) ---
    // Este método impede que o programa crashe se a checkbox não existir
    private void safeSetVisible(Control c, boolean show) {
        if (c != null) {
            c.setVisible(show);
            c.setManaged(show); // Remove o buraco branco se estiver invisível
        }
    }
        
        
    

	@FXML
		private void onSalvar() {
	        try {
	            // Validações Básicas
	            if (txtValor.getText().isEmpty() || dpData.getValue() == null) {
	                mostrarAlerta("Preencha Data e Valor!");
	                return;
	            }

	            if (financas == null) {
	                mostrarAlerta("Erro: Finanças não ligadas.");
	                return;
	            }
	            
	           

	            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
	            LocalDate data = dpData.getValue();
	            String descricao = txtDescricao.getText();
	            
	            if(valor < 0)
	            {
	            	throw new ValorNegativo("o valor introduzido nao pode ser negativo!");
	            }
	            
	            LocalDate hoje = LocalDate.now();
	            if(data.isAfter(hoje))
	            {
	            	throw new DataFutura("Não se pode registar transações futuras!");
	            }
	            
	            // Remover antigo se estivermos a editar
	            // Nota: Confirma se o teu método 'remover' aceita Transacao ou se tens de fazer Cast
	            if (movimentoEmEdicao != null) {
	                 if (movimentoEmEdicao instanceof Receita) financas.eliminarReceita((Receita) movimentoEmEdicao);
	                 else if (movimentoEmEdicao instanceof DespesasPoo) financas.eliminarDespesa((DespesasPoo) movimentoEmEdicao);
	            }

	            if (tipoMovimento.equalsIgnoreCase("Receita")) {
	                // --- SALVAR RECEITA ---
	                String nomeTipo = cbtipoReceita.getValue();
	                if (nomeTipo == null) { mostrarAlerta("Escolha o tipo!"); return; }

	                Receita novaReceita = new Receita(
	                    data, 
	                    valor, 
	                    descricao, 
	                    tipoMovimento, 
	                    new TipoReceita(nomeTipo) // O teu construtor pede objeto TipoReceita
	                );
	                
	                financas.adicionarReceita(novaReceita);
	                System.out.println("Receita Salva: " + nomeTipo);

	            } else {
	                // --- SALVAR DESPESA ---
	                String nomeCat = cbCategoria.getValue();
	                String nomePag = cbPagamento.getValue();
	                boolean isFixa = (chkFixas != null) && chkFixas.isSelected();

	                DespesasPoo novaDespesa = new DespesasPoo(
	                    data, 
	                    valor, 
	                    descricao, 
	                    tipoMovimento, 
	                    new Categoria(nomeCat), 
	                    new FormaPag(nomePag), 
	                    isFixa
	                );
	                
	                financas.adicionarDespesa(novaDespesa);
	                System.out.println("Despesa Salva: " + nomeCat);
	            }
	            
	            GestorFicheiros.guardarDados(financas);
	            
	            fecharJanela();
	            
	         
	        } catch(DataFutura e) {
	        	mostrarAlerta("Erro de Data: " + e.getMessage());
	        } catch(ValorNegativo e) {
	        	mostrarAlerta("Erro de validação: " + e.getMessage());
	        } catch (NumberFormatException e) {
	            mostrarAlerta("Valor inválido.");
	        } catch (Exception e) {
	            e.printStackTrace();
	            mostrarAlerta("Erro ao salvar: " + e.getMessage());
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
	public void setMovimento(Transacao t) {
        this.movimentoEmEdicao = t;
        txtDescricao.setText(t.getDescricao());
        txtValor.setText(String.valueOf(t.getValor()));
        dpData.setValue(t.getData());
        
        if (t instanceof Receita) {
            setTipo("Receita");
            Receita r = (Receita) t;
            if (cbtipoReceita != null) cbtipoReceita.setValue(r.getTp().getTipo()); // Ajusta getter se necessário
        } else if (t instanceof DespesasPoo) {
            setTipo("Despesa");
            DespesasPoo d = (DespesasPoo) t;
            if(cbCategoria != null) cbCategoria.setValue(d.getC().getNome());
            if(cbPagamento != null) cbPagamento.setValue(d.getF().getFormaPagamento());
            if(chkFixas != null) chkFixas.setSelected(d.getFixas());
        }
    }
}
