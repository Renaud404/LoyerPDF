package loyer.model;

public class Proprietaire extends Personne {

	/* Utilité à définir, juste la pour tester l'héritage
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
