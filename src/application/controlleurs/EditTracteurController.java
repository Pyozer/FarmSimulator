package application.controlleurs;

import application.modeles.Tracteur;
import application.modeles.Vehicule;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTracteurController implements Initializable {

    /** Layout **/
    @FXML
    private BorderPane bpane;
    /** Elements **/
    @FXML
    private JFXTextField modele;
    @FXML
    private JFXTextField marque;
    @FXML
    private JFXTextField cap_rem;
    @FXML
    private JFXComboBox<String> liste_etat;

    /** Initializes the controller class. **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initaliser la combobox avec une view
        liste_etat.getItems().addAll("En maitenance", "Utilisé", "Non utilisé");
        System.out.println("poiuygtf");
    }

    @FXML
    public void handleSaveTracteur() {
        System.out.println("BOUTON Sauvegarde CLIQUÉ");
        System.out.println("Créer une tracteur : " );
        System.out.println("         -Marque  : " + marque.getText());
        System.out.println("         -Modèle  : " + modele.getText());
        System.out.println("         -Etat  : " + liste_etat.getValue());
        System.out.println("         -Capacité de rendement  : " + cap_rem.getText());
    }

    public void setVehiculeData(Vehicule v){
        Tracteur selectedTracteur = (Tracteur) v;
        selectedTracteur.toString();
    }
}