package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import java.util.Optional;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class ClientController implements APIGoogleMap  {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
	@FXML private BorderPane infoContent;
	
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

        gMaps = new GoogleMaps("maps_client", this);
        gMaps.setParent(googleMaps);

        column_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        column_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        tableView.getItems().addAll(ClientSQL.getClientsList());
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsClient(newvalue));

        resetListInfo();

        infoContent.setOnMouseClicked(event -> clearAllSelection());

    }

    private void showInformationsClient(Agriculteur agriculteur) {
        selectedAgri = null;

        if (agriculteur != null) {
            selectedAgri = agriculteur;
			
            delete_btn.setVisible(true);
            edit_btn.setVisible(true);

			listInfos.getItems().clear();
			for(ElementPair information : agriculteur.getInformations())
				listInfos.getItems().add(information);

			gMaps.removeAll();
			for(Champ champ : ClientSQL.getClientsChampsList(agriculteur.getId()))
				gMaps.addChamp(champ.getId(), champ.getType_culture(), champ.getProprietaire(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp());
		}
    }

    @FXML
    public void addClient() {
        SwitchView switchView = new SwitchView("add_client_app", Constant.ADD_CLIENT_APP_TITLE, bpane);
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
        } else {
            alert.close();
        }
    }

    @FXML
    public void editClient() {
        Agriculteur agriculteur = tableView.getSelectionModel().getSelectedItem();

        SwitchViewData switchViewData = new SwitchViewData("edit_client_app", Constant.ADD_VEHICULE_APP_TITLE, agriculteur);
        switchViewData.showScene();
    }

    public void askToLoadChamps() {
        gMaps.removeAll();
        for(Champ champ : ClientSQL.getClientsChampsList()) {
            gMaps.addChamp(champ.getId(), champ.getType_culture(), champ.getProprietaire(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp());
        }
    }

    private void clearAllSelection() {
        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        tableView.getSelectionModel().clearSelection();
        resetListInfo();
        askToLoadChamps();
    }

    private void resetListInfo() {
        listInfos.getItems().setAll(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));
    }

    public void log(String msg) {
        System.err.println(msg);
    }

}
