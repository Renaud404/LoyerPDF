package loyer.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Location {

	private Personne locataire;
	private ObjectProperty<LocalDate> dateDebut;
	private Logement leLogement;
	private float montantLoyer;
	private float montantCharges;
	private ObservableList<Quittance> listeQuittance = FXCollections.observableArrayList();

	public Location(Location uneLoc){ // Constructeur pour le premier loyer
		this.locataire = uneLoc.getLocataire();
		this.dateDebut = new SimpleObjectProperty<LocalDate>(uneLoc.getDateDebut());
		this.leLogement = uneLoc.getLeLogement();
		this.montantLoyer = uneLoc.getMontantLoyer();
		this.montantCharges = uneLoc.getMontantCharges();
		// GenererQuittance(); On ne génère pas de quittance pour éviter boucle infinie + c'est inutile car les infos servent juste pour la première quittance
	}
	public Location (Personne locataire, LocalDate dateDebut, Logement leLogement, float montantLoyer, float montantCharges) {
		this.locataire = locataire;
		this.dateDebut = new SimpleObjectProperty<LocalDate>(dateDebut);
		this.leLogement = leLogement;
		this.montantLoyer = montantLoyer;
		this.montantCharges = montantCharges;
		GenererQuittance();
	}

	public void GenererQuittance(){
		listeQuittance.clear();
		LocalDate ladatedudebut = dateDebut.get(); // conversion du objectproperty en localdate classique
		LocalDate maintenant = LocalDate.now();

		if(ladatedudebut != null){

			int nbmois = (int) ChronoUnit.MONTHS.between(ladatedudebut.withDayOfMonth(1), maintenant); // autre méthode pour compter les mois sans le probleme YEAR de period

			for(int i = nbmois ; i >= 0; i--){ // on génère une liste de quittances
				 if (i == 0) { // Pour le premier loyer application d'un prorata
					 float montantLoyerProrata;
					 float montantChargesProrata;

					 Period premierePeriode = Period.between(ladatedudebut, ladatedudebut.with(TemporalAdjusters.lastDayOfMonth()));
					 Period moisComplet = Period.between(ladatedudebut.with(TemporalAdjusters.firstDayOfMonth()), ladatedudebut.with(TemporalAdjusters.lastDayOfMonth()));
					 montantLoyerProrata = Math.round(this.montantLoyer * premierePeriode.getDays() / (moisComplet.getDays() + 1));
					 montantChargesProrata = Math.round(this.montantCharges * premierePeriode.getDays() / (moisComplet.getDays() + 1));

					 Quittance uneQuittance = new Quittance(ladatedudebut.plusMonths(i), this, montantLoyerProrata, montantChargesProrata);

					 listeQuittance.add(uneQuittance);

				 }else {
					 Quittance uneQuittance = new Quittance(ladatedudebut.plusMonths(i), this);
					 listeQuittance.add(uneQuittance);
					 //System.out.println("LoyerCLassique :"+ this.montantLoyer );
				 }
			}
		}
	}

	public Personne getLocataire() {
		return locataire;
	}

	public void setLocataire(Personne locataire) {
		this.locataire = locataire;
	}

	public LocalDate getDateDebut() {
		return dateDebut.get();
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut.set(dateDebut);
	}

	public ObservableList<Quittance> getlisteQuittance() {
		return listeQuittance;
	}

	public void setlisteQuittance(ObservableList<Quittance> lesQuittances) {
		this.listeQuittance = lesQuittances;
	}

	public Logement getLeLogement() {
		return leLogement;
	}

	public void setLeLogement(Logement leLogement) {
		this.leLogement = leLogement;
	}

	public float getMontantLoyer() {
		return montantLoyer;
	}

	public void setMontantLoyer(float montantLoyer) {
		this.montantLoyer = montantLoyer;
	}

	public float getMontantCharges() {
		return montantCharges;
	}

	public void setMontantCharges(float montantCharges) {
		this.montantCharges = montantCharges;
	}

}
