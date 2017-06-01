package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import application.modeles.ChampSQL;
import application.modeles.Vehicule;
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
public class ChampController implements APIGoogleMap {

    /**
     * Layout
     **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
    @FXML private BorderPane infoContent;
    @FXML private TableView<Champ> tableView;
    @FXML private TableColumn<Champ, String> column_type_culture;
    @FXML private TableColumn<Champ, Agriculteur> column_proprietaire;

    @FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;

    @FXML private ListView<ElementPair> listInfos;

    private List<Champ> champList;
    private GoogleMaps gMaps;
    private ChampSQL champSQL;
    private Champ selectedChamp = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("maps_champ", this);
        gMaps.setParent(googleMaps);

        column_type_culture.setCellValueFactory(new PropertyValueFactory<>("type_culture"));
        column_proprietaire.setCellValueFactory(new PropertyValueFactory<>("proprietaire"));

        champSQL = new ChampSQL();
        champList = champSQL.getChampsList();

        tableView.getItems().addAll(champList);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsChamp(newvalue));

        resetListInfo();

        infoContent.setOnMouseClicked(event -> clearAllSelection());

    }

    public void showInformationsChamp(Champ champ) {
        selectedChamp = null;

        if (champ != null) {
            selectedChamp = champ;
			
            delete_btn.setVisible(true);
            edit_btn.setVisible(true);
			
            listInfos.getItems().clear();
            for (ElementPair information : champ.getInformations())
                listInfos.getItems().add(information);

            gMaps.removeAll();
            gMaps.addChamp(champ.getId(), champ.getType_culture(), champ.getProprietaire(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp());
        }

    }

    @FXML
    public void addChamp() {
        SwitchView switchView = new SwitchView("add_champ_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
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
                champSQL.deleteChamp(selectedChamp);
                tableView.getItems().remove(selectedChamp);
                askToLoadChamps();
            } else {
                alert.close();
            }
        }
    }

    @FXML
    public void editChamp() {
        Champ champSelected = tableView.getSelectionModel().getSelectedItem();
        SwitchViewData switchViewData = new SwitchViewData("edit_champ_app", Constant.ADD_VEHICULE_APP_TITLE, champSelected);
        switchViewData.showScene();
    }

    private void clearAllSelection() {
        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        tableView.getSelectionModel().clearSelection();
        resetListInfo();
        askToLoadChamps();
    }

    public void askToLoadChamps() {
        gMaps.removeAll();
        for (Champ champ : champList) {
            gMaps.addChamp(champ.getId(), champ.getType_culture(), champ.getProprietaire(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp());
        }
    }

    private void resetListInfo() {
        listInfos.getItems().setAll(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));
    }

    public void log(String msg) {
        System.err.println(msg);
    }

}