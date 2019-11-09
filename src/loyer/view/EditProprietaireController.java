package loyer.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import loyer.controller.MainAppJX;
import loyer.model.Proprietaire;

public class EditProprietaireController {

    @FXML
    private TextField nomProprietaire;

    @FXML
    private TextField prenomProprietaire;

    @FXML
    private TextField villeProprietaire;

    @FXML
    private ImageView imageSignature;

    private Proprietaire proprietaire;

    @SuppressWarnings("unused")
	private Stage dialogStage;
	private MainAppJX mainAppJX;

    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProprietaire(Proprietaire proprio) throws FileNotFoundException {
        this.proprietaire = proprio;
        nomProprietaire.setText(proprio.getNom());
        prenomProprietaire.setText(proprio.getPrenom());
        villeProprietaire.setText(proprio.getVilleProprietaire());
        Image image = new Image(new FileInputStream(proprio.getUrlSignature()));

        imageSignature.setImage(image);
    }

    @FXML
    private void handleOpen() {

    	try {
	        FileChooser fileChooser = new FileChooser();
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
	                "PNG files (*.png)", "*.png");
	        fileChooser.getExtensionFilters().add(extFilter);

	        File file = fileChooser.showOpenDialog(null);

	        if (file != null) {
	        	proprietaire.setUrlSignature(file.getAbsolutePath());

	        	Image image = new Image(new FileInputStream(file));
	            imageSignature.setImage(image);
	            Preferences prefs = Preferences.userNodeForPackage(MainAppJX.class);
	            prefs.put("signatureProprietaire", file.getAbsolutePath());
	        }
	        else {
	        }

	    }
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    private void handleValider() {
    	proprietaire.setNom(nomProprietaire.getText());
    	proprietaire.setPrenom(prenomProprietaire.getText());
    	proprietaire.setVilleProprietaire(villeProprietaire.getText());
    	Preferences prefs = Preferences.userNodeForPackage(MainAppJX.class);
        prefs.put("nomProprietaire", nomProprietaire.getText());
        prefs.put("prenomProprietaire", prenomProprietaire.getText());
        prefs.put("villeProprietaire", villeProprietaire.getText());

    	mainAppJX.showLocationOverview();
    }

	public void setMainAppJX(MainAppJX mainAppJX) {
		this.mainAppJX = mainAppJX;
	}

    @FXML
    private void handleCancel() {
    	mainAppJX.showLocationOverview();
    }

}
