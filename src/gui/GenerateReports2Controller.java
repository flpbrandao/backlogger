package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import services.CreateExcelFile;

public class GenerateReports2Controller implements Initializable {

	Boolean buttonClicked, buttonClicked2, validate, end = false;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat todayDate = new SimpleDateFormat("dd-MM-yyyy");
	List<Ticket> ticketList = new ArrayList<>();
	List<Ticket> finalList = new ArrayList<>();
	Ticket ticket;
	String todayDateString = todayDate.format(new Date());
	String outputPath = ".\\data\\" + todayDateString;
	FileChooser fc = new FileChooser();

	private ObservableList<Ticket> obsList;

	@FXML
	private Label lblPath;

	@FXML
	private TextArea txtArea;

	@FXML
	private CheckBox chkExclude;

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
	private Button btFileChooser;

	@FXML
	private Button btExit;

	@FXML
	private Button btClean;

	@FXML
	private Button btExport;

	@FXML
	private Button btGenerate;

	@FXML
	private Button btCompare;

	@FXML
	private TextField txtInitDate;

	@FXML
	private TextField txtCurrentDate;

	@FXML
	private void onBtFileChooserAction() {

		fc.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {

			txtArea.setText(selectedFile.getAbsolutePath());
			String inputPath = selectedFile.getAbsolutePath();
			ticketList = readFromFile(inputPath, false);
			createFile(ticketList, outputPath, end = false);
			buttonClicked = true;
			Alerts.showAlert("Informação", "Leitura feita com sucesso.", inputPath, AlertType.INFORMATION);

		} else {
			Alerts.showAlert("Erro", "Seleção incorreta", "Selecione um arquivo válido!", AlertType.ERROR);
		}

	}

	@FXML
	private void onBtCleanAction() {

		btGenerate.setDisable(false);
		buttonClicked = false;
		buttonClicked2 = false;
		tableViewTicket.setItems(null);
		btExport.setDisable(true);
		chkExclude.setDisable(false);
		chkExclude.setSelected(false);
		txtArea.setText("");
		btFileChooser.setDisable(false);

	}

	@FXML
	private void onchkExcludeAction() {
		String path = ".\\data\\Exclude\\Exclude";

		ticketList = readFromRawTickets(path);

		createFile(ticketList, outputPath, end = false);
		buttonClicked2 = true;
		chkExclude.setDisable(true);
		Alerts.showAlert("Informação", "Arquivos lidos", "Os arquivos a serem excluídos foram lidos.",
				AlertType.INFORMATION);
	}

	@FXML
	private void onBtExitAction() {

	}

	@FXML
	private void onBtExportAction() {
		CreateExcelFile excel = new CreateExcelFile();
		excel.createExcelFile(outputPath, new Date(), finalList);
		onBtCleanAction();
	}

	@FXML
	private void onBtGenerateAction() {

		btGenerate.setDisable(true);
		List<Ticket> defList = new ArrayList<>();
		defList = compareLists(ticketList, false);
		obsList = FXCollections.observableArrayList(defList);
		tableViewTicket.setItems(obsList);
		createFile(defList, outputPath, end = true);
		btExport.setDisable(false);
		chkExclude.setDisable(true);
		btFileChooser.setDisable(true);

	}

	public List<Ticket> compareLists(List<Ticket> ticketList, Boolean comparison) {
		Set<Ticket> s = new HashSet<>();

		for (Ticket t : ticketList) {
			if (s.add(t) == false) {
				s.remove(t);
				System.out.println(t.getNumber() + " is duplicated - removing from HashSet and list");
				if (comparison == true) {
					finalList.add(t);
				}

			}

		}
		if (comparison == false) {
			for (Ticket t : s) {
				finalList.add(t);
			}
		}
		return finalList;

	}

	public void createFile(List<Ticket> ticketList, String outputPath, Boolean end) {
		if (end == true) {
			outputPath = outputPath + "-final-csv-report.csv";
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
			bw.write("BASELINE - Não apagar");
			bw.newLine();
			for (Ticket t : ticketList) {
				bw.write(t.getNumber() + "," + t.getAssigned_to() + "," + t.getAssignment_group() + "," + t.getStatus()
						+ "," + sdf.format(t.getCreatedOn()) + "," + sdf.format(t.getUpdatedOn()));
				bw.newLine();

			}

		} catch (IOException e) {

		}
	}

	public List<Ticket> readFromFile(String inputPath, Boolean compare) {

		try (BufferedReader br = new BufferedReader(new FileReader(inputPath));) {

			String line;
			br.readLine();

			if (compare == false) {

				while ((line = br.readLine()) != null) {
                    try {
					String[] data = (line.split(","));

					String tNum = data[0].toString().replace("\"", "");
					String assignedTo = data[1].toString().replace("\"", "");
					String assignment_group = data[2].toString().replace("\"", "");
					String status = data[3].toString().replace("\"", "");
					Date created = sdf.parse(data[4].toString().replace("\"", ""));
					Date updated = sdf.parse(data[5].toString().replace("\"", ""));

					Ticket ticket = new Ticket(tNum, assignedTo, assignment_group, status, created, updated);
					System.out.println(ticket + ": Ticket from readFromFile function");
					ticketList.add(ticket);
                    }
                    catch (IndexOutOfBoundsException a) {
                        Alerts.showAlert("Erro", "Formato de arquivo incorreto", "Verifique o .CSV", AlertType.ERROR);
					}
                    
				}
			} else {
				while ((line = br.readLine()) != null) {
					String[] data = (line.split(","));
					try {

						String tNum = data[0].toString();
						String assignedTo = data[1].toString();
						String assignment_group = data[2].toString();
						String status = data[3].toString();
						Date created = sdf.parse(data[4].toString());
						Date updated = sdf.parse(data[5].toString());

						Ticket ticket = new Ticket(tNum, assignedTo, assignment_group, status, created, updated);
						System.out.println(ticket + ": Ticket from readFromFile - no replace function");
						ticketList.add(ticket);
					} catch (IndexOutOfBoundsException a) {
                         Alerts.showAlert("Erro", "Formato de arquivo incorreto", "Verifique o .CSV", AlertType.ERROR);
					}
				}

			}

		} catch (IOException | ParseException e) {

			Alerts.showAlert("Erro", "Arquivo não encontrado ou tipo incorreto :(", "Favor verificar o arquivo",
					AlertType.ERROR);
			e.printStackTrace();

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
				Date created = new Date();
				Date updated = new Date();

				Ticket ticket = new Ticket(tNum, created, updated);
				System.out.println(ticket + ": Ticket form readFromRawTicket function");
				ticketList.add(ticket);

			}

		} catch (IOException e) {

			Alerts.showAlert("Erro", "Arquivo não encontrado ou tipo incorreto ", "Favor verificar o arquivo",
					AlertType.ERROR);

		} finally {

		}
		return ticketList;
	}

	@Override
	public void initialize(URL url, ResourceBundle rg) {
		initializeTableView();
		btExport.setDisable(true);
	}

	private void initializeTableView() {

		tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		tableColumnAssignedTo.setCellValueFactory(new PropertyValueFactory<>("assigned_to"));
		tableColumnAssignmentGroup.setCellValueFactory(new PropertyValueFactory<>("assignment_group"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnCreatedOn.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
		tableColumnUpdatedOn.setCellValueFactory(new PropertyValueFactory<>("updatedOn"));

	}

}
