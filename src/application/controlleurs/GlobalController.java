package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.controlleurs.commande.AffectationController;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Controlleur de la vue Global
 */
public class GlobalController implements APIGoogleMap {

    @FXML private BorderPane bpane;
    @FXML private VBox infoContent;
    @FXML private StackPane googleMaps;

    @FXML private JFXTextField FChamp;
    @FXML private JFXTextField SChamp;

    @FXML private JFXButton btn_markDone;
    @FXML private JFXButton btn_affects;

    @FXML private TableView<Commande> tableView;
    @FXML private TableColumn<Commande, String> column_date;
    @FXML private TableColumn<Commande, String> column_adresse;
    @FXML private TableColumn<Commande, String> column_transport;
    @FXML private TableColumn<Commande, String> column_type_bott;

    private GoogleMaps gMaps;

    private Commande commandeSelected;

    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("global/maps_global", this);
        gMaps.setParent(googleMaps);

        column_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        column_adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChampCommande().getAdresse()));
        column_transport.setCellValueFactory(new PropertyValueFactory<>("transport"));
        column_type_bott.setCellValueFactory(new PropertyValueFactory<>("typebott"));

        tableView.getItems().addAll(CommandeSQL.getCommandeMakedList(false));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showButtons(newvalue));

        infoContent.setOnMouseClicked(event -> clearAllSelection());
    }

    public void setFirstChamp(String firstChamp) {
        FChamp.setText(firstChamp.replace("(", "").replace(")", ""));
        FChamp.requestFocus();
    }
    public void setSecondChamp(String secondChamp) {
        SChamp.setText(secondChamp.replace("(", "").replace(")", ""));
        SChamp.requestFocus();
    }
    public void calculIntineraire() {
        if (FChamp.getText().isEmpty() || SChamp.getText().isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Veuillez saisir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            gMaps.changeRoute(FChamp.getText(), SChamp.getText());
        }
    }

    public void askToLoadData() {
        gMaps.removeAllChampsMarkers();
        for (Champ champ : ChampSQL.getChampsList())
            gMaps.addChamp(champ.getId(), champ.getType_culture(), champ.getProprietaire(), champ.getAdresse(), champ.getSurface(), champ.getCoordChamp(), champ.getProprietaire().getCouleur());

        for(Vehicule vehicule : VehiculeSQL.getVehiculeList())
            gMaps.addMarker(vehicule.getId(), vehicule.getPosition(), vehicule.toString(), vehicule.getType(), vehicule.getEtat());
    }

    @FXML
    public void goToCommandes() {
        SwitchView switchView = new SwitchView("commande/commande_app", Constant.ACCUEIL_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void markToDone() {
        if(commandeSelected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation commande");
            alert.setHeaderText("Confirmation de validation");
            alert.setContentText("Voulez-vous vraiment marquer comme effectué la commande\n" + commandeSelected + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                commandeSelected.setEffectuer(true);
                CommandeSQL.editCommande(commandeSelected);
                tableView.getItems().remove(commandeSelected);
            } else {
                alert.close();
            }
        }
    }

    @FXML
    public void showAffects() {
        SwitchView switchViewData = new SwitchView("commande/affectations_app", Constant.ADD_VEHICULE_APP_TITLE);
        switchViewData.setPopUp();
        AffectationController affectationController = switchViewData.getFxmlLoader().getController();
        affectationController.defineCommandeSelected(commandeSelected);
        switchViewData.showScene();
    }

    private void showButtons(Commande commande) {
        commandeSelected = null;

        if(commande != null) {
            commandeSelected = commande;
            defineStateElements(true);
        }
    }

    private void clearAllSelection() {
        commandeSelected = null;

        tableView.getSelectionModel().clearSelection();

        defineStateElements(false);

        gMaps.removeAllChampsMarkers();
        askToLoadData();
    }

    private void defineStateElements(boolean state) {
        btn_markDone.setVisible(state);
        btn_markDone.setManaged(state);
        btn_affects.setVisible(state);
        btn_affects.setManaged(state);
    }

    public void log(String msg) {
        System.err.println(msg);
    }
}
