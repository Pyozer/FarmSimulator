package application.controlleurs.client;

import application.Constant;
import application.classes.*;
import application.controlleurs.CarteController;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import application.modeles.ClientSQL;
import application.modeles.EtaSettings;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Optional;
import static application.Constant.rechercherValeurListe;

/**
 * Controlleur de la vue de la gestion des clients de l'Eta
 */
public class ClientController extends CarteController {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
	@FXML private BorderPane infoContent;
	@FXML private VBox listInfo;

	@FXML private TableView<Agriculteur> tableView;
    @FXML private TableColumn<Agriculteur, String> column_nom;
    @FXML private TableColumn<Agriculteur, String> column_prenom;
    @FXML private TableColumn<Agriculteur, Shape> column_color;

	@FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;

    @FXML private JFXTextField search_field;

    @FXML private ListView<ElementPair> listInfos;

    private GoogleMaps gMaps;
    private Agriculteur selectedAgri = null;
    private ObservableList<Agriculteur> listClient;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("client/maps_client_champ", this);
        gMaps.setParent(googleMaps);

        column_nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        column_prenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        column_color.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new Rectangle(42, 20, cellData.getValue().getCouleur())));

        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        column_nom.setMaxWidth( 1f * Integer.MAX_VALUE * 43 ); // 50% width
        column_prenom.setMaxWidth( 1f * Integer.MAX_VALUE * 43 ); // 50% width
        column_color.setMaxWidth( 1f * Integer.MAX_VALUE * 14 ); // 50% width

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsClient(newvalue));
        tableView.getStyleClass().add("custom-table-view");

        listInfos.getStyleClass().add("custom-list-view");

        search_field.textProperty().addListener((observable, oldValue, newValue) ->
            tableView.getItems().setAll(rechercherValeurListe(listClient, search_field.getText()))
        );

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
        switchView.setPopUp();
        EditClientController editClientController = switchView.getFxmlLoader().getController();
        editClientController.defineClientController(this);
        switchView.showScene();
    }

    @FXML
    public void deleteClient() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression client");
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

        SwitchView switchView = new SwitchView("client/edit_client_app", Constant.EDIT_CLIENT_APP_TITLE);
        switchView.setPopUp();
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
        listInfos.getItems().clear();
    }

    public void initData() {
        listClient = ClientSQL.getClientsList();
        tableView.getItems().setAll(listClient);
    }

    public void selectByChamp(int id_champ, int id_proprio) {
        for(Agriculteur agriculteur : listClient) {
            if (agriculteur.getId() == id_proprio) {
                tableView.getSelectionModel().select(agriculteur);
                tableView.scrollTo(agriculteur);
            }
        }
    }

    private void defineStateElements(boolean state) {
        delete_btn.setVisible(state);
        delete_btn.setManaged(state);
        edit_btn.setVisible(state);
        edit_btn.setManaged(state);
        listInfo.setVisible(state);
        listInfo.setManaged(state);
    }

}