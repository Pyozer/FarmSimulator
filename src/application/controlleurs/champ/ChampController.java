package application.controlleurs.champ;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Controlleur de la vue de la gestion des champs de l'Eta
 */
public class ChampController implements APIGoogleMap {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
    @FXML private BorderPane infoContent;
    @FXML private VBox listInfo;

    @FXML private TableView<Champ> tableView;
    @FXML private TableColumn<Champ, Culture> column_type_culture;
    @FXML private TableColumn<Champ, Agriculteur> column_proprietaire;
    @FXML private TableColumn<Champ, String> column_adresse;

    @FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;

    @FXML private ListView<ElementPair> listInfos;

    private GoogleMaps gMaps;
    private Champ selectedChamp = null;
    private ObservableList<Champ> listChamps;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("client/maps_client_champ", this);
        gMaps.setParent(googleMaps);

        column_type_culture.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType_culture()));
        column_proprietaire.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProprietaire()));
        column_adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));

        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        column_type_culture.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width
        column_proprietaire.setMaxWidth( 1f * Integer.MAX_VALUE * 30 ); // 30% width
        column_adresse.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsChamp(newvalue));

        resetListInfo();
        initData();

        infoContent.setOnMouseClicked(event -> clearAllSelection());
    }

    private void showInformationsChamp(Champ champ) {
        selectedChamp = null;

        if (champ != null) {
            selectedChamp = champ;

            defineStateElements(true);
			
            listInfos.getItems().clear();
            for (ElementPair information : champ.getInformations())
                listInfos.getItems().add(information);

            gMaps.removeAllChamps();
            gMaps.addChamp(champ);
        }
    }

    @FXML
    public void addChamp() {
        SwitchView switchView = new SwitchView("champ/edit_champ_app", Constant.EDIT_CHAMP_APP_TITLE);
        switchView.setPopUp();
        EditChampController editChampController = switchView.getFxmlLoader().getController();
        editChampController.defineChampController(this);
        switchView.showScene();
    }

    @FXML
    public void deleteChamp() {
        if (selectedChamp != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppresion champ");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Voulez-vous vraiment supprimer ce champ ?\n" + selectedChamp.toString());
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 220);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                ChampSQL.deleteChamp(selectedChamp);
                tableView.getItems().remove(selectedChamp);
                clearAllSelection();
                askToLoadChamps();
            } else {
                alert.close();
            }
        }
    }

    @FXML
    public void editChamp() {
        Champ champ = tableView.getSelectionModel().getSelectedItem();

        SwitchView switchView = new SwitchView("champ/edit_champ_app", Constant.ADD_CHAMP_APP_TITLE);
        switchView.setPopUp();
        EditChampController editChampController = switchView.getFxmlLoader().getController();
        editChampController.setEditionMode(true);
        editChampController.initView(champ);
        editChampController.defineChampController(this);
        switchView.showScene();
    }

    public void clearAllSelection() {
        tableView.getSelectionModel().clearSelection();
        resetListInfo();
        defineStateElements(false);
        askToLoadChamps();
    }

    public void askToLoadChamps() {
        gMaps.removeAllChamps();
        for (Champ champ : listChamps) {
            gMaps.addChamp(champ);
        }
    }

    public double getPosEtaX() {
        return EtaSettings.getInfosEta().getPosition().getX();
    }
    public double getPosEtaY() {
        return EtaSettings.getInfosEta().getPosition().getY();
    }

    public void selectByChamp(int id) {
        for(Champ champ : listChamps) {
            if(champ.getProprietaire().getId() == id) {
                tableView.getSelectionModel().select(champ);
                tableView.scrollTo(champ);
            }
        }
    }

    public void initData() {
        listChamps = ChampSQL.getChampsList();
        tableView.getItems().setAll(listChamps);
    }

    private void resetListInfo() {
        listInfos.getItems().setAll(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));
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
        System.err.println(msg);
    }
}