package projetoPOO;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class sceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    // Mantendo a tua estrutura estática
    protected static FinancasPoo financas = new FinancasPoo();

    // --- MÉTODOS DE NAVEGAÇÃO ---
    public void switchToHome(ActionEvent event) throws IOException {
        trocarCena(event, "Home.fxml");
    }

    public void switchToReceitas(ActionEvent event) throws IOException {
        trocarCena(event, "Receitas.fxml");
    }
    
    public void switchToDespesas(ActionEvent event) throws IOException {
        trocarCena(event, "Despesas.fxml");
    }
    
    public void switchToRelatorios(ActionEvent event) throws IOException {
        trocarCena(event, "Relatorios.fxml");
    }
    
    public void switchToSobre(ActionEvent event) throws IOException {
        trocarCena(event, "Sobre.fxml");
    }
    
    // Método auxiliar para não repetir código de troca de cena
    private void trocarCena(ActionEvent event, String fxmlFile) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        // CSS ATIVADO
        try {
            String css = getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.out.println("Aviso: CSS não encontrado.");
        }
        
        stage.setScene(scene);
        stage.show();
    }

    // --- ABRIR FORMULÁRIO ---
    protected void abrirFormulario(String tipo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario.fxml"));
            Parent root = loader.load();

            FormularioController controller = loader.getController();
            controller.setModel(financas);
            controller.setTipo(tipo); 

            Stage stage = new Stage();
            stage.setTitle("Adicionar " + tipo);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); 
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao abrir formulário: " + e.getMessage());
        }
    }
    
    // --- MÉTODO ELIMINAR CORRIGIDO ---
    // Agora recebe o item direto e retorna 'true' se apagou, para a filha atualizar a tabela.
    protected boolean eliminarGenerico(Transacao selecionado) {
        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma linha para eliminar.");
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText(null);
        alert.setContentText("Tem a certeza que quer eliminar: " + selecionado.getDescricao() + "?"); 

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // 1. Remove das listas principais (backend)
            if (selecionado instanceof Receita) {
                financas.receitas.remove(selecionado);
                System.out.println("A apagar Receita...");
            } else if (selecionado instanceof DespesasPoo) {
            	System.out.println("A apagar Despesa...");
                financas.despesas.remove(selecionado);
            }
            
            // Remove da lista geral também
            financas.transacoes.remove(selecionado);
            
            // 2. Guarda no ficheiro (usando o teu GestorFicheiros)
            GestorFicheiros.guardarDados(financas);
            
            // Retorna true para avisar o controlador filho que pode atualizar a tabela
            return true;
        }
        return false;
    }

    // --- MÉTODO EDITAR ---
    protected void editarGenerico(TableView<? extends Transacao> tabela, String tipo) {
        Transacao selecionado = tabela.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma linha para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario.fxml"));
            Parent root = loader.load();

            FormularioController controller = loader.getController();
            controller.setModel(financas);
            controller.setTipo(tipo); 
            
            // Passamos a transação selecionada para o formulário preencher os campos
            controller.setMovimento(selecionado); 

            Stage stage = new Stage();
            stage.setTitle("Editar " + tipo);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // A tabela é atualizada pelo método carregarDados() do filho, se necessário
            tabela.refresh(); 

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao abrir edição: " + e.getMessage());
        }
    }

    protected void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}