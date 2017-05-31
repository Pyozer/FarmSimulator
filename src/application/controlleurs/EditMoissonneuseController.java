package application.controlleurs;

import application.modeles.Moissonneuse;
import application.modeles.Tracteur;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class EditMoissonneuseController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initaliser la combobox avec une view
        liste_etat.getItems().addAll("En maitenance", "Utilisé", "Non utilisé");
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
    }

    @FXML
    public void onSubmit() {
        System.out.println("BOUTON SUBMIT CLIQUÉ");
    }


}
