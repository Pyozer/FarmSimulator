package application.classes;

import application.Constant;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe pour gérer le changement des vues
 */
public class SwitchView {

    private Stage newStage;
    private FXMLLoader fxmlLoader;
    private Parent root;

    private boolean showAndWait = false;
    private boolean popup = false;

    private final static String STYLECSS = "styles.css";

    public SwitchView(String view, String title) {
        fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource(Constant.LAYOUT_PATH + view + ".fxml"));
            fxmlLoader.load();

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

    public SwitchView(String view, String title, boolean showAndWait) {
        this(view, title);
        this.showAndWait = showAndWait;
    }

    public void setPopUp(boolean popup) {
        this.popup = popup;
    }

    public FXMLLoader getFxmlLoader() { return fxmlLoader; }

    public void showScene() {
        if (!popup)
            newStage.setMaximized(false);

        if (showAndWait)
            newStage.showAndWait();
        else
            newStage.show();

        /*if (root != null) {
              Stage stage = (Stage) root.getScene().getWindow();
              stage.close();
        }*/
    }
}