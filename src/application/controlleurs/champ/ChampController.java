package application.controlleurs.champ;

import application.Constant;
import application.classes.ElementPair;
import application.classes.GoogleMaps;
import application.classes.MenuApp;
import application.classes.SwitchView;
import application.controlleurs.CarteController;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import application.modeles.ChampSQL;
import application.modeles.Culture;
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

import java.util.Optional;

import static application.Constant.rechercherValeurListe;
import static application.Constant.setWidthColumn;

/**
 * Controlleur de la vue de la gestion des champs de l'Eta
 */
public class ChampController extends CarteController {

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

    @FXML private JFXTextField search_field;

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

        column_type_culture.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTypeCulture()));
        column_proprietaire.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProprietaire()));
        column_adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));

        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        setWidthColumn(column_type_culture, 20);
        setWidthColumn(column_proprietaire, 30);
        setWidthColumn(column_adresse, 50);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsChamp(newvalue));

        search_field.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.getItems().setAll(rechercherValeurListe(listChamps, search_field.getText()))
        );

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
            alert.setTitle("Suppression champ");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Voulez-vous vraiment supprimer ce champ ?\n" + selectedChamp.toString());
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 220);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                ChampSQL.deleteChamp(selectedChamp);
                initData();
                clearAllSelection();
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
        defineStateElements(false);
        askToLoadChamps();
    }

    public void askToLoadChamps() {
        gMaps.removeAllChamps();
        for (int i = 0;i < listChamps.size(); i++) {
            gMaps.addChamp(listChamps.get(i));
        }
    }

    public void selectByChamp(int id_champ, int id_proprio) {
        for(Champ champ : listChamps) {
            if(champ.getId() == id_champ) {
                tableView.getSelectionModel().select(champ);
                tableView.scrollTo(champ);
            }
        }
    }

    public void initData() {
        listChamps = ChampSQL.getChampsList();
        tableView.getItems().setAll(listChamps);
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