package loyer.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import loyer.model.Location;
import util.DateUtil;

/**
 * Dialog to edit location
 *
 *
 */
public class LocationEditDialogController {

    @FXML
    private TextField nomLocation;
    @FXML
    private TextField nomRue;
    @FXML
    private TextField noRue;
    @FXML
    private TextField codePostal;
    @FXML
    private TextField ville;
    @FXML
    private TextField nomLocataire;
    @FXML
    private TextField prenomLocataire;
    @FXML
    private TextField dateDebut;
    @FXML
    private TextField loyer;
    @FXML
    private TextField charges;

    private Stage dialogStage;
    private Location location;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
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


    public void setLocation(Location location) {
        this.location = location;
        nomLocation.setText(location.getLeLogement().getNomLogement());
        nomRue.setText(location.getLeLogement().getLadresse().getNomRue());
        noRue.setText(Integer.toString(location.getLeLogement().getLadresse().getNoRue()));
        codePostal.setText(location.getLeLogement().getLadresse().getCodePostal());
        ville.setText(location.getLeLogement().getLadresse().getVille());
        nomLocataire.setText(location.getLocataire().getNom());
        prenomLocataire.setText(location.getLocataire().getPrenom());
        loyer.setText(Float.toString(location.getMontantLoyer()));
        charges.setText(Float.toString(location.getMontantCharges()));
        dateDebut.setText(DateUtil.format(location.getDateDebut()));
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {

        	location.getLeLogement().setNomLogement(nomLocation.getText());
        	location.getLeLogement().getLadresse().setNomRue(nomRue.getText());
        	location.getLeLogement().getLadresse().setNoRue(Integer.parseInt(noRue.getText()));
        	location.getLeLogement().getLadresse().setCodePostal(codePostal.getText());
        	location.getLeLogement().getLadresse().setVille(ville.getText());
        	location.getLocataire().setNom(nomLocataire.getText());
        	location.getLocataire().setPrenom(prenomLocataire.getText());
        	location.setMontantLoyer(Float.parseFloat(loyer.getText()));
        	location.setMontantCharges(Float.parseFloat(charges.getText()));
        	location.setDateDebut(DateUtil.parse(dateDebut.getText()));

        	//System.out.println(location.getlisteQuittance())
        	location.GenererQuittance();

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        //String errorMessage = "";

        /*if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }*/
        return true;
    }





}
