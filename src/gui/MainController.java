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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable {

	Boolean buttonClicked, buttonClicked2, validate, end = false;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat todayDate = new SimpleDateFormat("dd-MM-yyyy");
	List<Ticket> ticketList = new ArrayList<>();
	List<Ticket> finalList = new ArrayList<>();
	List<Ticket> ticketExcludedList = new ArrayList<>();
	Ticket ticket;
	String todayDateString = todayDate.format(new Date());
	String outputPath = ".\\data\\" + todayDateString;
	private ObservableList<Ticket> obsList;

	

	@FXML
	private TableView<Ticket> tableViewTicket;

	@FXML
	private TableColumn<Ticket, String> tableColumnNumber;

	@FXML
	private TableColumn<Ticket, Date> tableColumnAssignedTo;

	@FXML
	private TableColumn<Ticket, Date> tableColumnAssignmentGroup;

	@FXML
	private TableColumn<Ticket, Date> tableColumnStatus;

	@FXML
	private TableColumn<Ticket, Date> tableColumnCreatedOn;

	@FXML
	private TableColumn<Ticket, Date> tableColumnUpdatedOn;

	@FXML
	private TableColumn<Ticket, Date> tableColumnCompleted;

	@FXML
	private Button btOk;

	@FXML
	private Button btExclude;

	@FXML
	private Button btGenerate;
	
	@FXML
	private Button btCompare;


	@FXML
	private TextField txtPathFile;

	@FXML
	private TextField txtExcludeFile;
	
	@FXML
	private void onBtCompareAction() {
		
	}

	@FXML
	private void onBtGenerateAction() {
		setValidation();
		if (validate == true) {
			txtExcludeFile.setDisable(true);
			txtPathFile.setDisable(true);
			btExclude.setDisable(true);
			btOk.setDisable(true);
			btGenerate.setDisable(true);
			List<Ticket> defList = new ArrayList<>();
			defList = compareLists(ticketList);
			obsList = FXCollections.observableArrayList(defList);
			tableViewTicket.setItems(obsList);
			createFile(defList, outputPath, end = true);
		} else {
			txtExcludeFile.setDisable(false);
			txtPathFile.setDisable(false);
		}
	}

	public List<Ticket> compareLists(List<Ticket> ticketList) {

		Set<String> s = new HashSet<>();
		int z = 0;
		for (Ticket t : ticketList) {
			System.out.println(t + "This ticket is present on the final list");

			if (s.add(t.getNumber()) == false) {
				System.out.println(t.getNumber() + " is duplicated");

			} else {
				System.out.println (t.getNumber() + " not present. Adding to hashset");
				finalList.add(t);

			}
		}

		for (Ticket v : finalList) {

			System.out.println("NOT REPEATED " + z + " - " + v.getNumber());
			z++;

		}
		return finalList;
	}

	@FXML
	private void onBtExcludeAction() {
		if (txtExcludeFile.getText()==null || txtExcludeFile.getText()=="") {
			Alerts.showAlert("Um arquivo precisa ser especificado!", "Se você quer clicar aqui...", "Selecione o arquivo!", AlertType.ERROR);
		}
		else {
		txtExcludeFile.setDisable(true);
		String inputPath = txtExcludeFile.getText();
	
		ticketList = readFromRawTickets(inputPath);
	
		createFile(ticketList, outputPath, end = false);
		buttonClicked2 = true;
		
		}
	}

	@FXML
	private void onBtOkAction() throws ParseException {

		setValidation();
		if (validate == true) {
			txtPathFile.setDisable(true);
			String inputPath = txtPathFile.getText();
			ticketList = readFromFile(inputPath);
			createFile(ticketList, outputPath, end = false);
			buttonClicked = true;
			

		} else {
			txtPathFile.setDisable(false);

		}

	}

	private void createFile(List<Ticket> ticketList, String outputPath, Boolean end) {
		if (end == true) {
			outputPath = outputPath + "-final";
		}

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
				CheckBox completed = new CheckBox();

				Ticket ticket = new Ticket(tNum, assignedTo, assignment_group, status, created, updated, completed);
				System.out.println(ticket + ": Ticket from readFromFile function");
				ticketList.add(ticket);

			}

		} catch (IOException | ParseException e) {

			Alerts.showAlert("Erro", "Arquivo não encontrado ou tipo incorreto :(", "Favor verificar o arquivo", AlertType.ERROR);
			txtPathFile.setDisable(false);
			txtPathFile.setText("");
			btOk.setDisable(false);
		} finally {

		}
		return ticketList;
	}
	
	public List<Ticket> readFromRawTickets(String inputPath) {

		try (BufferedReader br = new BufferedReader(new FileReader(inputPath));) {

			String line;

			while ((line = br.readLine()) != null) {
				
				String[] data = (line.split(","));

				String tNum = data[0].toString().replace("\"", "");
			

				Ticket ticket = new Ticket(tNum);
				System.out.println(ticket + ": Ticket form readFromRawTicket function");
				ticketList.add(ticket);

			}

		} catch (IOException e) {

			Alerts.showAlert("Erro", "Arquivo não encontrado ou tipo incorreto :(", "Favor verificar o arquivo", AlertType.ERROR);
			txtExcludeFile.setDisable(false);
			txtExcludeFile.setText("");
			txtExcludeFile.clear();
			btExclude.setDisable(false);
		} finally {

		}
		return ticketList;
	}

	@Override
	public void initialize(URL url, ResourceBundle rg) {
		initializeTableView();
	}

	private void initializeTableView() {

		tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		tableColumnAssignedTo.setCellValueFactory(new PropertyValueFactory<>("assigned_to"));
		tableColumnAssignmentGroup.setCellValueFactory(new PropertyValueFactory<>("assignment_group"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnCreatedOn.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
		tableColumnUpdatedOn.setCellValueFactory(new PropertyValueFactory<>("updatedOn"));
		tableColumnCompleted.setCellValueFactory(new PropertyValueFactory<>("Completed"));

		// String number, String assigned_to, String assignment_group, String status,
		// Date createdOn,
		// Date updatedOn, CheckBox complete
	}

	public Boolean setValidation() {
		if ((txtPathFile.getText() == "") || (txtPathFile.getText() == null)) {
			Alerts.showAlert("Missing fields", "Rapaz, nada foi selecionado.", "Você precisa especificar o arquivo de backlog!",
					AlertType.WARNING);
			return (validate = false);
		} else {
			return (validate = true);
		}

	}

}
