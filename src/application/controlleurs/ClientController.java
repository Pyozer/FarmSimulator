package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import application.modeles.ClientSQL;
import application.modeles.Vehicule;
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
public class ClientController implements Initializable, APIGoogleMap  {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
    @FXML private TableView<Agriculteur> tableView;
    @FXML private TableColumn<Agriculteur, String> column_nom;
    @FXML private TableColumn<Agriculteur, String> column_prenom;

    @FXML private ListView<ElementPair> listInfos;

    private GoogleMaps gMaps;
    private ClientSQL clientSQL;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("maps_client", this);
        gMaps.setParent(googleMaps);

        column_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        column_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        clientSQL = new ClientSQL();

        tableView.getItems().addAll(clientSQL.getClientsList());
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsClient(newvalue));

        listInfos.getItems().add(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));

    }

    private void showInformationsClient(Agriculteur agriculteur) {
        listInfos.getItems().clear();
        for(ElementPair information : agriculteur.getInformations())
            listInfos.getItems().add(information);
    }

    @FXML
    public void addClient() {
        SwitchView switchView = new SwitchView("add_client_app", Constant.ADD_CLIENT_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void deleteClient() {
        AlertDialog alert = new AlertDialog("Suppression", null, "Voulez vous vraiment supprimer ce client ?", Alert.AlertType.WARNING);
        alert.show();
    }

    @FXML
    public void editClient() {
        SwitchView switchView = new SwitchView("add_client_app", Constant.ADD_CLIENT_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void askToLoadChamps() {

        List<Champ> listClientChamp = clientSQL.getClientsChampsList();

        for(Champ champ : listClientChamp) {
            //System.out.println(champ.getId());
            //System.out.println(champ.getCoordChamp());
            gMaps.addChamp(champ.getId(), champ.getType_culture(), champ.getProprietaire(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp());
        }
    }

    public void log(String msg) {
        System.err.println(msg);
    }

}
