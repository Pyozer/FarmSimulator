package application.controlleurs;

import application.Constant;
import application.classes.SwitchView;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controlleur pour le choix de véhicue à ajouter
 */
public class ChoixVehiculeController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private ImageView img_tracteur;
    @FXML private ImageView img_moissonneuse;
    @FXML private ImageView img_botteleuse;

    @FXML private VBox tracteur;
    @FXML private VBox moissonneuse;
    @FXML private VBox botteleuse;

    private boolean tracteur_selected = false;
    private boolean moissonneuse_selected = false;
    private boolean botteleuse_selected = true;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        img_tracteur.setImage(new Image(Constant.IMAGE_PATH + "choix_tracteur.png"));
        img_botteleuse.setImage(new Image(Constant.IMAGE_PATH + "choix_botteleuse.png"));
        img_moissonneuse.setImage(new Image(Constant.IMAGE_PATH + "choix_moissonneuse.png"));

        botteleuse.getStyleClass().add("box_selected");
    }

    @FXML
    public void defineBotteleuse() {
        tracteur_selected = false;
        moissonneuse_selected = false;
        botteleuse_selected = true;
        botteleuse.getStyleClass().setAll("box_selected");
        tracteur.getStyleClass().setAll("box");
        moissonneuse.getStyleClass().setAll("box");
    }

    @FXML
    public void defineTracteur() {
        tracteur_selected = true;
        moissonneuse_selected = false;
        botteleuse_selected = false;
        tracteur.getStyleClass().setAll("box_selected");
        botteleuse.getStyleClass().setAll("box");
        moissonneuse.getStyleClass().setAll("box");
    }

    @FXML
    public void defineMoissonneuse() {
        tracteur_selected = false;
        moissonneuse_selected = true;
        botteleuse_selected = false;
        moissonneuse.getStyleClass().setAll("box_selected");
        tracteur.getStyleClass().setAll("box");
        botteleuse.getStyleClass().setAll("box");
    }

    public void nextStep() {
        SwitchView swtichView = new SwitchView("add_botteleuse_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        if (tracteur_selected) {
            swtichView = new SwitchView("add_tracteur_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        } else if(moissonneuse_selected) {
            swtichView = new SwitchView("add_moissonneuse_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        }
        swtichView.showScene();
    }
}