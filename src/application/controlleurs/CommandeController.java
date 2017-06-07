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

        tableView.getItems().setAll(CommandeSQL.getCommandeList());
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsCommande(newvalue));

        infoContent.setOnMouseClicked(event -> clearAllSelection());
    }

    private void showInformationsCommande(Commande commande) {
        selectedCommande = null;

        if (commande != null) {
            selectedCommande = commande;
            delete_btn.setVisible(true);
            edit_btn.setVisible(true);
        }
    }

    @FXML
    public void addCommande() {
        SwitchView switchView = new SwitchView("add_commande_app", Constant.ADD_VEHICULE_APP_TITLE);
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
        editCommandeController.initTextFields(commandeSelected);
        switchViewData.showScene();
    }

    private void clearAllSelection() {
        selectedCommande = null;
        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        tableView.getSelectionModel().clearSelection();
    }
}