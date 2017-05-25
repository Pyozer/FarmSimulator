package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class VehiculeController implements Initializable, APIGoogleMap {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
    @FXML private TableView<Vehicule> tableView;
    @FXML private TableColumn<Vehicule, String> column_type;
    @FXML private TableColumn<Vehicule, String> column_marque;
    @FXML private TableColumn<Vehicule, String> column_modele;
    @FXML private TableColumn<Vehicule, String> column_etat;

    @FXML private ListView<ElementPair> listInfos;
    private GoogleMaps gMaps;
    private List<Vehicule> vehiculeList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("maps_vehicule", this);
        gMaps.setParent(googleMaps);

        column_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        column_marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        column_modele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        column_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        VehiculeSQL vehiculeSQL = new VehiculeSQL();
        vehiculeList = vehiculeSQL.getVehiculeList();

        tableView.getItems().addAll(vehiculeList);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsVehicule(newvalue));

        listInfos.getItems().add(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));

    }

    public void showInformationsVehicule(Vehicule vehicule) {
        listInfos.getItems().clear();
        for(ElementPair information : vehicule.getInformations())
            listInfos.getItems().add(information);
        gMaps.removeMarkers();
        gMaps.addMarker(vehicule.getId(), vehicule.getPosition(), vehicule.toString(), vehicule.getType(), vehicule.getEtat());

    }

    @FXML
    public void addVehicule() {
        SwitchView switchView = new SwitchView("choix_vehicule_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();

    }

    @FXML
    public void deleteVehicule() {
        AlertDialog alert = new AlertDialog("Suppression", null, "Voulez vous vraiment supprimer ce véhicule ?", Alert.AlertType.WARNING);
        alert.show();
    }

    @FXML
    public void editVehicule() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> System.out.println(newvalue));

        SwitchView switchView = new SwitchView("add_tracteur_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void askToLoadMarkers() {
        for(Vehicule vehicule : vehiculeList) {
            gMaps.addMarker(vehicule.getId(), vehicule.getPosition(), vehicule.toString(), vehicule.getType(), vehicule.getEtat());
        }
    }

    public void log(String msg) {
        System.out.println(msg);
    }
}
