package application.classes;

import application.Constant;
import application.modeles.Champ;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.net.URL;

/**
 * Class permettant de créer une carte Google Maps avec l'API V3
 */
public class GoogleMaps extends Region {

    private WebView webView;
    private JSObject javascriptOBJ;

    public GoogleMaps(String mapHTML, APIGoogleMap controller) {

        webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        final URL urlGoogleMaps = getClass().getResource(Constant.LAYOUT_PATH + mapHTML + ".html");
        webEngine.load(urlGoogleMaps.toExternalForm());

        javascriptOBJ = (JSObject) webView.getEngine().executeScript("window");
        javascriptOBJ.setMember("jsInterface", controller);
        webEngine.setOnAlert(e -> System.out.println(e.toString()));
        webEngine.setOnError(e -> System.err.println(e.toString()));

    }

    public void setParent(StackPane parent) {
        parent.getChildren().setAll(webView);
    }

    /**
     * Affiche L'itineraire entre 2 points sur la Map
     *
     * @param originNew String
     * @param destNew   String
     */
    public void changeRoute(String originNew, String destNew) {
        javascriptOBJ.call("calculate", originNew, destNew);
    }

    /**
     * Ajoute un Point sur la Map
     *
     * @param id       int
     * @param position Point
     * @param title    String
     * @param type     String
     * @param etat     String
     */
    public void addMarker(int id, Point position, String title, String type, String etat) {
        javascriptOBJ.call("addMarker", id, position.getX(), position.getY(), title, type, etat);
    }

    /**
     * Ajoute un Champ sur la Map
     *
     * @param champ Champ
     */
    public void addChamp(Champ champ) {
        try {
            javascriptOBJ.call("addChamp", champ.getId(), champ.getTypeCulture().toString(),champ.getProprietaire().toString(), champ.getProprietaire().getId(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp(), ConvertColor.ColorFXToWeb(champ.getProprietaire().getCouleur()));
        } catch (JSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cache tous les Marker sauf un sur la carte
     * @param id int
     */
    public void hideAllExceptOne(int id) {
        javascriptOBJ.call("hideAllExceptOne", id);
    }

    /**
     * Réaffiche tous les champs sur la carte
     */
    public void removeAllChamps() {
        javascriptOBJ.call("removeAllChamps");
    }

    /**
     * Réaffiche tous les markers sur la carte
     */
    public void removeAllMarkers() {
        javascriptOBJ.call("removeAllMarkers");
    }

    /**
     * Réaffiche tous les champs et markers sur la carte
     */
    public void removeAllChampsMarkers() {
        javascriptOBJ.call("removeAllChamps");
        javascriptOBJ.call("removeAllMarkers");
    }

    /**
     * Active l'affiche de la distance à vol d'oiseau
     */
    public void enableFlightItinerary() {
        javascriptOBJ.call("enableFlightItinerary");
        javascriptOBJ.call("checkIfItinerary");
    }

    /**
     * Désactive l'affiche de la distance à vol d'oiseau
     */
    public void disableFlightItinerary() {
        javascriptOBJ.call("disableFlightItinerary");
        javascriptOBJ.call("hideFlightPath");
    }

}