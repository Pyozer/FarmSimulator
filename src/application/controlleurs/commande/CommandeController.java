package application.controlleurs.commande;

import application.Constant;
import application.classes.MenuApp;
import application.classes.SwitchView;
import application.modeles.Agriculteur;
import application.modeles.Commande;
import application.modeles.CommandeSQL;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Controlleur de la vue de la gestion des commandes de l'Eta
 */
public class CommandeController {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private VBox infoContent;
    /** TableView Commande à faire **/
    @FXML private TableView<Commande> tableView_todo;

    @FXML private TableColumn<Commande, LocalDate> column_date_todo;
    @FXML private TableColumn<Commande, Agriculteur> column_client_todo;
    @FXML private TableColumn<Commande, String> column_adr_todo;
    @FXML private TableColumn<Commande, Float> column_surf_todo;
    @FXML private TableColumn<Commande, String> column_transport_todo;
    @FXML private TableColumn<Commande, String> column_type_bott_todo;
    @FXML private TableColumn<Commande, Float> column_tonn_max_todo;

    /** TableView Commande faites **/
    @FXML private TableView<Commande> tableView_make;

    @FXML private TableColumn<Commande, LocalDate> column_date_make;
    @FXML private TableColumn<Commande, Agriculteur> column_client_make;
    @FXML private TableColumn<Commande, String> column_adr_make;
    @FXML private TableColumn<Commande, Float> column_surf_make;
    @FXML private TableColumn<Commande, String> column_transport_make;
    @FXML private TableColumn<Commande, String> column_type_bott_make;
    @FXML private TableColumn<Commande, Float> column_tonn_max_make;
    @FXML private TableColumn<Commande, Float> column_tonn_make;
    @FXML private TableColumn<Commande, Float> column_cout_make;

    @FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;
    @FXML private JFXButton markToDone_btn;
    @FXML private JFXButton affect_btn;

    private Commande selectedCommande = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        // TableView commande à faire
        column_date_todo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        column_client_todo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getChampCommande().getProprietaire()));
        column_adr_todo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChampCommande().getAdresse()));
        column_surf_todo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getChampCommande().getSurface()));
        column_transport_todo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTransport()));
        column_type_bott_todo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypebott()));
        column_tonn_max_todo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTaillemax()));

        tableView_todo.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        column_date_todo.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 10% width
        column_client_todo.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 15% width
        column_adr_todo.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width
        column_surf_todo.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 10% width
        column_transport_todo.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 15% width
        column_type_bott_todo.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 15% width
        column_tonn_max_todo.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 15% width

        // TableView commande faites
        column_date_make.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        column_client_make.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getChampCommande().getProprietaire()));
        column_adr_make.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChampCommande().getAdresse()));
        column_surf_make.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getChampCommande().getSurface()));
        column_transport_make.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTransport()));
        column_type_bott_make.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypebott()));
        column_tonn_max_make.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTaillemax()));
        column_tonn_make.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTonne()));
        column_cout_make.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCout()));

        tableView_make.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        column_date_make.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 10% width
        column_client_make.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 15% width
        column_adr_make.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // 20% width
        column_surf_make.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 10% width
        column_transport_make.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 15% width
        column_type_bott_make.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 15% width
        column_tonn_max_make.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 15% width
        column_tonn_make.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 15% width
        column_cout_make.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 15% width

        tableView_todo.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsCommande(newvalue));

        tableView_make.setSelectionModel(null);
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
        SwitchView switchView = new SwitchView("commande/edit_commande_app", Constant.ADD_COMMANDE_APP_TITLE);
        switchView.setPopUp();
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
                tableView_todo.getItems().remove(selectedCommande);
                clearAllSelection();
            } else {
                alert.close();
            }
        }
    }

    @FXML
    public void editCommande() {
        Commande commandeSelected = tableView_todo.getSelectionModel().getSelectedItem();

        SwitchView switchView = new SwitchView("commande/edit_commande_app", Constant.EDIT_COMMANDE_APP_TITLE);
        switchView.setPopUp();
        EditCommandeController editCommandeController = switchView.getFxmlLoader().getController();
        editCommandeController.setEditionMode(true);
        editCommandeController.initView(commandeSelected);
        editCommandeController.defineCommandeController(this);
        switchView.showScene();
    }

    @FXML
    public void markToDone() {
        if(selectedCommande != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation commande");
            alert.setHeaderText("Confirmation de validation");
            alert.setContentText("Voulez-vous vraiment marquer comme effectué la commande\n" + selectedCommande + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                selectedCommande.setEffectuer(true);
                CommandeSQL.editCommande(selectedCommande);
                tableView_todo.getItems().remove(selectedCommande);
                tableView_make.getItems().add(selectedCommande);
            } else {
                alert.close();
            }
        }
    }

    @FXML
    public void showAffects() {
        SwitchView switchViewData = new SwitchView("commande/affectations_app", Constant.ADD_AFFECTATION_APP_TITLE);
        switchViewData.setPopUp();
        AffectationController affectationController = switchViewData.getFxmlLoader().getController();
        affectationController.defineCommandeSelected(selectedCommande);
        switchViewData.showScene();
    }

    public void initData() {
        tableView_todo.getItems().setAll(CommandeSQL.getCommandeMakedList(false));
        tableView_make.getItems().setAll(CommandeSQL.getCommandeMakedList(true));
    }

    private void clearAllSelection() {
        selectedCommande = null;

        tableView_todo.getSelectionModel().clearSelection();
        defineStateElements(false);
    }

    private void defineStateElements(boolean state) {
        delete_btn.setVisible(state);
        delete_btn.setManaged(state);
        edit_btn.setVisible(state);
        edit_btn.setManaged(state);
        markToDone_btn.setVisible(state);
        markToDone_btn.setManaged(state);
        affect_btn.setVisible(state);
        affect_btn.setManaged(state);
    }
}