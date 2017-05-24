package application.classes;

import application.Constant;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

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
        webEngine.load(getClass().getResource(Constant.LAYOUT_PATH + mapHTML + ".html").toExternalForm());
        javascriptOBJ = (JSObject) webView.getEngine().executeScript("window");
        javascriptOBJ.setMember("jsInterface", controller);
        webEngine.setOnAlert(e -> System.out.println(e.toString()));

    }

    public void setParent(StackPane parent) {
        parent.getChildren().add(webView);
    }

    /** Affiche L'itineraire entre 2 points sur la Map **/
    public void changeRoute(String originNew, String destNew) {
        javascriptOBJ.call("calculate", originNew, destNew);
    }

    /** Ajoute un Point sur la Map **/
    public void addMarker(Point position, String title, String type, String etat) {
        javascriptOBJ.call("addMarker", position.x(), position.y(), title, type, etat);
    }

}
