package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController implements Initializable {
	
	@FXML
	private ImageView imgView;

	@FXML
	private MenuItem menuItemMain;

	@FXML
	private MenuItem menuItemCompare;
	
	public void onMenuIntemCompare() {
		loadView("/gui/CompareReports.fxml");
	}

	public void onMenuItemMain() {

		loadView("/gui/GenerateReports2.fxml");

	}

	@FXML
	private MenuItem menuItemAbout;

	public void onMenuItemAbout() {
		createDialogForm("/gui/About.fxml"); // Passa como parametro o FXML da tela About e suas propriedades para ser
		// executado em LoadView
	}

	@Override
	public void initialize(URL uri, ResourceBundle rg) {
		File file = new File("./img/logo.png");
        Image image = new Image(file.toURI().toString());
        imgView.setImage(image);
	}

	public void createDialogForm(String absoluteName) { // Precisa de ajuste para ficar no mesmo Stage
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Report generator");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			// Alerts.showAlert("IO Exception", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}

	public synchronized void loadView(String absoluteName) { // Método recebendo a string com o caminho do FXML
		try {

			// Esses códigos manipulam diretamente a cena principal e o vbox principal da
			// cena em tempo de execução:

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName)); // Inicializa loader de tela
																						// padrão do java fx no caminho
																						// especificado, onde será
																						// criado o Vbox da tela about

			VBox newVBox = loader.load(); // Cria o VBox da tela About, onde serão exibidas as operações feitas a seguir

			Scene mainScene = Main.getMainScene(); // Pega a scene principal criada na classe principal para que seja
													// exibido na mesma tela. Esse método estático precisa ser criado.
													// Se der erro dizendo que a main scene está vazia, é porque não foi
													// atribuída a varíavel mainscene criada à criação da tela.
													// mainScene = new Scene(parent);

			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Pega o vbox principal (já
																					// existente) dentro do
																					// scrollpane principal, dentro da
																					// scene principal. O content é o
																					// conteudo XML dentro do arquivo
																					// MsinView.xml
			Node mainMenu = mainVBox.getChildren().get(0); // Pega o primeiro item dentro do VBox acima (nesse caso o
															// Main Menu que precisa ser preservado).
			mainVBox.getChildren().clear(); // Limpa o VBox principal
			mainVBox.getChildren().add(mainMenu); // Adiciona ao VBOx principal o Main Menu obtido acima, que foi
													// preservado na tela
			mainVBox.getChildren().addAll(newVBox.getChildren()); // Adiciona o conteúdo do new vbox com os itens do
																	// VBOx About e o painel acima

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}
}
