package application.controlleurs;

import application.Constant;
import application.classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Created by justin on 19/05/2017.
 *
 */

public class GlobalController implements APIGoogleMap {

    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;

    @FXML private TextField FChamp;
    @FXML private TextField SChamp;

    private GoogleMaps gMaps;

    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("maps_global", this);
        gMaps.setParent(googleMaps);

    }

    public void setFirstChamp(String firstChamp) {
        FChamp.setText(firstChamp.replace("(", "").replace(")", ""));
        FChamp.requestFocus();
    }
    public void setSecondChamp(String secondChamp) {
        SChamp.setText(secondChamp.replace("(", "").replace(")", ""));
        SChamp.requestFocus();
    }

    public void calculIntineraire() {
        if (FChamp.getText().isEmpty() || SChamp.getText().isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Veuillez saisir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            gMaps.changeRoute(FChamp.getText(), SChamp.getText());
        }
    }

    @FXML
    public void goToCommandes() {
        SwitchView switchView = new SwitchView("commande_app", Constant.ACCUEIL_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void log(String msg) {
        System.err.println(msg);
    }
}
