package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainController implements Initializable {
	
	@FXML
	private Button btOk;
	
	@FXML
	private void onBtOkAction() {
		try {
			
			BufferedReader br = new BufferedReader (new FileReader("task.csv"));
			while (br.readLine() != null) {
					System.out.println(br.readLine());
			}
		}
		catch (IOException e) {
			System.out.println("File not found!");
		}
		
	}
	
	

	@Override
	public void initialize(URL url, ResourceBundle rg) {
		
		
	}

	
	
}
