# LoyerPDF
 
Logiciel gratuite pour créer des quittances de loyer en PDF.

Une version prête à utiliser est disponible ici : https://github.com/Renaud404/LoyerPDF/releases/download/v1.0/LoyerPDF.rar 

Il suffit de décompresser l'archive et de lancer le fichier LoyerPDF.exe

Le logiciel permet de créer un nombre illimité de locations et de générer les quittances correspondantes. La quittance est datée à 10 jours après la fin du mois concerné. Pour des locations en cours de mois le premier loyer est calculé au prorata.

Il est possible d'ajouter un fichier image de sa signature et de configurer le propriétaire dans Fichier > Configurer le propriétaire.

Il y a une location exemple 'StudioF1' pour voir le fonctionnement du logiciel.



___________________

D'un point de vue dev :
C'est une libre adaptation de l'excellent tuto sur JavaFX disponible ici : https://code.makery.ch/fr/library/javafx-tutorial/

Le projet permet de voir quelques fonctionnalités supplémentaires :
- Ouvrir une fenêtre dans la fenêtre en cours
- Générer des PDF en Java avec Apache PDFBox https://pdfbox.apache.org/
- Importer et exporter des fichiers textes de sauvegarde pour les locations
- Utilisation des imageView de JavaFX
- 'preferences' de Java pour sauvegarder les infos du propriétaire dans le registre
- Affichage d'un bouton dans une tableView dans une colonne particulière, avec un listener qui génère le PDF stocké dans un répertoire temporaire
- Des objects imbriqués
