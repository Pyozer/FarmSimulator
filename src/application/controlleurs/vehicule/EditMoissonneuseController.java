package application.controlleurs.vehicule;

import application.classes.AlertDialog;
import application.modeles.Moissonneuse;
import application.modeles.VehiculeSQL;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controlleur pour la modification d'une moissonneuse
 */
public class EditMoissonneuseController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXComboBox<String> liste_etat;
    @FXML private JFXTextField modele;
    @FXML private JFXTextField marque;
    @FXML private JFXTextField taille_tremis;
    @FXML private JFXTextField taille_reservoir;
    @FXML private JFXTextField largeur_route;
    @FXML private JFXTextField largeur_coupe;
    @FXML private JFXTextField hauteur;
    @FXML private JFXTextField conso_fonctionnement;
    @FXML private JFXTextField conso_route;
    @FXML private JFXTextField poids;

    @FXML private Label title;

    private VehiculeController vehiculeController;
    private ChoixVehiculeController choixVehiculeController;
    private Moissonneuse moissonneuseToEdit;

    private boolean isEdit = false;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initalisation des Comboox
        liste_etat.getItems().setAll("En maitenance", "Utilisé", "Non utilisé");
        liste_etat.setValue(liste_etat.getItems().get(0));
    }

    public void setEditionMode(boolean state) {
        isEdit = state;
    }

    public void initView(Moissonneuse moissonneuse) {
        if(isEdit) {
            title.setText("Modifier la moissonneuse");

            moissonneuseToEdit = moissonneuse;

            modele.setText(moissonneuse.getModele());
            marque.setText(moissonneuse.getMarque());
            liste_etat.setValue(moissonneuse.getEtat());
            taille_tremis.setText(String.valueOf(moissonneuse.getCapacite_tremis()));
            taille_reservoir.setText(String.valueOf(moissonneuse.getCapacite_reservoir()));
            largeur_route.setText(String.valueOf(moissonneuse.getLargeur()));
            largeur_coupe.setText(String.valueOf(moissonneuse.getTaille_coupe()));
            conso_fonctionnement.setText(String.valueOf(moissonneuse.getConso_fonctionnement()));
            conso_route.setText(String.valueOf(moissonneuse.getConso_route()));
            poids.setText(String.valueOf(moissonneuse.getPoids()));
            hauteur.setText(String.valueOf(moissonneuse.getHauteur()));
        }
    }

    @FXML
    public void handleSaveMoissonneuse() {
        String inputEtat = liste_etat.getValue();
        String inputMarque = marque.getText();
        String inputModele = modele.getText();
        String inputTailleTremisString = taille_tremis.getText();
        String inputTailleReservoirString = taille_reservoir.getText();
        String inputLargeurRouteString = largeur_route.getText();
        String inputLargeurCoupeString = largeur_route.getText();
        String inputConsoFonctionnementString = conso_fonctionnement.getText();
        String inputConsoRouteString = conso_route.getText();
        String inputPoidsString = poids.getText();
        String inputHauteurString = hauteur.getText();

        try {
            if(inputMarque.isEmpty() || inputModele.isEmpty() || inputTailleReservoirString.isEmpty() || inputEtat.isEmpty() || inputTailleTremisString.isEmpty() ||
                    inputLargeurRouteString.isEmpty() || inputTailleReservoirString.isEmpty() || inputLargeurCoupeString.isEmpty() || inputConsoFonctionnementString.isEmpty() ||
                    inputConsoRouteString.isEmpty() || inputPoidsString.isEmpty() || inputHauteurString.isEmpty()) {
                AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs !", Alert.AlertType.ERROR);
                alert.show();
            } else {
                String message = "La moissonneuse a bien été";

                int inputTailleReservoir = Integer.parseInt(inputTailleReservoirString.replace(',', '.'));
                float inputLargeurRoute = Float.parseFloat(inputLargeurRouteString.replace(',', '.'));
                float inputLargeurCoupe = Float.parseFloat(inputLargeurCoupeString.replace(',', '.'));
                int inputConsoFonctionnement = Integer.parseInt(inputConsoFonctionnementString.replace(',', '.'));
                int inputConsoRoute = Integer.parseInt(inputConsoRouteString.replace(',', '.'));
                float inputPoids = Float.parseFloat(inputPoidsString.replace(',', '.'));
                float inputHauteur = Float.parseFloat(inputHauteurString.replace(',', '.'));
                int inputTailleTremis = Integer.parseInt(inputTailleTremisString.replace(',', '.'));

                if(isEdit) {

                    moissonneuseToEdit.setEtat(inputEtat);
                    moissonneuseToEdit.setMarque(inputMarque);
                    moissonneuseToEdit.setModele(inputModele);
                    moissonneuseToEdit.setCapacite_reservoir(inputTailleReservoir);
                    moissonneuseToEdit.setCapacite_tremis(inputTailleTremis);
                    moissonneuseToEdit.setConso_fonctionnement(inputConsoFonctionnement);
                    moissonneuseToEdit.setHauteur(inputHauteur);
                    moissonneuseToEdit.setLargeur(inputLargeurRoute);
                    moissonneuseToEdit.setConso_route(inputConsoRoute);
                    moissonneuseToEdit.setPoids(inputPoids);
                    moissonneuseToEdit.setTaille_coupe(inputLargeurCoupe);

                    VehiculeSQL.editMoissonneuse(moissonneuseToEdit);

                    message += " modifié !";
                } else {
                    VehiculeSQL.addMoissonneuse(inputModele, inputMarque, inputEtat, inputConsoFonctionnement, inputConsoRoute, inputHauteur, inputLargeurCoupe, inputLargeurRoute, inputPoids, inputTailleReservoir, inputTailleTremis);

                    message += " ajouté !";
                }

                AlertDialog alert = new AlertDialog("Succès", null, message, Alert.AlertType.INFORMATION);
                alert.show();

                vehiculeController.initData();
                vehiculeController.clearAllSelection();

                if(choixVehiculeController != null)
                    choixVehiculeController.closeWindow();

                Stage stage = (Stage) bpane.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException  e){
            AlertDialog alert = new AlertDialog("Erreur", null, "Les champs de texte à chiffres doit être un nombre !\nUtilisez un . ou , pour les nombres décimaux.", Alert.AlertType.ERROR);
            alert.show();
        }
    }

    public void defineVehiculeController(VehiculeController vehiculeController, ChoixVehiculeController choixVehiculeController) {
        this.vehiculeController = vehiculeController;
        this.choixVehiculeController = choixVehiculeController;
    }
}