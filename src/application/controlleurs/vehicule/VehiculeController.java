package application.controlleurs.vehicule;

import application.Constant;
import application.classes.ElementPair;
import application.classes.GoogleMaps;
import application.classes.MenuApp;
import application.classes.SwitchView;
import application.controlleurs.CarteController;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

import static application.Constant.rechercherValeurListe;
import static application.Constant.setWidthColumn;

/**
 * Controlleur de la vue de la gestion des véhicules de l'Eta
 */
public class VehiculeController extends CarteController {

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

	@FXML private JFXTextField search_field;

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

        gMaps = new GoogleMaps("vehicule/maps_vehicule", this);
        gMaps.setParent(googleMaps);

        column_type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        column_marque.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        column_modele.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        column_etat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtat()));

        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        setWidthColumn(column_type, 20); // 15% width
        setWidthColumn(column_marque, 30); // 35% width
        setWidthColumn(column_modele, 30); // 35% width
        setWidthColumn(column_etat, 20); // 15% width

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsVehicule(newvalue));

        search_field.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.getItems().setAll(rechercherValeurListe(vehiculeList, search_field.getText()))
        );

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
        SwitchView switchView = new SwitchView("vehicule/choix_vehicule_app", Constant.ADD_VEHICULE_APP_TITLE);
        switchView.setPopUp();
        ChoixVehiculeController choixVehiculeController = switchView.getFxmlLoader().getController();
        choixVehiculeController.defineVehiculeController(this);
        switchView.showScene();
    }

    @FXML
    public void deleteVehicule() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression véhicule");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Voulez-vous vraiment supprimer ce véhicule ?\n" + selectedVehicule.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            VehiculeSQL.deleteVehicule(selectedVehicule);
            tableView.getItems().remove(selectedVehicule);
            clearAllSelection();
        } else {
            alert.close();
        }
    }

    @FXML
    public void editVehicule() {
        Vehicule vehiculeSelected = tableView.getSelectionModel().getSelectedItem();

        SwitchView switchView;

        if (vehiculeSelected instanceof Botteleuse){
            switchView = new SwitchView("vehicule/edit_botteleuse_app", Constant.EDIT_BOTTELEUSE_APP_TITLE);
            switchView.setPopUp();
            EditBotteleuseController editBotController = switchView.getFxmlLoader().getController();
            editBotController.setEditionMode(true);
            editBotController.initView((Botteleuse) vehiculeSelected);
            editBotController.defineVehiculeController(this, null);
            switchView.showScene();

        } else if (vehiculeSelected instanceof Moissonneuse){
            switchView = new SwitchView("vehicule/edit_moissonneuse_app", Constant.EDIT_MOISSONNEUSE_APP_TITLE);
            switchView.setPopUp();
            EditMoissonneuseController editMoiController = switchView.getFxmlLoader().getController();
            editMoiController.setEditionMode(true);
            editMoiController.initView((Moissonneuse) vehiculeSelected);
            editMoiController.defineVehiculeController(this, null);
            switchView.showScene();

        } else if (vehiculeSelected instanceof Tracteur) {
            switchView = new SwitchView("vehicule/edit_tracteur_app", Constant.EDIT_TRACTEUR_APP_TITLE);
            switchView.setPopUp();
            EditTracteurController editTraController = switchView.getFxmlLoader().getController();
            editTraController.setEditionMode(true);
            editTraController.initView((Tracteur) vehiculeSelected);
            editTraController.defineVehiculeController(this, null);
            switchView.showScene();
        }
    }

    public void askToLoadMarkers() {
		gMaps.removeAllMarkers();
        for(Vehicule vehicule : vehiculeList) {
            gMaps.addMarker(vehicule.getId(), vehicule.getPosition(), vehicule.toString(), vehicule.getType(), vehicule.getEtat());
        }
    }

    public void selectVehiculeByID(int id) {
        for(Vehicule vehicule : vehiculeList)
            if(vehicule.getId() == id) {
                tableView.getSelectionModel().select(vehicule);
                tableView.scrollTo(vehicule);
            }

    }
	
	public void clearAllSelection() {
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

}