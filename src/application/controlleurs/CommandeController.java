package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.List;
import java.util.Optional;


/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class CommandeController {

    /**
     * Layout
     **/
    @FXML private BorderPane bpane;
    @FXML private TableView<Commande> tableView;

    @FXML private TableColumn<Commande, String> column_date;
    @FXML private TableColumn<Commande, Agriculteur> column_client;
    @FXML private TableColumn<Commande, String> column_adr;
    @FXML private TableColumn<Commande, Double> column_surf;
    @FXML private TableColumn<Commande, String> column_transport;
    @FXML private TableColumn<Commande, String> column_type_bott;
    @FXML private TableColumn<Commande, String> column_taillemax;
    @FXML private TableColumn<Commande, String> column_tonn;
    @FXML private TableColumn<Commande, String> column_cout;

    @FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;

    private List<Commande> commandeList;
    private CommandeSQL commandeSQL;
    private Commande selectedCommande = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        column_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        //column_client.setCellValueFactory(new PropertyValueFactory<>("proprietaire"));
        //column_adr.setCellValueFactory(new PropertyValueFactory<>("date"));
        //column_surf.setCellValueFactory(new PropertyValueFactory<>("date"));
        column_transport.setCellValueFactory(new PropertyValueFactory<>("transport"));
        column_type_bott.setCellValueFactory(new PropertyValueFactory<>("typebott"));
        column_taillemax.setCellValueFactory(new PropertyValueFactory<>("taillemax"));
        column_tonn.setCellValueFactory(new PropertyValueFactory<>("tonne"));
        column_cout.setCellValueFactory(new PropertyValueFactory<>("cout"));

        commandeSQL = new CommandeSQL();
        commandeList = commandeSQL.getCommandeList();

        tableView.getItems().addAll(commandeList);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsCommande(newvalue));;

    }

    public void showInformationsCommande(Commande commande) {
        selectedCommande = null;

        if (commande != null) {
            selectedCommande = commande;
			
            delete_btn.setVisible(true);
            edit_btn.setVisible(true);
        }

    }

    @FXML
    public void addCommande() {
        SwitchView switchView = new SwitchView("add_champ_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void deleteCommande() {
        if (selectedCommande != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppresion champ");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Voulez-vous vraiment supprimer ce champ ?\n" + selectedCommande.toString());
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 220);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                commandeSQL.deleteCommande(selectedCommande);
                tableView.getItems().remove(selectedCommande);
            } else {
                alert.close();
            }
        }
    }

    @FXML
    public void editCommande() {
        SwitchView switchView = new SwitchView("add_champ_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

    private void clearAllSelection() {
        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        tableView.getSelectionModel().clearSelection();
    }

    public void log(String msg) {
        System.err.println(msg);
    }

}