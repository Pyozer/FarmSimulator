package application.classes;

import application.Constant;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import application.modeles.Culture;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private WebEngine webEngine;
    private JSObject javascriptOBJ;

    public GoogleMaps(String mapHTML, APIGoogleMap controller) {

        webView = new WebView();
        webEngine = webView.getEngine();
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

    /** Affiche L'itineraire entre 2 points sur la Map **/
    public void changeRoute(String originNew, String destNew) {
        javascriptOBJ.call("calculate", originNew, destNew);
    }

    /** Ajoute un Point sur la Map **/
    public void addMarker(int id, Point position, String title, String type, String etat) {
        System.out.println("addMarker('" + id + "','" + position.x() + "','" + position.y() + "','" + title + "','" + type + "','" + etat + "');");
        javascriptOBJ.call("addMarker", id, position.x(), position.y(), title, type, etat);
    }

    /** Ajoute un Champ sur la Map **/
    public void addChamp(Champ champ) {
        addChamp(champ.getId(), champ.getType_culture(), champ.getProprietaire(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp(), champ.getProprietaire().getCouleur());
    }
    public void addChamp(int id, Culture culture, Agriculteur proprio, String adresse, float surface, Polygon coords, Color couleur) {
        try {
            javascriptOBJ.call("addChamp", id, culture, proprio.toString(), adresse, surface, coords.toString(), ConvertColor.ColorFXToWeb(couleur));
        } catch (JSException e) {
            e.printStackTrace();
        }
    }

    /** Cache tous les Marker sauf un sur la carte **/
    public void hideAllExceptOne(int id) {
        javascriptOBJ.call("hideAllExceptOne", id);
    }

    /** Réaffiche tous les champs sur la carte **/
    public void removeAllChamps() {
        javascriptOBJ.call("removeAllChamps");
    }

    /** Réaffiche tous les markers sur la carte **/
    public void removeAllMarkers() {
        javascriptOBJ.call("removeAllMarkers");
    }

    /** Réaffiche tous les champs et markers sur la carte **/
    public void removeAllChampsMarkers() {
        javascriptOBJ.call("removeAllChamps");
        javascriptOBJ.call("removeAllMarkers");
    }

}