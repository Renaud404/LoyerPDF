package util;

//import java.awt.Color;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDType0Font;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import loyer.model.Location;
import loyer.model.Proprietaire;



public class PDFgenerator {

    public PDFgenerator () throws IOException {
    }

    public void genererPDF(Location loc, int moisLoc, Proprietaire proprio) throws IOException {
    	
  

          PDDocument document = new PDDocument();
          document.addPage(new PDPage());

          PDPage page = document.getPage(0);

          PDPageContentStream contentStream = new PDPageContentStream(document, page);

          //Begin the Content stream
          contentStream.beginText();

          //Setting the font to the Content stream
          PDType0Font font = PDType0Font.load(document, new File("c:/windows/fonts/arial.ttf"));

          contentStream.setFont(font, 30);

          //Setting the position for the line
          contentStream.newLineAtOffset(120, 700);

          Color color = new Color(18,166,208);
          contentStream.setNonStrokingColor(color);

          String text = "QUITTANCE DE LOYER";

          //Adding text in the form of string
          contentStream.showText(text);

          //Ending the content stream
          contentStream.endText();

          contentStream.setStrokingColor(184,184,184);
          contentStream.moveTo(50, 740);
          contentStream.lineTo(550, 740);
          contentStream.stroke();

          contentStream.moveTo(50, 680);
          contentStream.lineTo(550, 680);
          contentStream.stroke();

          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 640);
          contentStream.setFont(font, 12);
          contentStream.setNonStrokingColor(Color.BLACK);
          text = "Période : " + loc.getlisteQuittance().get(moisLoc).moisTexteString();
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/


          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 580);
          contentStream.setFont(font, 16);
          color = new Color(245,164,34);
          contentStream.setNonStrokingColor(color);
          text = "Adresse de location :";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(195, 580);
          contentStream.setFont(font, 12);
          contentStream.setNonStrokingColor(Color.BLACK);
          text = loc.getLeLogement().getLadresse().adresseComplete();
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/

          /*----------------- AJOUT TEXTE * -------------*/

          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 530);
          contentStream.setFont(font, 12);
          contentStream.setNonStrokingColor(Color.BLACK);
          text = "Je soussigné " + proprio.getNomComplet() + ", propriétaire du logement désigné ci-dessus, déclare avoir reçu de";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/

          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 510);
          contentStream.setFont(font, 12);
          text =  loc.getLocataire().getNomComplet() + " la somme de " + loc.getlisteQuittance().get(moisLoc).totalLoyer() + " € , au titre du paiement du loyer et des charges pour la période";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/

          /*----------------- AJOUT TEXTE -------------*/

          LocalDate debutmois = loc.getlisteQuittance().get(moisLoc).getMoisConcerne().withDayOfMonth(1);

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
  		  String ladatedebutmois = debutmois.format(formatter);

  		  LocalDate finmois = loc.getlisteQuittance().get(moisLoc).getMoisConcerne().withDayOfMonth(debutmois.lengthOfMonth());

  		  String ladatedefin = finmois.format(formatter);

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 490);
          contentStream.setFont(font, 12);
          text = "de location du "+ ladatedebutmois + " au " + ladatedefin + " et lui en donne quittance, sous réserve de tous ";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/

          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 470);
          contentStream.setFont(font, 12);
          text = "mes droits.";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/
          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 400);
          contentStream.setFont(font, 16);
          color = new Color(245,164,34);
          contentStream.setNonStrokingColor(color);
          text = "Détail du règlement  :";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/
          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 370);
          contentStream.setFont(font, 12);
          contentStream.setNonStrokingColor(Color.BLACK);
          text = "Loyer : " + loc.getlisteQuittance().get(moisLoc).getUnelocation().getMontantLoyer()  + " € ";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/
          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 350);
          contentStream.setFont(font, 12);
          text = "Charges : " + loc.getlisteQuittance().get(moisLoc).getUnelocation().getMontantCharges()  + " €";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/

          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 310);
          contentStream.setFont(font, 12);
          text = "Total : " + loc.getlisteQuittance().get(moisLoc).totalLoyer() + " €";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/
          /*----------------- AJOUT TEXTE -------------*/

          LocalDate finmoisplusdix = finmois.plusDays(10);

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 250);
          contentStream.setFont(font, 12);
          text = "Fait à " + proprio.getVilleProprietaire() +  ", le " + finmoisplusdix.format(formatter) + "  ";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/
          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 210);
          contentStream.setFont(font, 12);
          text = "Signature";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/
          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 170);
          contentStream.setFont(font, 10);
          text = "Cette quittance annule tous les reçus qui auraient pu être établis précédemment en cas de paiement partiel du montant du ";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/
          /*----------------- AJOUT TEXTE -------------*/

          contentStream.beginText();
          contentStream.newLineAtOffset(25, 160);
          contentStream.setFont(font, 10);
          text = "présent terme. Elle est à conserver pendant trois ans par le locataire (article 7-1 de la loi n° 89-462 du 6 juillet 1989). ";
          contentStream.showText(text);
          contentStream.endText();

          /*----------------- AJOUT TEXTE * -------------*/

          /*----------------- AJOUT IMG * -------------*/
          
         
          try {
        	  //System.out.println(proprio.getUrlSignature());
        	  PDImageXObject pdImage = PDImageXObject.createFromFile(proprio.getUrlSignature(),document);
              contentStream.drawImage(pdImage,85, 190, 58,50);
        	  
          }catch(Exception e) {
        	  System.out.println(proprio.getUrlSignature());
        	    Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Results:");
                alert.setContentText(proprio.getUrlSignature());
                alert.showAndWait();
          }
         /* PDImageXObject pdImage = PDImageXObject.createFromFile(proprio.getUrlSignature(),document);
          contentStream.drawImage(pdImage,85, 190, 58,50);*/

          contentStream.close();
  
          try {
        	  
        	  /* f = null;
             /* f = File.createFile(System.getProperty("user.dir")+"-", ".pdf", null);*/

        	  File f = null;
        	  f = File.createTempFile(loc.getlisteQuittance().get(moisLoc).moisTexteString()+"-", ".pdf", null);
        	  System.out.println("File path: "+f.getAbsolutePath());
          document.save(f);
          document.close();
          Desktop.getDesktop().open(f);        
              
      
          }catch (Exception e) {
        	
          
        	  e.printStackTrace();
          }
          
          //System.out.println(f);

          

         
          //file.deleteOnExit();
       }

	private File File(String string, String string2, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
