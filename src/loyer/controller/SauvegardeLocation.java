package loyer.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import javafx.collections.ObservableList;
import loyer.model.Adresse;
import loyer.model.Location;
import loyer.model.Logement;
import loyer.model.Personne;
import util.DateUtil;

public class SauvegardeLocation {

	private MainAppJX mainAppJX;

    public SauvegardeLocation() {
    }

    public void setMainAppJX(MainAppJX mainAppJX) {
        this.mainAppJX = mainAppJX;
    }


   public Location CreerLoc(String nomLocation, String noRue, String nomRue, String codePostal, String ville, String nomLocataire, String prenomLocataire, String dateDebut, String loyer, String charges ) {

	   	LocalDate dateFromString = DateUtil.parse(dateDebut);
		LocalDate dateEntree = dateFromString;

		int intRue = Integer.parseInt(noRue);
		Float intLoyer = Float.parseFloat(loyer);
		Float intCharges = Float.parseFloat(charges);

		Adresse adresse = new Adresse(nomRue,intRue,codePostal,ville);

		Logement studioNice = new Logement(nomLocation,adresse);

		Personne locataire = new Personne(nomLocataire,prenomLocataire);

		Location uneLocation = new Location(locataire, dateEntree, studioNice, intLoyer, intCharges);

	   return uneLocation;
   }

   public String saveLocToFile (Location laloc) {

	   String uneLocEnString = "";

	   uneLocEnString += laloc.getLeLogement().getNomLogement();
	   uneLocEnString += "\t";
	   uneLocEnString += laloc.getLeLogement().getLadresse().getNoRue();
	   uneLocEnString += "\t";
	   uneLocEnString += laloc.getLeLogement().getLadresse().getNomRue();
	   uneLocEnString += "\t";
	   uneLocEnString += laloc.getLeLogement().getLadresse().getCodePostal();
	   uneLocEnString += "\t";
	   uneLocEnString += laloc.getLeLogement().getLadresse().getVille();
	   uneLocEnString += "\t";
	   uneLocEnString += laloc.getLocataire().getNom();
	   uneLocEnString += "\t";
	   uneLocEnString += laloc.getLocataire().getPrenom();
	   uneLocEnString += "\t";
	   uneLocEnString += DateUtil.format(laloc.getDateDebut());
	   uneLocEnString += "\t";
	   uneLocEnString += laloc.getMontantLoyer();
	   uneLocEnString += "\t";
	   uneLocEnString += laloc.getMontantCharges();

	   return uneLocEnString;
   }

   public void saveAllLoc(ObservableList<Location> listeLocation){

	   String toutesLesLocs = "";

	   for(Location uneloc : listeLocation){
		   if (listeLocation.indexOf(uneloc) == listeLocation.size() - 1) {
			   toutesLesLocs += saveLocToFile(uneloc);
	        }else {
	        	toutesLesLocs += saveLocToFile(uneloc) + System.lineSeparator();
	        }
	   }
	   writeUsingFileWriter(toutesLesLocs,mainAppJX.getLoyerPDFFilePath().getAbsolutePath());
   }

   public void exportAllLoc(ObservableList<Location> listeLocation, String Pathfile){

	   String toutesLesLocs = "";

	   for(Location uneloc : listeLocation){
		   if (listeLocation.indexOf(uneloc) == listeLocation.size() - 1) {
			   toutesLesLocs += saveLocToFile(uneloc);
	        }else {
	        	toutesLesLocs += saveLocToFile(uneloc) + System.lineSeparator();
	        }
	   }
	   writeUsingFileWriter(toutesLesLocs,Pathfile);
   }

   private static void writeUsingFileWriter(String data, String dest) {
       File file = new File(dest);
       FileWriter fr = null;
       try {
           fr = new FileWriter(file);
           fr.write(data);
       } catch (IOException e) {
           e.printStackTrace();
       }finally{
           //close resources
           try {
               fr.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

   public void loadAllLoc() throws FileNotFoundException {
       mainAppJX.getlisteLocation().clear();

       File inputFile = new File(mainAppJX.getLoyerPDFFilePath().getAbsolutePath());
       //FileReader in = new FileReader(inputFile);

       Scanner scanner = new Scanner(inputFile);

       if(scanner.hasNextLine() == false)
       {
    	   //System.out.println("FichierVide");
    	   buildUneLoc("SuperF1	12	Jacques Offenbach	06000	Nice	Letudiant	Jean	05/09/2019	490.0	60.0");
       }
       while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          buildUneLoc(line);
       }
       scanner.close();

     }

   public void importAllLoc(String PathFileImport) throws FileNotFoundException {
       mainAppJX.getlisteLocation().clear();

       File inputFile = new File(PathFileImport);
       //FileReader in = new FileReader(inputFile);

       Scanner scanner = new Scanner(inputFile);
       while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          buildUneLoc(line);
       }
       scanner.close();

     }

   public void buildUneLoc(String uneLigneLoc){

	   String[] champLoc = uneLigneLoc.split("\t");

	   Adresse adresse = new Adresse(champLoc[2],Integer.parseInt(champLoc[1]),champLoc[3],champLoc[4]);

	   Logement studioNice = new Logement(champLoc[0],adresse);
	   Personne locataire = new Personne(champLoc[5],champLoc[6]);

		Location uneLocation = new Location(locataire, DateUtil.parse(champLoc[7]), studioNice, Float.parseFloat(champLoc[8]), Float.parseFloat(champLoc[9]));

		mainAppJX.listeLocation.add(uneLocation);

   }



   }

