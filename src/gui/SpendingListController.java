package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Spending;
import model.service.SpendingService;

public class SpendingListController implements Initializable, DataChangeListener {

	private SpendingService service;

	@FXML
	private TableColumn<Spending, String> tableColumnDate;

	@FXML
	private TableView<Spending> tableViewSpending;

	@FXML
	private TableColumn<Spending, Double> tableColumnWater;
	
	@FXML
	private TableColumn<Spending, Double> tableColumnLight;
	
	@FXML
	private TableColumn<Spending, Double> tableColumnSuperMarket;
	
	@FXML
	private TableColumn<Spending, Double> tableColumnCreditCard;
	
	@FXML
	private TableColumn<Spending, Double> tableColumnOthers;

	@FXML
	private TableColumn<Spending, Spending> tableColumnEdit;

	@FXML
	private TableColumn<Spending, Spending> tableColumnRemove;

	@FXML
	private Button btNew;
	
	@FXML
	private Button btSearch;

	private ObservableList<Spending> obsList;

	@FXML
	public void onbtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Spending obj = new Spending();
		createDialogForm(obj, "/gui/SpendingForm.fxml", parentStage);
	}
	
	@FXML
	public void onbtSearchAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Spending obj = new Spending();
		CreateDialogSearch(obj, "/gui/SpendingSearch.fxml", parentStage);
	}
	
	public void setSpendingService(SpendingService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNode();
	}

	private void initializeNode() {
		tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tableColumnWater.setCellValueFactory(new PropertyValueFactory<>("Water"));
		tableColumnLight.setCellValueFactory(new PropertyValueFactory<>("Light"));
		tableColumnSuperMarket.setCellValueFactory(new PropertyValueFactory<>("SuperMarket"));
		tableColumnCreditCard.setCellValueFactory(new PropertyValueFactory<>("CreditCard"));
		tableColumnOthers.setCellValueFactory(new PropertyValueFactory<>("Others"));

		//tamanho todo da tela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSpending.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Spending> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSpending.setItems(obsList);
		initEditButtons(); // IRÁ CRIAR BOTÃO EDITAVEL EM CADA TABELA, SUPER IMPORTANTE
		initRemoveButtons();// Mesma ideia acima porem remover
	}

	private void createDialogForm(Spending obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			SpendingFormController controller = loader.getController();
			controller.setSpending(obj);
			controller.setSpendingService(new SpendingService());
			controller.subscribeDataChangeListener(this); // acionar o evento onDataChanged. irá att a tabela
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com as contas do mes");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false); // redimensionar
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL); // travar até fechar
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void CreateDialogSearch(Spending obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			SpendingSearchController controller = loader.getController();
			controller.setSpending(obj);
			controller.setSpendingService(new SpendingService());
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com a data a ser procurada");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Spending, Spending>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Spending obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SpendingForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Spending, Spending>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Spending obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Spending obj) {

		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza da deleção?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error to remove object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
