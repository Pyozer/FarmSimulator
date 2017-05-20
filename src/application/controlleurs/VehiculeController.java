package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class VehiculeController implements Initializable, APIGoogleMap {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
    @FXML private TableView<Vehicule> tableView;
    @FXML private TableColumn<Vehicule, Integer> column_id;
    @FXML private TableColumn<Vehicule, String> column_type;
    @FXML private TableColumn<Vehicule, String> column_marque;
    @FXML private TableColumn<Vehicule, String> column_modele;
    @FXML private TableColumn<Vehicule, String> column_etat;

    @FXML private ListView<ElementPair> listInfos;

    private ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();
    private int idCount = 0;

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

        column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        column_marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        column_modele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        column_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        vehiculeList.add(new Botteleuse(idCount++, "Volvo", "V70", "Non utilisé", true));
        vehiculeList.add(new Botteleuse(idCount++, "Volvo", "850", "En cours d'utilisation", false));
        vehiculeList.add(new Tracteur(idCount++, "Renault", "Laguna", "En maintenance", 125));
        vehiculeList.add(new Botteleuse(idCount++, "Renault", "Laguna", "En maintenance", true));
        vehiculeList.add(new Moissonneuse(idCount++, "Porsche", "911 GT3 Cup", "En moisson", 25, 260, 25, 20, 1305, 20, 15, 10));
        vehiculeList.add(new Botteleuse(idCount++, "Renault", "Laguna", "En maintenance", true));
        vehiculeList.add(new Botteleuse(idCount++, "Renault", "Laguna", "En maintenance", true));
        vehiculeList.add(new Botteleuse(idCount++, "Renault", "Laguna", "En maintenance", true));
        vehiculeList.add(new Botteleuse(idCount++, "Renault", "Laguna", "En maintenance", true));

        tableView.getItems().addAll(vehiculeList);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsVehicule(newvalue));

        listInfos.getItems().add(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));

    }

    public void showInformationsVehicule(Vehicule vehicule) {
        listInfos.getItems().clear();
        for(ElementPair information : vehicule.getInformations())
            listInfos.getItems().add(information);
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

}
