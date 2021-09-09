package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entities.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainController implements Initializable {

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat todayDate = new SimpleDateFormat("dd-MM-yyyy");
	List<Ticket> ticketList = new ArrayList<>();
	Ticket ticket;
	String todayDateString = todayDate.format(new Date());
	String path = ".\\data\\" + todayDateString;
	
	@FXML
	private Button btOk;

	@FXML
	private void onBtOkAction() throws ParseException {
		
		try(BufferedReader br = new BufferedReader(new FileReader("task.csv"));) { //Consertar isso, lendo direto da raiz do programa

			String line;
			
			while ((line = br.readLine()) != null) {
				
				String[] data = (line.split(","));

				String tNum = data[0].toString().replace("\"", "");
				String assignedTo = data[1].toString().replace("\"", "");
				String assignment_group = data[2].toString().replace("\"", "");
				String status = data[3].toString().replace("\"", "");
				Date created = sdf.parse(data[4].toString().replace("\"", ""));
				Date updated = sdf.parse(data[5].toString().replace("\"", ""));

				Ticket ticket = new Ticket(tNum, assignedTo, assignment_group, status, created, updated);
				System.out.println(ticket);
				ticketList.add(ticket);
			

			}

		} catch (IOException e) {
			System.out.println("File not found!");
		}
		finally {
			createFile(ticketList, path);
		}

	}

	private void createFile(List<Ticket> ticketList, String path) {
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (Ticket t : ticketList) {
				bw.write(t.getNumber()+","+ t.getAssigned_to() +"," + t.getAssignment_group() +"," + t.getStatus() + "," + 
			t.getCreatedOn() +"," + t.getCreatedOn());
				bw.newLine();
			}
			
		} catch (IOException e) {
			
		//	System.out.println("Enter file path: ");
		//	String sourceFileStr = sc.nextLine();

		//	File sourceFile = new File(sourceFileStr);
		//	String sourceFolderStr = sourceFile.getParent();
			
		//	boolean success = new File(sourceFolderStr + "\\out").mkdir();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rg) {

	}

}
