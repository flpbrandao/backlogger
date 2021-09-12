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
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Ticket;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController implements Initializable {

	Boolean buttonClicked, buttonClicked2, validate = false;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat todayDate = new SimpleDateFormat("dd-MM-yyyy");
	List<Ticket> ticketList = new ArrayList<>();
	List<Ticket> finalList = new ArrayList<>();
	List<Ticket> ticketExcludedList = new ArrayList<>();
	Ticket ticket;
	String todayDateString = todayDate.format(new Date());
	String outputPath = ".\\data\\" + todayDateString;

	@FXML
	private Button btOk;

	@FXML
	private Button btExclude;

	@FXML
	private Button btGenerate;

	@FXML
	private TextField txtPathFile;

	@FXML
	private TextField txtExcludeFile;

	@FXML
	private void onBtGenerateAction() {
		setValidation();
		if (validate == true) {
			txtExcludeFile.setDisable(true);
			txtPathFile.setDisable(true);
			btExclude.setDisable(true);
			btOk.setDisable(true);
			// int i = 0;
			// for (Ticket t : ticketList) {

			// System.out.println(i + " - " + t.getNumber());
			// i++;
			// }
			compareLists(ticketList);
		} else {
			txtExcludeFile.setDisable(false);
			txtPathFile.setDisable(false);
		}
	}

	public void compareLists(List<Ticket> ticketList) {
	

		Set<String> s = new HashSet<>();
		int i=0,z=0;
		for (Ticket t : ticketList) {
			
			if (s.add(t.getNumber()) == false) {
				System.out.println("Entry " + i + " - " + t.getNumber() + " present on first list");
			
				
			} else {
				System.out.println("Entry " + i + " - " +  t.getNumber() + " not present. Adding to hashset");
				finalList.add(t);
				
				
			}
			i++;
		}

	

		for (Ticket v : finalList) {

			System.out.println("NOT REPEATED " + z + " - " + v.getNumber());
			z++;
		}

	}

	@FXML
	private void onBtExcludeAction() {
		String inputPath = txtExcludeFile.getText();

		ticketList = readFromFile(inputPath);
		createFile(ticketList, outputPath);
		buttonClicked2 = true;
		txtExcludeFile.setDisable(true);

	}

	@FXML
	private void onBtOkAction() throws ParseException {

		setValidation();
		if (validate == true) {
			String inputPath = txtPathFile.getText();
			ticketList = readFromFile(inputPath);
			createFile(ticketList, outputPath);
			buttonClicked = true;
			txtPathFile.setDisable(true);

		} else {
			txtPathFile.setDisable(false);

		}

	}

	private void createFile(List<Ticket> ticketList, String outputPath) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
			for (Ticket t : ticketList) {
				bw.write(t.getNumber() + "," + t.getAssigned_to() + "," + t.getAssignment_group() + "," + t.getStatus()
						+ "," + t.getCreatedOn() + "," + t.getCreatedOn());
				bw.newLine();
			}

		} catch (IOException e) {

			// System.out.println("Enter file path: ");
			// String sourceFileStr = sc.nextLine();

			// File sourceFile = new File(sourceFileStr);
			// String sourceFolderStr = sourceFile.getParent();

			// boolean success = new File(sourceFolderStr + "\\out").mkdir();
		}
	}

	public List<Ticket> readFromFile(String inputPath) {

		try (BufferedReader br = new BufferedReader(new FileReader(inputPath));) {

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

		} catch (IOException | ParseException e) {

			e.printStackTrace();
		} finally {

		}
		return ticketList;
	}

	@Override
	public void initialize(URL url, ResourceBundle rg) {

	}

	public Boolean setValidation() {
		if ((txtPathFile.getText() == "") || (txtPathFile.getText() == null)) {
			Alerts.showAlert("Missing fields", todayDateString, "Você precisa especificar o arquivo de backlog!",
					AlertType.WARNING);
			return (validate = false);
		} else {
			return (validate = true);
		}

	}

}
