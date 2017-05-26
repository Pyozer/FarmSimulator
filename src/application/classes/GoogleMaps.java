package application.classes;

import application.Constant;
import application.modeles.Agriculteur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;

/**
 * Class permettant de créer une carte Google Maps avec l'API V3
 */
public class GoogleMaps extends Region {

    private WebView webView;
    private WebEngine webEngine;
    private JSObject javascriptOBJ;

    public GoogleMaps(String mapHTML, APIGoogleMap controller) {

        webView = new WebView();
        webEngine = webView.getEngine();

        final URL urlGoogleMaps = getClass().getResource(Constant.LAYOUT_PATH + mapHTML + ".html");
        webEngine.load(urlGoogleMaps.toExternalForm());

        javascriptOBJ = (JSObject) webView.getEngine().executeScript("window");
        javascriptOBJ.setMember("jsInterface", controller);
        webEngine.setOnAlert(e -> System.out.println(e.toString()));
        webEngine.setOnError(e -> System.err.println(e.toString()));

    }

    public void setParent(StackPane parent) {
        parent.getChildren().add(webView);
    }

    /** Affiche L'itineraire entre 2 points sur la Map **/
    public void changeRoute(String originNew, String destNew) {
        javascriptOBJ.call("calculate", originNew, destNew);
    }

    /** Ajoute un Point sur la Map **/
    public void addMarker(int id, Point position, String title, String type, String etat) {
        javascriptOBJ.call("addMarker", id, position.x(), position.y(), title, type, etat);
    }

    /** Ajoute un Champ sur la Map **/
    public void addChamp(int id, String culture, Agriculteur proprio, String adresse, double surface, Polygon coords) {
        javascriptOBJ.call("addChamp", id, culture, proprio.toString(), adresse, surface, JSONManager.write(coords.getPoints()));
    }

    /** Cache tous les Marker sauf un sur la carte **/
    public void hideAllExceptOne(int id) {
        javascriptOBJ.call("hideAllExceptOne", id);
    }

    /** Réaffiche tous les markers sur la carte **/
    public void showAll() {
        javascriptOBJ.call("showAll");
    }

}
