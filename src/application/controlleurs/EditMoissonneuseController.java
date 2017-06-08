package application.controlleurs;

import application.modeles.Moissonneuse;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

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

    private VehiculeController vehiculeController;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initalisation des Comboox
        liste_etat.getItems().setAll("En maitenance", "Utilisé", "Non utilisé");
        liste_etat.setValue(liste_etat.getItems().get(0));
    }

    public void initTextFields(Moissonneuse moissonneuse) {
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

    @FXML
    public void onSubmit() {
        // TODO: Modification d'une moissonneuse
    }

    public void defineVehiculeController(VehiculeController vehiculeController) {
        this.vehiculeController = vehiculeController;
    }
}