package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import entities.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainController implements Initializable {
	
SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
	@FXML
	private Button btOk;
	
	@FXML
	private void onBtOkAction() throws ParseException {
		try {
			
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader (new FileReader("task.csv"));
			String line;
			while (br.readLine() != null) {
				line = (br.readLine());
				String[] data = (line.split(","));
				 
					String ticketNum = data[0].toString().replace("\"", "");
					String assignedTo = data[1].toString().replace("\"", "");
					String assignment_group = data[2].toString().replace("\"", "");
					String status = data[3].toString().replace("\"", "");
					Date created = sdf.parse(data[4].toString().replace("\"", ""));
					Date updated = sdf.parse(data[5].toString().replace("\"", ""));
					
					Ticket ticket = new Ticket (ticketNum, assignedTo, assignment_group, status, created, updated);
					System.out.println(ticket);
					
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
