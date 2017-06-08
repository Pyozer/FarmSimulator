package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Controlleur de la vue de la gestion des véhicules de l'ETA
 */
public class VehiculeController implements APIGoogleMap {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
	@FXML private BorderPane infoContent;
	@FXML private VBox listInfo;

    @FXML private TableView<Vehicule> tableView;
    @FXML private TableColumn<Vehicule, String> column_type;
    @FXML private TableColumn<Vehicule, String> column_marque;
    @FXML private TableColumn<Vehicule, String> column_modele;
    @FXML private TableColumn<Vehicule, String> column_etat;
	
	@FXML private JFXButton edit_btn;
	@FXML private JFXButton delete_btn;

    @FXML private ListView<ElementPair> listInfos;
    private ObservableList<Vehicule> vehiculeList;

    private GoogleMaps gMaps;
    private Vehicule selectedVehicule = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("maps_vehicule", this);
        gMaps.setParent(googleMaps);

        column_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        column_marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        column_modele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        column_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsVehicule(newvalue));

        resetListInfo();
        initData();

        infoContent.setOnMouseClicked(event -> clearAllSelection());
    }

    private void showInformationsVehicule(Vehicule vehicule) {
		selectedVehicule = null;
		
		if(vehicule != null) {
			selectedVehicule = vehicule;
			
			defineStateElements(true);

			listInfos.getItems().clear();
			for(ElementPair information : vehicule.getInformations())
				listInfos.getItems().add(information);
			gMaps.hideAllExceptOne(vehicule.getId());
		}
    }

    @FXML
    public void addVehicule() {
        SwitchView switchView = new SwitchView("choix_vehicule_app", Constant.ADD_VEHICULE_APP_TITLE);
        ChoixVehiculeController choixVehiculeController = switchView.getFxmlLoader().getController();
        choixVehiculeController.defineVehiculeController(this);
        switchView.showScene();
    }

    @FXML
    public void deleteVehicule() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppresion véhicule");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Voulez-vous vraiment supprimer ce véhicule ?\n" + selectedVehicule.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            VehiculeSQL.deleteVehicule(selectedVehicule);
            tableView.getItems().remove(selectedVehicule);
        } else {
            alert.close();
        }
    }

    @FXML
    public void editVehicule() {
        Vehicule vehiculeSelected = tableView.getSelectionModel().getSelectedItem();

        SwitchView switchView;

        if (vehiculeSelected instanceof Botteleuse){
            switchView = new SwitchView("edit_botteleuse_app", Constant.ADD_VEHICULE_APP_TITLE);
            EditBotteleuseController editBotController = switchView.getFxmlLoader().getController();
            editBotController.setEditionMode(true);
            editBotController.initView((Botteleuse) vehiculeSelected);
            editBotController.defineVehiculeController(this);
            switchView.showScene();

        } else if (vehiculeSelected instanceof Moissonneuse){
            switchView = new SwitchView("edit_moissonneuse_app", Constant.ADD_VEHICULE_APP_TITLE);
            EditMoissonneuseController editMoiController = switchView.getFxmlLoader().getController();
            editMoiController.setEditionMode(true);
            editMoiController.initView((Moissonneuse) vehiculeSelected);
            editMoiController.defineVehiculeController(this);
            switchView.showScene();

        } else if (vehiculeSelected instanceof Tracteur) {
            switchView =new SwitchView("edit_tracteur_app", Constant.ADD_VEHICULE_APP_TITLE);
            EditTracteurController editTraController = switchView.getFxmlLoader().getController();
            editTraController.setEditionMode(true);
            editTraController.initView((Tracteur) vehiculeSelected);
            editTraController.defineVehiculeController(this);
            switchView.showScene();
        }
    }

    public void askToLoadMarkers() {
		gMaps.removeAll();
        for(Vehicule vehicule : vehiculeList) {
            gMaps.addMarker(vehicule.getId(), vehicule.getPosition(), vehicule.toString(), vehicule.getType(), vehicule.getEtat());
        }
    }
	
	private void clearAllSelection() {
        tableView.getSelectionModel().clearSelection();
        resetListInfo();
        defineStateElements(false);
        askToLoadMarkers();
    }

    private void resetListInfo() {
        listInfos.getItems().setAll(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));
    }

    public void initData() {
        vehiculeList = VehiculeSQL.getVehiculeList();

        tableView.getItems().setAll(vehiculeList);
    }

    private void defineStateElements(boolean state) {
        edit_btn.setVisible(state);
        edit_btn.setManaged(state);
        delete_btn.setVisible(state);
        delete_btn.setManaged(state);
        listInfo.setVisible(state);
        listInfo.setManaged(state);
    }

    public void log(String msg) {
        System.out.println(msg);
    }
}