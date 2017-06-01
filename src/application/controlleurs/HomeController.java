package application.controlleurs;

import application.Constant;
import application.classes.SwitchView;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur de la vue de paramétrage de des infos de l'ETA
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
        SwitchView switchView = new SwitchView("client_app", Constant.CLIENT_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleVehicule() {
        SwitchView switchView = new SwitchView("vehicule_app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleChamps() {
        SwitchView switchView = new SwitchView("champ_app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void handleGlobal() {
        SwitchView switchView = new SwitchView("global_app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleParams() {
        System.out.println("handleParams");
    }

    @FXML
    public void handleExit() {
        System.out.println("handleExit");
    }
}
