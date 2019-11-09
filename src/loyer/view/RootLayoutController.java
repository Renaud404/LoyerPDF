package loyer.view;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import loyer.controller.MainAppJX;
import java.io.File;
import java.io.FileNotFoundException;

public class RootLayoutController {

    // Reference to the main application
    private MainAppJX mainAppJX;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainAppJX mainApp) {
        this.mainAppJX = mainApp;
    }

    /**
     * Modifier le proprietaire.
     */
    @FXML
    private void handleConfigProprietaire() {
    	mainAppJX.showEditProprietaire();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainAppJX.getPrimaryStage());

        if (file != null) {
        	try {
				mainAppJX.gestionSauvegarde.importAllLoc(file.getAbsolutePath());
				mainAppJX.gestionSauvegarde.saveAllLoc(mainAppJX.getlisteLocation());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			};
        }
    }


    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainAppJX.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".txt")) {
                file = new File(file.getPath() + ".txt");
            }
           mainAppJX.gestionSauvegarde.exportAllLoc(mainAppJX.getlisteLocation(), file.getPath());
        }
    }

}
