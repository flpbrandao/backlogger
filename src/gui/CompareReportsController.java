package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CompareReportsController {

	List<Ticket> tempList = new ArrayList<>();
	List<Ticket> finaltempList = new ArrayList<>();
	List<Ticket> ultimateList = new ArrayList<>();
	FileChooser fc2 = new FileChooser();

	GenerateReports2Controller gn = new GenerateReports2Controller();

	@FXML
	private Button btPreviousBacklog;

	@FXML
	private List<Ticket> onBtPreviousBacklog() {

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
		return ultimateList;
	}

	
}
