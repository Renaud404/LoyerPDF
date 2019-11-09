package loyer.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import util.DateUtil;

public class Quittance {

	private ObjectProperty<LocalDate> moisConcerne;
	private StringProperty moisTexte;
	private Location uneLocation;
	private Button unbouton;

	public Quittance(LocalDate moisConcerne, Location Location) {
		this.moisConcerne = new SimpleObjectProperty<LocalDate>(moisConcerne);;
		this.uneLocation = Location;
		ImageView img = new ImageView("/util/pdf-icon (1).png");
		//this.setImage(img);*/
		Button btn = new Button("", img);
		this.setUnbouton(btn);

	}

	public Quittance(LocalDate moisConcerne, Location Location, float premierLoyer, float premiereCharges) { // Constructeur pour le premier loyer, obligatoire pour la première quittance avec  un montant au prorata
		this.moisConcerne = new SimpleObjectProperty<LocalDate>(moisConcerne);;
		this.uneLocation = new Location(Location);
		this.uneLocation.setMontantCharges(premiereCharges);
		this.uneLocation.setMontantLoyer(premierLoyer);
		ImageView img = new ImageView("/util/pdf-icon (1).png");
		Button btn = new Button("", img);
		this.setUnbouton(btn);
	}

	public LocalDate getMoisConcerne() {
		return moisConcerne.get();
	}

	public String getMoisConcerneToString() {
		return DateUtil.format(moisConcerne.get());
	}

	public int getAnneInt() {
		return moisConcerne.get().getYear();
	}

	public StringProperty moisTexte() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		String ladate = moisConcerne.get().format(formatter);
		ladate = ladate.substring(0, 1).toUpperCase() + ladate.substring(1);
		moisTexte = new SimpleStringProperty(ladate);
		return moisTexte;
	}

	public String moisTexteString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		String ladate = moisConcerne.get().format(formatter);
		ladate = ladate.substring(0, 1).toUpperCase() + ladate.substring(1);
		//moisTexte = new SimpleStringProperty(ladate);
		return ladate;
	}

	public ObjectProperty<LocalDate> getMoisConcerneProperty() {
		return moisConcerne;
	}

	public void setMoisConcerne(LocalDate moisConcerne) {
		this.moisConcerne.set(moisConcerne);
	}

	public float totalLoyer() {
		float totalloyer = 0;
		totalloyer = this.uneLocation.getMontantLoyer() + this.uneLocation.getMontantCharges();
		return totalloyer;
	}

	public Location getUnelocation() {
		return uneLocation;
	}

	public void setUnelocation(Location unelocation) {
		this.uneLocation = unelocation;
	}

	public Button getUnbouton() {
		return unbouton;
	}

	public void setUnbouton(Button unbouton) {
		this.unbouton = unbouton;
	}

}
