package loyer.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Adresse {

	private StringProperty nomRue;
	private IntegerProperty noRue;
	private StringProperty codePostal;
	private StringProperty ville;

	public Adresse(String nomRue, int noRue, String codePostal, String Ville){
		this.nomRue = new SimpleStringProperty(nomRue);
		this.noRue = new SimpleIntegerProperty(noRue);
		this.codePostal = new SimpleStringProperty(codePostal);
		this.ville = new SimpleStringProperty(Ville);
	}

	public StringProperty getVilleProperty() {
		return ville;
	}

	public String adresseComplete() {
		String adresseComplete = "";

		adresseComplete = this.noRue.get() + " " + this.nomRue.get() + " " + this.codePostal.get() + " " + this.ville.get() ;

		return adresseComplete;
	}

	public String getVille() {
		return ville.get();
	}

	public void setVille(String ville) {
		this.ville.set(ville);
	}

	public StringProperty getCodePostalProperty() {
		return codePostal;
	}

	public String getCodePostal() {
		return codePostal.get();
	}

	public void setCodePostal(String codePostal) {
		this.codePostal.set(codePostal);
	}

	public int getNoRue() {
		return noRue.get();
	}

	public IntegerProperty getNoRueProperty() {
		return noRue;
	}

	public void setNoRue(Integer noRue) {
		this.noRue.set(noRue);
	}

	public String getNomRue() {
		return nomRue.get();
	}

	public StringProperty getNomRueProperty() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue.set(nomRue);
	}

}
