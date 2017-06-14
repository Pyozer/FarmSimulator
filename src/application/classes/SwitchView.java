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
    private Parent parent;

    private boolean showAndWait = false;
    private boolean popup = false;

    private final static String STYLECSS = "styles.css";

    public SwitchView(String view, String title, Parent parent) {
        this.parent = parent;

        fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource(Constant.LAYOUT_PATH + view + ".fxml"));
            fxmlLoader.load();

            Scene scene = new Scene(fxmlLoader.getRoot());
            scene.getStylesheets().add(Constant.STYLE_PATH + STYLECSS);
            newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(title + " - " + Constant.TITLE_APP);

            newStage.setMinHeight(Constant.MIN_HEIGHT);
            newStage.setMinWidth(Constant.MIN_WIDTH);
            newStage.setHeight(Constant.PREF_HEIGHT);
            newStage.setWidth(Constant.PREF_WIDTH);

            newStage.setMaximized(false);
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

    public void setPopUp() {
        this.popup = true;
    }

    public FXMLLoader getFxmlLoader() { return fxmlLoader; }

    public void showScene() {
        if (!popup)
            newStage.setMaximized(true);

        if (showAndWait)
            newStage.showAndWait();
        else
            newStage.show();

        if (!popup && parent != null) {
              Stage stage = (Stage) parent.getScene().getWindow();
              stage.close();
        }
    }
}