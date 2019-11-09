package loyer.model;

public class Proprietaire extends Personne {

	/* Utilit� � d�finir, juste la pour tester l'h�ritage
	 * */

	private String VilleProprietaire;
	private String urlSignature;

	public Proprietaire(String nom, String prenom, String ville, String urlSignature) {
		super(nom, prenom);
		this.VilleProprietaire = ville;
		this.urlSignature = urlSignature;
	}

	public String getVilleProprietaire() {
		return VilleProprietaire;
	}

	public void setVilleProprietaire(String villeProprietaire) {
		VilleProprietaire = villeProprietaire;
	}

	public String getUrlSignature() {
		return urlSignature;
	}

	public void setUrlSignature(String urlSignature) {
		this.urlSignature = urlSignature;
	}

}
