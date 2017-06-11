package application.controlleurs.client;

import application.Constant;
import application.classes.*;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import application.modeles.ClientSQL;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class ClientController implements APIGoogleMap  {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
	@FXML private BorderPane infoContent;
	@FXML private VBox listInfo;

    @FXML private TableView<Agriculteur> tableView;
    @FXML private TableColumn<Agriculteur, String> column_nom;
    @FXML private TableColumn<Agriculteur, String> column_prenom;
	
	@FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;

    @FXML private ListView<ElementPair> listInfos;

    private GoogleMaps gMaps;
    private Agriculteur selectedAgri = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("client/maps_client_champ", this);
        gMaps.setParent(googleMaps);

        column_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        column_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsClient(newvalue));

        resetListInfo();
        initData();

        infoContent.setOnMouseClicked(event -> clearAllSelection());
    }

    private void showInformationsClient(Agriculteur agriculteur) {
        selectedAgri = null;

        if (agriculteur != null) {
            selectedAgri = agriculteur;

            defineStateElements(true);

			listInfos.getItems().setAll(agriculteur.getInformations());

			gMaps.removeAllChamps();
			for(Champ champ : ClientSQL.getClientsChampsList(agriculteur.getId()))
				gMaps.addChamp(champ);
		}
    }

    @FXML
    public void addClient() {
        SwitchView switchView = new SwitchView("client/edit_client_app", Constant.ADD_CLIENT_APP_TITLE);
        EditClientController editClientController = switchView.getFxmlLoader().getController();
        editClientController.defineClientController(this);
        switchView.showScene();
    }

    @FXML
    public void deleteClient() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppresion client");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Voulez-vous vraiment supprimer ce client ?\n" + selectedAgri.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ClientSQL.deleteClient(selectedAgri);
            tableView.getItems().remove(selectedAgri);
            clearAllSelection();
        } else {
            alert.close();
        }
    }

    @FXML
    public void editClient() {
        Agriculteur agriculteur = tableView.getSelectionModel().getSelectedItem();

        SwitchView switchView = new SwitchView("client/edit_client_app", Constant.ADD_VEHICULE_APP_TITLE);
        EditClientController editClientController = switchView.getFxmlLoader().getController();
        editClientController.setEditionMode(true);
        editClientController.initView(agriculteur);
        editClientController.defineClientController(this);
        switchView.showScene();
    }

    public void askToLoadChamps() {
        gMaps.removeAllChamps();
        for(Champ champ : ClientSQL.getClientsChampsList()) {
            gMaps.addChamp(champ);
        }
    }

    public void clearAllSelection() {
        tableView.getSelectionModel().clearSelection();
        resetListInfo();
        defineStateElements(false);
        askToLoadChamps();
    }

    private void resetListInfo() {
        listInfos.getItems().setAll(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));
    }

    public void initData() {
        tableView.getItems().setAll(ClientSQL.getClientsList());
    }

    private void defineStateElements(boolean state) {
        delete_btn.setVisible(state);
        delete_btn.setManaged(state);
        edit_btn.setVisible(state);
        edit_btn.setManaged(state);
        listInfo.setVisible(state);
        listInfo.setManaged(state);
    }

    public void log(String msg) {
        System.err.println(msg);
    }
}