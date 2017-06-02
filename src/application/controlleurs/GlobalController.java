package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by justin on 19/05/2017.
 *
 */

public class GlobalController implements APIGoogleMap {

    @FXML private BorderPane bpane;
    @FXML private VBox infoContent;
    @FXML private StackPane googleMaps;
    @FXML private VBox section_affection;

    @FXML private JFXTextField FChamp;
    @FXML private JFXTextField SChamp;

    @FXML private JFXButton markToDone;
    @FXML private JFXButton newAffect;
    @FXML private JFXButton editAffect;

    @FXML private TableView<Commande> tableView;
    @FXML private TableColumn<Commande, String> column_date;
    @FXML private TableColumn<Commande, String> column_adresse;
    @FXML private TableColumn<Commande, String> column_transport;
    @FXML private TableColumn<Commande, String> column_type_bott;

    private GoogleMaps gMaps;
    private CommandeSQL commandeSQL;

    private List<Commande> commandeList;

    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("maps_global", this);
        gMaps.setParent(googleMaps);

        column_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        column_adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChampCommande().getAdresse()));
        column_transport.setCellValueFactory(new PropertyValueFactory<>("transport"));
        column_type_bott.setCellValueFactory(new PropertyValueFactory<>("typebott"));

        commandeSQL = new CommandeSQL();
        commandeList = commandeSQL.getCommandeList(5);

        tableView.getItems().addAll(commandeList);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showButtons(newvalue));

        infoContent.setOnMouseClicked(event -> clearAllSelection());
    }

    public void setFirstChamp(String firstChamp) {
        FChamp.setText(firstChamp.replace("(", "").replace(")", ""));
        FChamp.requestFocus();
    }
    public void setSecondChamp(String secondChamp) {
        SChamp.setText(secondChamp.replace("(", "").replace(")", ""));
        SChamp.requestFocus();
    }

    public void calculIntineraire() {
        if (FChamp.getText().isEmpty() || SChamp.getText().isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Veuillez saisir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            gMaps.changeRoute(FChamp.getText(), SChamp.getText());
        }
    }

    public void askToLoadMarkers() {

        VehiculeSQL vehiculeSQL = new VehiculeSQL();
        for(Vehicule vehicule : vehiculeSQL.getVehiculeList()) {
            gMaps.addMarker(vehicule.getId(), vehicule.getPosition(), vehicule.toString(), vehicule.getType(), vehicule.getEtat());
        }
    }

    public void askToLoadChamps() {
        gMaps.removeAll();
        ChampSQL champSQL = new ChampSQL();
        System.out.println(champSQL.getChampsList());
        for (Champ champ : champSQL.getChampsList()) {

            gMaps.addChamp(champ.getId(), champ.getType_culture(), champ.getProprietaire(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp());
        }
    }

    @FXML
    public void goToCommandes() {
        SwitchView switchView = new SwitchView("commande_app", Constant.ACCUEIL_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void markToDone() {

    }

    public void showButtons(Commande commande) {
        markToDone.setVisible(true);
    }

    public void showAffectation() {

    }
    public void clearAllSelection() {
        markToDone.setVisible(true);
        tableView.getSelectionModel().clearSelection();
    }

    public void log(String msg) {
        System.err.println(msg);
    }

    public void newAffect() {

    }

    public void editAffect() {
    }
}
