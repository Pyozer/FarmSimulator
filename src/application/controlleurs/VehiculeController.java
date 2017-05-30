package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class VehiculeController implements Initializable, APIGoogleMap {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
	@FXML private SplitPane splitPane;
	
    @FXML private TableView<Vehicule> tableView;
    @FXML private TableColumn<Vehicule, String> column_type;
    @FXML private TableColumn<Vehicule, String> column_marque;
    @FXML private TableColumn<Vehicule, String> column_modele;
    @FXML private TableColumn<Vehicule, String> column_etat;
	
	@FXML private JFXButton edit_btn;
	@FXML private JFXButton delete_btn;

    @FXML private ListView<ElementPair> listInfos;

    private GoogleMaps gMaps;
    private VehiculeSQL vehiculeSQL;
    private List<Vehicule> vehiculeList;
    private Vehicule selectedVehicule = null;

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

        vehiculeSQL = new VehiculeSQL();
        vehiculeList = vehiculeSQL.getVehiculeList();

        tableView.getItems().addAll(vehiculeList);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsVehicule(newvalue));

        listInfos.getItems().add(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));
		
		splitPane.setOnMouseClicked(event -> clearAllSelection());

    }

    public void showInformationsVehicule(Vehicule vehicule) {
		selectedVehicule = null;
		
		if(vehicule != null) {
			selectedVehicule = vehicule;
			
			edit_btn.setVisible(true);
			delete_btn.setVisible(true);

			listInfos.getItems().clear();
			for(ElementPair information : vehicule.getInformations())
				listInfos.getItems().add(information);
			gMaps.hideAllExceptOne(vehicule.getId());
		}
    }

    @FXML
    public void addVehicule() {
        SwitchView switchView = new SwitchView("choix_vehicule_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void deleteVehicule() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppresion véhicule");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Voulez-vous vraiment supprimer ce véhicule ?\n" + selectedVehicule.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            vehiculeSQL.deleteVehicule(selectedVehicule);
            tableView.getItems().remove(selectedVehicule);
        } else {
            alert.close();
        }
    }

    @FXML
    public void editVehicule() {
        SwitchView switchView = new SwitchView("add_tracteur_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.setSelectedVehicule(selectedVehicule);
        System.out.println(switchView.toString());
        switchView.showScene();
    }

    public void askToLoadMarkers() {
		gMaps.removeAll();
        for(Vehicule vehicule : vehiculeList) {
            gMaps.addMarker(vehicule.getId(), vehicule.getPosition(), vehicule.toString(), vehicule.getType(), vehicule.getEtat());
        }
    }
	
	private void clearAllSelection() {
        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        tableView.getSelectionModel().clearSelection();
        listInfos.getSelectionModel().clearSelection();
        askToLoadMarkers();
    }

    public void log(String msg) {
        System.out.println(msg);
    }
}
