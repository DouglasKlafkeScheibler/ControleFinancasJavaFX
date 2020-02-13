package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Spending;
import model.exceptions.ValidationException;
import model.service.SpendingService;

public class SpendingFormController implements Initializable{

	private Spending entity;
	
	private SpendingService service;
	
	private List<DataChangeListener> dataChanceListeners = new ArrayList<>(); 
	
	@FXML
	private DatePicker dpDate;
	
	@FXML
	private TextField txtWater;
	
	@FXML
	private TextField txtLight;
	
	@FXML
	private TextField txtSuperMarket;
	
	@FXML
	private TextField txtCreditCard;
	
	@FXML
	private TextField txtOthers;	
	
	@FXML
	private Label lbErrorDate;

	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setSpending(Spending entity) {
		this.entity = entity;
	}
	
	public void setSpendingService(SpendingService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChanceListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
		throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChanceListeners) {
			listener.onDataChanged();
		}
	}
	
	private Spending getFormData() {
		 Spending obj = new Spending();

		 ValidationException exception = new ValidationException("Validation error");
		 
		if (dpDate.getValue() == null) {
			exception.addError("date", "Data inválida");
		}
		else {			
			obj.setDate(dpDate.getValue().toString());
		}
	 
		 fieldNullTest(txtWater.getText());
		 if(fieldEmptyTest(txtWater.getText())) {
			 obj.setWater((double) 0);
		 }		 
		 else {
			 obj.setWater(Utils.tryParseToDouble(txtWater.getText()));			 
		 }
		 
		 
		 fieldNullTest(txtLight.getText());
		 if(fieldEmptyTest(txtLight.getText())) {
			 obj.setLight((double) 0);
		 }
		 else {
			 obj.setLight(Utils.tryParseToDouble(txtLight.getText()));			 
		 }
		 
		 
		 fieldNullTest(txtSuperMarket.getText());
		 if(fieldEmptyTest(txtSuperMarket.getText())) {
			 obj.setSuperMarket((double) 0);
		 }
		 else {
			 obj.setSuperMarket(Utils.tryParseToDouble(txtSuperMarket.getText()));
		 }
		 
		 
		 fieldNullTest(txtCreditCard.getText());
		 if(fieldEmptyTest(txtCreditCard.getText())) {
			 obj.setCreditCard((double) 0);
		 }	 
		 else {
			 obj.setCreditCard(Utils.tryParseToDouble(txtCreditCard.getText()));
		 }
		 
		 
		 fieldNullTest(txtOthers.getText());
		 if(fieldEmptyTest(txtOthers.getText())) {
			 obj.setOthers((double) 0);
		 }
		 else {
			 obj.setOthers(Utils.tryParseToDouble(txtOthers.getText()));
		 }		
		 if(exception.getErrors().size() > 0) {
			 throw exception;
		 }
		 return obj;
	}
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		
		Constraints.setTextFieldDouble(txtWater);
		Constraints.setTextFieldDouble(txtLight);
		Constraints.setTextFieldDouble(txtSuperMarket);
		Constraints.setTextFieldDouble(txtCreditCard);
		Constraints.setTextFieldDouble(txtOthers);
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		if(entity.getDate() != null){
		dpDate.setValue(LocalDate.parse(entity.getDate()));
		}
		
		txtWater.setText(String.valueOf(entity.getWater()));
		if((entity.getLight()) != null) {
			txtLight.setText(String.valueOf(entity.getLight()));
			txtSuperMarket.setText(String.valueOf(entity.getSuperMarket()));			
			txtCreditCard.setText(String.valueOf(entity.getCreditCard()));
			txtOthers.setText(String.valueOf(entity.getOthers()));						
		}
		else {
			txtLight.setText("");
			txtSuperMarket.setText("");
			txtCreditCard.setText("");
			txtOthers.setText("");
		}
	}
	
	private void setErrorMessages(Map<String, String> error) {
		Set<String> fields = error.keySet();
		
		if(fields.contains("date")) {
			lbErrorDate.setText(error.get("date"));
		}
	}
	
	private void fieldNullTest(String field) {
		
		if(field == null) {
			throw new ValidationException("Campo nulo");
		 }
	}
	
	private Boolean fieldEmptyTest(String field) {
	
			if(field.trim().equals("")) {
				return true;
			}
			else {
				return false;
			}
	}
}