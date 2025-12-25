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

    public void switchToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToReceitas(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Receitas.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToDespesas(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Despesas.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToRelatorios(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Relatorios.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToSobre(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Sobre.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

   
    // Todas as classes filhas (Home, Receitas, Despesas) podem usar isto!
    protected void abrirFormulario(String tipo) {
        try {
            // 1. Carregar o FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario.fxml"));
            Parent root = loader.load();

            // 2. Configurar o controlador
            FormularioController controller = loader.getController();
            controller.setTipo(tipo); 

            // 3. Criar e mostrar a janela
            Stage stage = new Stage();
            stage.setTitle("Adicionar " + tipo);
            stage.setScene(new Scene(root));
            
            // Bloqueia a janela de trás enquanto esta estiver aberta
            stage.initModality(Modality.APPLICATION_MODAL); 
            
            stage.showAndWait();

            // Aqui podes adicionar lógica futura para atualizar as tabelas
            System.out.println("Janela fechada.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir formulário: " + e.getMessage());
        }
    }
    
 // --- MÉTODO GENÉRICO: ELIMINAR ---
    // A filha só tem de passar a sua tabela (tabelaReceitas ou tabelaDespesas)
    protected void eliminarGenerico(TableView<Movimento> tabela) {
        Movimento selecionado = tabela.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma linha para eliminar.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText(null);
        alert.setContentText("Tem a certeza que quer eliminar: " + selecionado.getDescricao() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tabela.getItems().remove(selecionado);
            System.out.println("Eliminado: " + selecionado.getDescricao());
        }
    }

    // --- MÉTODO GENÉRICO: EDITAR ---
    protected void editarGenerico(TableView<Movimento> tabela, String tipo) {
        Movimento selecionado = tabela.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma linha para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario.fxml"));
            Parent root = loader.load();

            FormularioController controller = loader.getController();
            controller.setTipo(tipo); // Configura se é Receita/Despesa
            controller.setMovimento(selecionado); // <--- PREENCHE OS DADOS!

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Atualiza a tabela visualmente caso tenhas mudado algo
            tabela.refresh(); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para não repetires código de alertas
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}