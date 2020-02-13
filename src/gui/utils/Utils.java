package gui.utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static Double tryParseToDouble(String str) {
		try {
			return Double.parseDouble(str);
		}
		catch(NumberFormatException e) {
			return null;
		}
	}
}
