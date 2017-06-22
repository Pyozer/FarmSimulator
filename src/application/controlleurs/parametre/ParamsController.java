package application.controlleurs.parametre;

import application.Constant;
import application.classes.MenuApp;
import application.modeles.UserSQL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Controlleur de la vue de paramétrage global
 */
public class ParamsController {

    /**
     * Layout
     **/
    @FXML
    private BorderPane bpane;
    @FXML
    private Tab bdd_tab;
    @FXML
    private Tab info_tab;
    @FXML
    private Tab account_tab;

    private FXMLLoader fxmlLoader;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        fxmlLoader = loadFXML("parametre/params_bdd.fxml");
        ParamsBddController bddParam = fxmlLoader.getController();
        bddParam.setToEditionMode();
        bdd_tab.setContent(fxmlLoader.getRoot());

        fxmlLoader = loadFXML("parametre/params_infos.fxml");
        ParamsInfosController infoParam = fxmlLoader.getController();
        infoParam.setToEditionMode();
        info_tab.setContent(fxmlLoader.getRoot());

        fxmlLoader = loadFXML("parametre/params_account.fxml");
        ParamsAccountController accountParam = fxmlLoader.getController();
        accountParam.setToEditionMode();
        accountParam.initView(UserSQL.getUser());
        account_tab.setContent(fxmlLoader.getRoot());

    }

    public FXMLLoader loadFXML(String fxmlFile) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(Constant.LAYOUT_PATH + fxmlFile));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fxmlLoader;
    }
}