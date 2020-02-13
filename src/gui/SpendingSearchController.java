package gui;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.utils.Alerts;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.entities.Spending;
import model.exceptions.ValidationException;
import model.service.SpendingService;

public class SpendingSearchController implements Initializable {

	@FXML
	private DatePicker dpSearchdate;
	
	@FXML
	private Label lbWater;
	
	@FXML
	private Label lbLight;
	
	@FXML
	private Label lbSupermarket;
	
	@FXML
	private Label lbCreditCard;
	
	@FXML
	private Label lbOthers;
	
	@FXML
	private Label lbError;
	
	@FXML
	private Button btSearch;
	
	@FXML
	private Button btCancel;
	
	private Spending entity;
	
	private SpendingService service;
	
	public void setSpending(Spending entity) {
		this.entity = entity;
	}
	
	public void setSpendingService(SpendingService service) {
		this.service = service;
	}
	
	@FXML
	public void onBtSearchAction(ActionEvent event) {
			if(entity == null) {
				throw new IllegalStateException("Entity was null");
			}
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			
			//caso tenha tentado procurar após errar
			lbError.setText("");
			
			ValidationException exception = new ValidationException("Validation error");
			
			try {
				if(dpSearchdate.getValue() == null) {
					exception.addError("date", "Data nula");
					throw exception;
				}
				else {
					Spending obj = service.findByDate(dpSearchdate.getValue().toString());
					
					if(obj == null) {
						exception.addError("date", "Data inexistente");
						throw exception;
					}
					else {
						entity = service.findByDate(dpSearchdate.getValue().toString());
						setSearchData(entity);	
					}
				}
			}
			catch(ValidationException e) {
				setErrorMessages(e.getErrors());
			}
			catch(DbException e) {
				Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
			}
	}
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	private void setSearchData(Spending obj) {
		lbWater.setText(obj.getWater().toString());
		lbLight.setText(obj.getLight().toString());
		lbSupermarket.setText(obj.getSuperMarket().toString());
		lbCreditCard.setText(obj.getCreditCard().toString());
		lbOthers.setText(obj.getOthers().toString());
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	private void setErrorMessages(Map<String, String> error) {
		Set<String> fields = error.keySet();
		
		if(fields.contains("date")) {
			lbError.setText(error.get("date"));
		}
	}
}
