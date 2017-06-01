package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.Commande;
import application.modeles.CommandeSQL;
import application.modeles.Vehicule;
import application.modeles.VehiculeSQL;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by justin on 19/05/2017.
 *
 */

public class GlobalController implements APIGoogleMap {

    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;

    @FXML private TextField FChamp;
    @FXML private TextField SChamp;

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
        System.out.println(commandeList.size());

        tableView.getItems().addAll(commandeList);
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

    @FXML
    public void goToCommandes() {
        SwitchView switchView = new SwitchView("commande_app", Constant.ACCUEIL_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void log(String msg) {
        System.err.println(msg);
    }
}
