package application.controlleurs;

import java.net.URL;
import java.util.ResourceBundle;

import application.Constant;
import application.classes.SwitchView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Controlleur de la vue de paramétrage de des infos de l'ETA
 */
public class HomeController  implements Initializable {

    /** Layout **/
    @FXML private BorderPane bpane;

    /** Elements **/
    /*@FXML private ImageView img_client;
    @FXML private ImageView img_vehicule;
    @FXML private ImageView img_champs;
    @FXML private ImageView img_global;
    @FXML private ImageView img_params;
    @FXML private ImageView img_exit;*/

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        /*img_client.setImage(new Image(Constant.IMAGE_PATH + "farmer.png"));
        img_vehicule.setImage(new Image(Constant.IMAGE_PATH + "tractor.png"));
        img_champs.setImage(new Image(Constant.IMAGE_PATH + "champs.png"));
        img_global.setImage(new Image(Constant.IMAGE_PATH + "champs.png"));
        img_params.setImage(new Image(Constant.IMAGE_PATH + "settings.png"));
        img_exit.setImage(new Image(Constant.IMAGE_PATH + "power.png"));*/

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
