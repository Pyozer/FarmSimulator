package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import application.modeles.ChampSQL;
import application.modeles.Vehicule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class ChampController implements Initializable, APIGoogleMap {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
    @FXML private TableView<Champ> tableView;
    @FXML private TableColumn<Champ, String> column_type_culture;
    @FXML private TableColumn<Champ, Agriculteur> column_proprietaire;

    @FXML private ListView<ElementPair> listInfos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        GoogleMaps gMaps = new GoogleMaps("maps_champ", this);
        gMaps.setParent(googleMaps);

        column_type_culture.setCellValueFactory(new PropertyValueFactory<>("type_culture"));
        column_proprietaire.setCellValueFactory(new PropertyValueFactory<>("proprietaire"));

        ChampSQL champSQL = new ChampSQL();
        tableView.getItems().addAll(champSQL.getChampsList());
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsChamp(newvalue));

        listInfos.getItems().add(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));

    }

    public void showInformationsChamp(Champ champ) {
        listInfos.getItems().clear();
        for(ElementPair information : champ.getInformations())
            listInfos.getItems().add(information);
    }

    @FXML
    public void addChamp() {
        SwitchView switchView = new SwitchView("add_champ_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void deleteChamp() {
        AlertDialog alert = new AlertDialog("Suppression", null, "Voulez vous vraiment supprimer ce champ ?", Alert.AlertType.WARNING);
        alert.show();
    }

    @FXML
    public void editChamp() {
        SwitchView switchView = new SwitchView("add_champ_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

}
