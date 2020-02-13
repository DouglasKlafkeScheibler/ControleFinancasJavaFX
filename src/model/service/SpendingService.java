package model.service;

import java.util.List;
import java.util.Optional;

import db.DbIntegrityException;
import gui.utils.Alerts;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.dao.DaoFactory;
import model.dao.SpendingDao;
import model.entities.Spending;

public class SpendingService {

	SpendingDao dao = DaoFactory.createSpendingDao();
	
	public List<Spending> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Spending obj) {
		Spending currentObj = findByDate(obj.getDate());

		if(currentObj != null) {
			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Voce tem certeza que deseja "
					+ "subscrever as informações da data?");
			
			if(result.get() == ButtonType.OK) {
				try {					
					obj.setId(currentObj.getId());
					dao.update(obj);
				}
				catch(DbIntegrityException e) {
					Alerts.showAlert("Error to remove object", null, e.getMessage(), AlertType.ERROR);
				}
			}
		}
		else {
			dao.insert(obj);
		}
	}
	
	public void remove(Spending obj) {
		dao.deleteById(obj.getDate());
	}
	
	public Spending findByDate (String date) {
		return dao.findByDate(date);
	}
}
