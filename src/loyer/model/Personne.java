package loyer.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Personne {

	private StringProperty nom;
	private StringProperty prenom;

	public Personne(String nom, String prenom) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom =  new SimpleStringProperty(prenom);
    }

	public String getNomComplet() {
		return prenom.get() + " " + nom.get();
	}

	public String getPrenom() {
		return prenom.get();
	}

	public StringProperty getPrenomProperty() {
		return prenom;
	}

	public void setPrenom(StringProperty prenom) {
		this.prenom = prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom.set(prenom);
	}

	public StringProperty getNomProperty() {
		return nom;
	}

	public String getNom() {
		return nom.get();
	}

	public void setNom(String nom) {
		this.nom.set(nom);
	}

}
