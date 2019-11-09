package loyer.view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import loyer.controller.MainAppJX;
import loyer.model.Adresse;
import loyer.model.Location;
import loyer.model.Logement;
import loyer.model.Personne;
import loyer.model.Quittance;
import util.DateUtil;
import util.PDFgenerator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Optional;

public class MainWindowController {

	@FXML
    private TableView<Location> locationTable;
    @FXML
    private TableColumn<Location, String> locationColonne;

    @FXML
    private Label logementLabel;
    @FXML
    private Label locataireLabel;
    @FXML
    private Label debutDuBailLabel;
    @FXML
    private Label loyerLabel;
    @FXML
    private Label chargesLabel;

    @FXML
    private TableView<Quittance> QuittanceTable;

    @FXML
    private TableColumn<Quittance, String> moisColonne;

    @FXML
    private TableColumn<Quittance, Button> pdfColonne;


    // Reference to the main application.
    private MainAppJX mainAppJX;

    /**
     * The constructor.
     * The constructor is called before the initialize() method
     */
    public MainWindowController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the location table with the two columns.
    	locationColonne.setCellValueFactory(cellData -> cellData.getValue().getLeLogement().getNomLogementProperty());

    	// reset location
    	showlocationDetails(null);

        // Listen for selection changes and show the location details when changed.
    	locationTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showlocationDetails(newValue));

    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainAppJX(MainAppJX mainAppJX) {
        this.mainAppJX = mainAppJX;

        // Add observable list data to the table
        locationTable.setItems(mainAppJX.getlisteLocation());
       // QuittanceListView.setItems(mainAppJX.getlisteQuittance());

    }


    /**
     * Fills all text fields to location details .
     * If the specified location is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showlocationDetails(Location location) {
        if (location != null) {
        	//QuittanceTable.getItems().clear();
            // Fill the labels with info from the person object.
        	logementLabel.setText(location.getLeLogement().getLadresse().adresseComplete() );
        	locataireLabel.setText(location.getLocataire().getNomComplet());
        	DecimalFormat decFor = new DecimalFormat("#####.##");
        	loyerLabel.setText(String.valueOf(decFor.format(location.getMontantLoyer())));
        	chargesLabel.setText(String.valueOf(decFor.format(location.getMontantCharges())));
        	debutDuBailLabel.setText(DateUtil.format(location.getDateDebut()));
        	QuittanceTable.setItems(location.getlisteQuittance());
        	moisColonne.setCellValueFactory(cellData -> cellData.getValue().moisTexte());
			pdfColonne.setCellValueFactory(new PropertyValueFactory<Quittance, Button>("unbouton"));


			for(int i = 0; i <location.getlisteQuittance().size() ; i++){
				final int ii = i;
				location.getlisteQuittance().get(i).getUnbouton().setOnAction(click -> {
					//System.out.println(location.getlisteQuittance().get(ii).getMoisConcerne(), ==);
					try {
						PDFgenerator pdfGen = new PDFgenerator();
						pdfGen.genererPDF(location, ii, mainAppJX.getProprietaire());

					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
        } else {
            // location is null, remove all the text.
        	logementLabel.setText("");
        	locataireLabel.setText("");
        	loyerLabel.setText("");
        	chargesLabel.setText("");
        	debutDuBailLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteLocation() {

        int selectedIndex = locationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

        	Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
        	alertConfirm.setTitle("Supprimer une location");
        	alertConfirm.setHeaderText("Etes vous sur de vouloir supprimer " + mainAppJX.getlisteLocation().get(selectedIndex).getLeLogement().getNomLogement() + " ?");
        	Optional<ButtonType> option = alertConfirm.showAndWait();
        	if (option.get() == null) {
    		} else if (option.get() == ButtonType.OK) {
    			locationTable.getItems().remove(selectedIndex);
    			if(locationTable.getItems().isEmpty() )
    			{
    				System.out.println("liste vide");
    				QuittanceTable.getItems().clear();
    			}
    			try{
            		mainAppJX.gestionSauvegarde.saveAllLoc(mainAppJX.getlisteLocation());
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    		} else if (option.get() == ButtonType.CANCEL) {
    		//this.label.setText("Cancelled!");
    		} else {
    		//this.label.setText("-");
    		}

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainAppJX.getPrimaryStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Aucune sélection");
            alert.setContentText("Sélectionnez une location dans la liste.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewLocation() {
		//Personne unproprio = new Personne(null,null);
		Personne unloc = new Personne(null,null);
		Adresse adresse = new Adresse(null,-1,null,null);
    	Logement logementvide = new Logement("",adresse);
        Location tempLocation = new Location(unloc, null, logementvide, 0, 0);
        boolean okClicked = mainAppJX.showLocationEditDialog(tempLocation);
        if (okClicked) {
            mainAppJX.getlisteLocation().add(tempLocation);

        	try{
        		mainAppJX.gestionSauvegarde.saveAllLoc(mainAppJX.getlisteLocation());
			}catch(Exception e){
				e.printStackTrace();
			}
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditLocation() {
        Location selectedLocation = locationTable.getSelectionModel().getSelectedItem();
        if (selectedLocation != null) {
            boolean okClicked = mainAppJX.showLocationEditDialog(selectedLocation);
            if (okClicked) {
            	showlocationDetails(selectedLocation);
            	try{
            		mainAppJX.gestionSauvegarde.saveAllLoc(mainAppJX.getlisteLocation());
    			}catch(Exception e){
    				e.printStackTrace();
    			}
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainAppJX.getPrimaryStage());
            alert.setTitle("Pas de sélection");
            alert.setHeaderText("Sélectionnez une location");
            alert.setContentText("Veuillez cliquer sur une location dans la liste pour la modifier.");
            alert.showAndWait();
        }
    }

}
