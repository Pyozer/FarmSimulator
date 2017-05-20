package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.Agriculteur;
import application.modeles.Champ;
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
    @FXML private TableColumn<Champ, Integer> column_id;
    @FXML private TableColumn<Champ, Integer> column_surface;
    @FXML private TableColumn<Champ, String> column_type_culture;
    @FXML private TableColumn<Champ, Agriculteur> column_proprietaire;
    @FXML private TableColumn<Champ, String> column_bottelage;
    @FXML private TableColumn<Champ, String> column_transport;

    private ObservableList<Champ> champList = FXCollections.observableArrayList();
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
        column_surface.setCellValueFactory(new PropertyValueFactory<>("surface"));
        column_type_culture.setCellValueFactory(new PropertyValueFactory<>("type_culture"));
        column_proprietaire.setCellValueFactory(new PropertyValueFactory<>("proprietaire"));
        column_bottelage.setCellValueFactory(new PropertyValueFactory<>("bottelage"));
        column_transport.setCellValueFactory(new PropertyValueFactory<>("transport"));

        Agriculteur client1 = new Agriculteur(1, "Robert", "Downey Jr", "0652555405", "23 rue saint-martin, 53000 LAVAL", "jean-charles.mousse.etu@univ-lemans.fr");
        Agriculteur client2 = new Agriculteur(2, "Chris", "Evans", "0652555405", "9 rue famille bizot, 72200 La Flèche", "jean-charles.mousse.etu@univ-lemans.fr");

        List<Point> coordChamp = new ArrayList<>();
        coordChamp.add(new Point(47.919129, -1.526379));
        coordChamp.add(new Point(47.919129, -1.526379));
        coordChamp.add(new Point(47.919129, -1.526379));

        champList.add(new Champ(idCount++, 666, new Point(47.919129, -1.526379), coordChamp, "Orge", client1, true, "ETA"));
        champList.add(new Champ(idCount++, 333, new Point(47.919129, -1.526379), coordChamp, "Blé", client2, true, "ETA"));
        champList.add(new Champ(idCount++, 500, new Point(47.919129, -1.526379), coordChamp, "Colza", client2, false, "Agriculteur"));
        champList.add(new Champ(idCount++, 1998, new Point(47.919129, -1.526379), coordChamp, "Orge", client1, false, "ETA"));
        champList.add(new Champ(idCount++, 111, new Point(47.919129, -1.526379), coordChamp, "Colza", client1, true, "ETA"));
        champList.add(new Champ(idCount++, 1305, new Point(47.919129, -1.526379), coordChamp, "Blé", client2, false, "ETA"));

        tableView.getItems().addAll(champList);


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
