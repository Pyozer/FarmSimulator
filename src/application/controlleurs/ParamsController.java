package application.controlleurs;

import application.Constant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Controlleur de la vue de paramétrage global
 */
public class ParamsController {

    /** Layout **/
    @FXML BorderPane bpane;
    @FXML TabPane tpane;
    @FXML Tab bdd_tab;
    @FXML Tab info_tab;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        /*try {
            BorderPane tmpPane = FXMLLoader.load(getClass().getResource(Constant.LAYOUT_PATH  + "params_bdd.fxml"));
            bdd_tab.setContent(tmpPane);

            tmpPane = FXMLLoader.load(getClass().getResource("/layouts/params_infos.fxml"));
            info_tab.setContent(tmpPane);

         } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    public void bddTabClicked() throws IOException {
        bdd_tab.setContent(FXMLLoader.load(getClass().getResource(Constant.LAYOUT_PATH  + "params_bdd.fxml")));
    }

    @FXML
    public void etaTabClicked() throws IOException {
        info_tab.setContent(FXMLLoader.load(getClass().getResource(Constant.LAYOUT_PATH  + "params_infos.fxml")));
    }
}