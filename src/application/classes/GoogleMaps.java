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

    public GoogleMaps(String mapHTML, APIGoogleMap controller) {

        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource(Constant.LAYOUT_PATH + mapHTML + ".html").toExternalForm());
        final JSObject jsobj = (JSObject) webView.getEngine().executeScript("window");
        jsobj.setMember("jsInterface", controller);
        webEngine.setOnAlert(e -> System.out.println(e.toString()));

    }

    public void setParent(StackPane parent) {
        parent.getChildren().add(webView);
    }

    public void changeRoute(String originNew, String destNew) {
        webEngine.executeScript("calculate(\"" + originNew + "\", \"" + destNew + "\");");
    }

}
