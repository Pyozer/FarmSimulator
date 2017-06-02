package application.classes;

import application.Constant;
import application.controlleurs.*;
import application.modeles.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe pour gérer le changement des vues
 */
public class SwitchViewData {

    private Stage newStage;

    private final static String STYLECSS = "styles.css";

    public SwitchViewData(String view, String title, Element data) {
        Parent root;

        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource(Constant.LAYOUT_PATH + view + ".fxml"));

            fxmlLoader.load();
            if(data != null){
                if(data instanceof Tracteur) {
                    EditTracteurController editTraController = fxmlLoader.getController();
                    editTraController.initTextFields((Tracteur) data);
                }
                else if(data instanceof Moissonneuse) {
                    EditMoissonneuseController editMoiController = fxmlLoader.getController();
                    editMoiController.initTextFields((Moissonneuse) data);
                }
                else if(data instanceof Botteleuse) {
                    EditBotteleuseController editBotController = fxmlLoader.getController();
                    editBotController.initTextFields((Botteleuse) data);
                }
                else if(data instanceof Agriculteur) {
                    EditClientController editClientController = fxmlLoader.getController();
                    editClientController.initTextFields((Agriculteur) data);
                }
                else if(data instanceof Commande) {
                    EditCommandeController editCommandeController = fxmlLoader.getController();
                    editCommandeController.initTextFields((Commande) data);
                }
            }

            root = fxmlLoader.getRoot();
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

    public void showScene() {
        newStage.show();
    }
}