package application.controlleurs;

import application.Constant;
import application.classes.SwitchView;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur de la vue de l'accueil
 */
public class HomeController {

    /** Layout **/
    @FXML private BorderPane bpane;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());
    }

    @FXML
    public void handleClient() {
        SwitchView switchView = new SwitchView("client/client_app", Constant.CLIENT_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleVehicule() {
        SwitchView switchView = new SwitchView("vehicule/vehicule_app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleChamps() {
        SwitchView switchView = new SwitchView("champ/champ_app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void handleGlobal() {
        SwitchView switchView = new SwitchView("global/global_app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleParams() {
        SwitchView switchView = new SwitchView("parametre/params-app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleExit() {
        SwitchView switchView = new SwitchView("home_login_app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }
}
