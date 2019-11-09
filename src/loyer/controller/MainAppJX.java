package loyer.controller;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loyer.model.Location;
import loyer.model.Proprietaire;
import loyer.view.EditProprietaireController;
import loyer.view.LocationEditDialogController;
import loyer.view.MainWindowController;
import loyer.view.RootLayoutController;


public class MainAppJX extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Proprietaire proprietaire;
    ObservableList<Location> listeLocation = FXCollections.observableArrayList();
    public SauvegardeLocation gestionSauvegarde = new SauvegardeLocation();


	@Override
	public void start(Stage primaryStage) {
		
		setLoyerPDFFilePath();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("LoyerPDF");


		// INIT PROPRIETAIRE, CODE A FACTORISER
		String signatureProprio = "";
		Preferences prefs = Preferences.userNodeForPackage(MainAppJX.class);

		String proprioinfo = prefs.get("signatureProprietaire", null);
	    if (proprioinfo != null) {
	    	signatureProprio = proprioinfo;
	    }else { // pas de signature en mémoire on utilise la démo
	    	signatureProprio = System.getProperty("user.dir") + "/src/util/signature-icon.png";
	    }

	    String nomProprio = "";
	    String nomproprioinfo = prefs.get("nomProprietaire", null);
	    if (nomproprioinfo != null) {
	    	nomProprio = nomproprioinfo;
	    }else { // rien mémoire on utilise la démo
	    	nomProprio = "Jean";
	    }

	    String prenomProprio = "";
	    String prenomproprioinfo = prefs.get("prenomProprietaire", null);
	    if (prenomproprioinfo != null) {
	    	prenomProprio = prenomproprioinfo;
	    }else { // rien mémoire on utilise la démo
	    	prenomProprio = "Michel";
	    }

	    String villeProprio = "";
	    String villeproprioinfo = prefs.get("villeProprietaire", null);
	    if (villeproprioinfo != null) {
	    	villeProprio = villeproprioinfo;
	    }else { // rien mémoire on utilise la démo
	    	villeProprio = "Paris";
	    }

		this.setProprietaire(new Proprietaire(nomProprio, prenomProprio, villeProprio, signatureProprio));

        initRootLayout();
        showLocationOverview();
	}

	public MainAppJX (){
	}

    /**
     * Returns the data as an observable list of location.
     * @return
     */
    public ObservableList<Location> getlisteLocation() {
        return listeLocation;
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppJX.class.getResource("/loyer/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getLoyerPDFFilePath();
        if (file != null) {
        	gestionSauvegarde.setMainAppJX(this);
        	try {
				gestionSauvegarde.loadAllLoc();
			} catch (FileNotFoundException e) {
				gestionSauvegarde.saveAllLoc(listeLocation);
			}
        }
    }


    /**
     * Shows the person overview inside the root layout.
     */
    public void showLocationOverview() {
        try {
            // Load MainWindow overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppJX.class.getResource("/loyer/view/MainWindow.fxml"));
            AnchorPane MainWindowOverview = (AnchorPane) loader.load();

            // Set MainWindow overview into the center of root layout.
            rootLayout.setCenter(MainWindowOverview);

            // Give the controller access to the main app.
            MainWindowController controller = loader.getController();
            controller.setMainAppJX(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showEditProprietaire() {
        try {
            // Load MainWindow overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppJX.class.getResource("/loyer/view/EditProprietaire.fxml"));
            AnchorPane EditProprietaire = (AnchorPane) loader.load();

            // Set MainWindow overview into the center of root layout.
            rootLayout.setCenter(EditProprietaire);

            // Give the controller access to the main app.
            EditProprietaireController controller = loader.getController();

            controller.setMainAppJX(this);
            controller.setProprietaire(proprietaire);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }


	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Opens a dialog to edit details for the specified location. If the user
	 * clicks OK, the changes are saved into the provided location object and true
	 * is returned.
	 *
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showLocationEditDialog(Location location) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainAppJX.class.getResource("/loyer/view/LocationEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Modifier une location");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        LocationEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setLocation(location);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public Proprietaire getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Proprietaire proprietaire) {
		this.proprietaire = proprietaire;
	}

	/**
	 * Returns loyer fil preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 *
	 * @return
	 */

	public File getLoyerPDFFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainAppJX.class);
	    String filePath = prefs.get("fichierDesLoc", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 *
	 * @param file the file or null to remove the path
	 */
	public void setLoyerPDFFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainAppJX.class);
	    prefs.put("fichierDesLoc", System.getProperty("user.dir")+"\\Sauvagarde.txt");
	}


}
