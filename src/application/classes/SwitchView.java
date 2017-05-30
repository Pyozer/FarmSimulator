package application.classes;

import application.Constant;
import application.controlleurs.EditBotteleuseController;
import application.controlleurs.EditMoissonneuseController;
import application.controlleurs.EditTracteurController;
import application.modeles.Botteleuse;
import application.modeles.Moissonneuse;
import application.modeles.Tracteur;
import application.modeles.Vehicule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe pour gérer le changement des vues
 */
public class SwitchView {

    private Vehicule selectedVehicule = null;
    private BorderPane borderPane;
    private Stage newStage;
    private boolean showAndWait = false;
    private boolean popup = false;

    private final static String STYLECSS = "styles.css";

    private static Pane parent;

    public SwitchView(String view, String title, BorderPane bpane) {
        borderPane = bpane;
        Parent root;

        try {
            // permet de passer des paramètre de l'ancienne page à la nouvelle
            FXMLLoader load = new FXMLLoader(getClass().getResource(Constant.LAYOUT_PATH + view + ".fxml"));
            if(selectedVehicule != null){
                if(selectedVehicule instanceof Tracteur) load.setController(new EditTracteurController());
                else if(selectedVehicule instanceof Moissonneuse) load.setController(new EditMoissonneuseController());
                else if(selectedVehicule instanceof Botteleuse) load.setController(new EditBotteleuseController());
            }

            root = load.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Constant.STYLE_PATH + STYLECSS);

            newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(title + " - " + Constant.TITLE_APP);

            newStage.setMinHeight(Constant.MIN_HEIGHT);
            newStage.setMinWidth(Constant.MIN_WIDTH);
            newStage.setHeight(Constant.PREF_HEIGHT);
            newStage.setWidth(Constant.PREF_WIDTH);

            newStage.setMaximized(true);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SwitchView(String view, String title) {
        this(view, title, null);
    }

    public SwitchView(String view, String title, boolean showAndWait) {
        this(view, title, null);
        this.showAndWait = showAndWait;
    }

    public void setPopUp(boolean popup) {
        this.popup = popup;
    }

    public void showScene() {
        if(!popup) {
            newStage.setMaximized(false);
        }
        if(showAndWait)
            newStage.showAndWait();
        else {
            newStage.show();
        }
        if(borderPane != null) {
            /*
             * DEBUG
             *
             * Stage stage = (Stage) borderPane.getScene().getWindow();
             * stage.close();
             */
        }
    }

    public void setSelectedVehicule(Vehicule v){
        selectedVehicule = v;
    }
}
