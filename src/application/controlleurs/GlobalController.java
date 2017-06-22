package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.controlleurs.commande.AffectationController;
import application.modeles.*;
import application.properties.SettingsProperties;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

import static application.Constant.PROP_FIGHT_MODE_STATE;
import static application.Constant.PROP_FIGHT_MODE_STATE_DEF;
import static application.Constant.setWidthColumn;

/**
 * Controlleur de la vue Global
 */
public class GlobalController extends CarteController {

    @FXML private BorderPane bpane;
    @FXML private VBox infoContent;
    @FXML private StackPane googleMaps;

    @FXML private JFXTextField FChamp;
    @FXML private JFXTextField SChamp;

    @FXML private JFXButton btn_markDone;
    @FXML private JFXButton btn_affects;

    @FXML private JFXToggleButton toggleButton;

    @FXML private TableView<Commande> tableView;
    @FXML private TableColumn<Commande, LocalDate> column_date;
    @FXML private TableColumn<Commande, String> column_adresse;
    @FXML private TableColumn<Commande, String> column_transport;

    private GoogleMaps gMaps;

    private Commande commandeSelected;

    private Properties prop;

    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        gMaps = new GoogleMaps("global/maps_global", this);
        gMaps.setParent(googleMaps);

        column_date.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        column_adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChampCommande().getAdresse()));
        column_transport.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTransport()));

        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        setWidthColumn(column_date, 25); // 25% width
        setWidthColumn(column_adresse, 50); // 50% width
        setWidthColumn(column_transport, 25); // 25% width

        tableView.setRowFactory(row -> new TableRow<Commande>(){
            @Override
            public void updateItem(Commande item, boolean empty){
                super.updateItem(item, empty);

                if (item != null && !empty)
                    if (item.getDate().isBefore(LocalDate.now()))
                        for(Node child : getChildren())
                            child.setStyle("-fx-font-weight: bold;-fx-text-fill: RED;");
                    else
                        for(Node child : getChildren())
                            child.setStyle("-fx-font-weight: normal;-fx-text-fill: BLACK;");
            }
        });

        tableView.getItems().setAll(CommandeSQL.getCommandeMakedList(false));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showButtons(newvalue));

        infoContent.setOnMouseClicked(event -> clearAllSelection());

        prop = SettingsProperties.loadSettingsPropertiesFile();

        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> defineFlightStateOnMap(newValue));
        toggleButton.setSelected(Boolean.parseBoolean(prop.getProperty(PROP_FIGHT_MODE_STATE, PROP_FIGHT_MODE_STATE_DEF)));
    }

    private void defineFlightStateOnMap(boolean value) {
        if(value) {
            gMaps.enableFlightItinerary();
            prop.setProperty(PROP_FIGHT_MODE_STATE, "true");
        } else {
            gMaps.disableFlightItinerary();
            prop.setProperty(PROP_FIGHT_MODE_STATE, "false");
        }
        SettingsProperties.makeSettingsProperties(prop);
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
            gMaps.addChamp(champ);

        for(Vehicule vehicule : VehiculeSQL.getVehiculeList())
            gMaps.addMarker(vehicule.getId(), vehicule.getPosition(), vehicule.toString(), vehicule.getType(), vehicule.getEtat());

    }

    public boolean isToggleButtonSelected() {
        return toggleButton.isSelected();
    }

    @FXML
    public void goToCommandes() {
        SwitchView switchView = new SwitchView("commande/commande_app", Constant.COMMANDE_APP_TITLE, bpane);
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
        SwitchView switchViewData = new SwitchView("commande/affectations_app", Constant.AFFECTATION_APP_TITLE);
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

        if(tableView.getSelectionModel().getSelectedItem() != null) {

            tableView.getSelectionModel().clearSelection();

            defineStateElements(false);

            gMaps.removeAllChampsMarkers();
            askToLoadData();
        }
    }

    private void defineStateElements(boolean state) {
        btn_markDone.setVisible(state);
        btn_markDone.setManaged(state);
        btn_affects.setVisible(state);
        btn_affects.setManaged(state);
    }
}
