package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	public static Stage currentStage(ActionEvent event) { //Fun��o para identificar o stage atual com base no evento event do but�o, Action	Click
		return (Stage) ((Node) event.getSource()).getScene().getWindow();

	}
}
