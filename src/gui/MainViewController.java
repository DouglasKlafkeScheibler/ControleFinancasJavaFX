package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.service.SpendingService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemRegistration;
	
	@FXML
	private MenuItem menuItemList;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemRegistrationAction() {
		loadView("/gui/SpendingList.fxml", (SpendingListController controller) -> {
			controller.setSpendingService(new SpendingService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
	}

	private synchronized<T> void loadView(String absoluteName, Consumer<T> initializingAction ) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVboX =(VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVboX.getChildren().get(0);
			mainVboX.getChildren().clear();
			mainVboX.getChildren().add(mainMenu);
			mainVboX.getChildren().addAll(newVbox.getChildren());
			
			//ativar função parametro generica
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IO Excepetion", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
