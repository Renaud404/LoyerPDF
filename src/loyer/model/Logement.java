package loyer.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Logement {

	private StringProperty nomLogement;
	private Adresse ladresse;


	public Logement(String nomlogement, Adresse uneadresse){
		this.setLadresse(uneadresse);
		this.nomLogement = new SimpleStringProperty(nomlogement);
	}

	public Adresse getLadresse() {
		return ladresse;
	}

	public void setLadresse(Adresse ladresse) {
		this.ladresse = ladresse;
	}

	public String getNomLogement() {
		return nomLogement.get();
	}

	public StringProperty getNomLogementProperty() {
		return nomLogement;
	}

	public void setNomLogement(String nomLogement) {
		this.nomLogement.set(nomLogement);
	}



}
