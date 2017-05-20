package application.controlleurs;

import application.classes.APIGoogleMap;
import application.classes.Point;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import application.classes.GoogleMaps;
import application.classes.MenuApp;
import javafx.fxml.Initializable;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by justin on 19/05/2017.
 */

public class GlobalController implements Initializable, APIGoogleMap {

    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;

    @FXML private TextField FChamp;
    @FXML private TextField SChamp;

    private GoogleMaps gMaps;

    public void initialize(URL location, ResourceBundle resources) {
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
        gMaps.changeRoute(FChamp.getText(), SChamp.getText());
    }
}
