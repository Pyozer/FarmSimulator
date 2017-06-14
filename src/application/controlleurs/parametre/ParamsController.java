package application.controlleurs.parametre;

import application.Constant;
import application.classes.MenuApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Controlleur de la vue de paramétrage global
 */
public class ParamsController {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private Tab bdd_tab;
    @FXML private Tab info_tab;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        try {
            bdd_tab.setContent(FXMLLoader.load(getClass().getResource(Constant.LAYOUT_PATH  + "parametre/params_bdd.fxml")));
            info_tab.setContent(FXMLLoader.load(getClass().getResource(Constant.LAYOUT_PATH  + "parametre/params_infos.fxml")));
         } catch (IOException e) {
            e.printStackTrace();
        }
    }
}