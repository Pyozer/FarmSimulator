package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Controlleur de la vue de la gestion des commandes de l'ETA
 */
public class CommandeController {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private VBox infoContent;
    @FXML private TableView<Commande> tableView;

    @FXML private TableColumn<Commande, LocalDate> column_date;
    @FXML private TableColumn<Commande, Agriculteur> column_client;
    @FXML private TableColumn<Commande, String> column_adr;
    @FXML private TableColumn<Commande, Float> column_surf;
    @FXML private TableColumn<Commande, String> column_transport;
    @FXML private TableColumn<Commande, String> column_type_bott;
    @FXML private TableColumn<Commande, Float> column_tonn_max;
    @FXML private TableColumn<Commande, Float> column_tonn;
    @FXML private TableColumn<Commande, Float> column_cout;

    @FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;
    @FXML private JFXButton affect_btn;

    private Commande selectedCommande = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        column_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        column_client.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getChampCommande().getProprietaire()));
        column_adr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChampCommande().getAdresse()));
        column_surf.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getChampCommande().getSurface()));
        column_transport.setCellValueFactory(new PropertyValueFactory<>("transport"));
        column_type_bott.setCellValueFactory(new PropertyValueFactory<>("typebott"));
        column_tonn_max.setCellValueFactory(new PropertyValueFactory<>("taillemax"));
        column_tonn.setCellValueFactory(new PropertyValueFactory<>("tonne"));
        column_cout.setCellValueFactory(new PropertyValueFactory<>("cout"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsCommande(newvalue));

        initData();

        infoContent.setOnMouseClicked(event -> clearAllSelection());
    }

    private void showInformationsCommande(Commande commande) {
        selectedCommande = null;

        if (commande != null) {
            selectedCommande = commande;
            defineStateElements(true);
        }
    }

    @FXML
    public void addCommande() {
        SwitchView switchView = new SwitchView("edit_commande_app", Constant.ADD_VEHICULE_APP_TITLE);
        EditCommandeController editCommandeController = switchView.getFxmlLoader().getController();
        editCommandeController.defineCommandeController(this);
        switchView.showScene();
    }

    @FXML
    public void deleteCommande() {
        if (selectedCommande != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppresion commande");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Voulez-vous vraiment supprimer cette commande ?\n" + selectedCommande.toString());
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 220);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                CommandeSQL.deleteCommande(selectedCommande);
                tableView.getItems().remove(selectedCommande);
            } else {
                alert.close();
            }
        }
    }

    @FXML
    public void editCommande() {
        Commande commandeSelected = tableView.getSelectionModel().getSelectedItem();

        SwitchView switchViewData = new SwitchView("edit_commande_app", Constant.ADD_VEHICULE_APP_TITLE);
        EditCommandeController editCommandeController = switchViewData.getFxmlLoader().getController();
        editCommandeController.setEditionMode(true);
        editCommandeController.initView(commandeSelected);
        editCommandeController.defineCommandeController(this);
        switchViewData.showScene();
    }

    @FXML
    public void showAffects() {
        SwitchView switchViewData = new SwitchView("affectations_app", Constant.ADD_VEHICULE_APP_TITLE);
        AffectationController affectationController = switchViewData.getFxmlLoader().getController();
        affectationController.defineCommandeSelected(selectedCommande);
        switchViewData.showScene();
    }

    public void initData() {
        tableView.getItems().setAll(CommandeSQL.getCommandeList());
    }

    private void clearAllSelection() {
        selectedCommande = null;

        tableView.getSelectionModel().clearSelection();
        defineStateElements(false);
    }

    private void defineStateElements(boolean state) {
        delete_btn.setVisible(state);
        delete_btn.setManaged(state);
        edit_btn.setVisible(state);
        edit_btn.setManaged(state);
        affect_btn.setVisible(state);
        affect_btn.setManaged(state);
    }
}