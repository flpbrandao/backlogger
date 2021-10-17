package gui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CompareReportsController implements Initializable{

	List<Ticket> tempList = new ArrayList<>();
	List<Ticket> finaltempList = new ArrayList<>();
	List<Ticket> ultimateList = new ArrayList<>();
	FileChooser fc2 = new FileChooser();
	private ObservableList<Ticket> obsList;

	GenerateReports2Controller gn = new GenerateReports2Controller();
	
	@FXML
	private TableView<Ticket> tableView;
	
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
	private Button btPreviousBacklog;

	@FXML
	private void onBtPreviousBacklog() {

		Set<Ticket> hs = new HashSet<>();

		fc2.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
		fc2.setInitialDirectory(new File(".\\data"));
		List<File> selectedFiles = fc2.showOpenMultipleDialog(null);
		if (selectedFiles != null) {
			for (int i = 0; i < selectedFiles.size(); i++) {
				System.out.println(selectedFiles.get(i));
				tempList = gn.readFromFile(String.valueOf(selectedFiles.get(i)), false);
				for (Ticket t : tempList) {
					if (hs.add(t) == false) {
						if (ultimateList.contains(t) == false) {
							ultimateList.add(t);
							System.out.println("Added to ultimate List - " + t.getNumber());
							;
						}
					}
				}
			}

		}
		obsList = FXCollections.observableArrayList(ultimateList);
		tableView.setItems(obsList);
		
	}


	@Override
	public void initialize(URL url, ResourceBundle rg) {
		initTV();
		
	}

	private void initTV() {
		tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		tableColumnAssignedTo.setCellValueFactory(new PropertyValueFactory<>("assigned_to"));
		tableColumnAssignmentGroup.setCellValueFactory(new PropertyValueFactory<>("assignment_group"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnCreatedOn.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
		tableColumnUpdatedOn.setCellValueFactory(new PropertyValueFactory<>("updatedOn"));
		
	}

	
}
