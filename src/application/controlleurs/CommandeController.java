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
    @FXML private TableColumn<Commande, String> column_type_culture;
    @FXML private TableColumn<Commande, Agriculteur> column_proprietaire;

    @FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;

    @FXML private ListView<ElementPair> listInfos;

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

        column_type_culture.setCellValueFactory(new PropertyValueFactory<>("type_culture"));
        column_proprietaire.setCellValueFactory(new PropertyValueFactory<>("proprietaire"));

        commandeSQL = new CommandeSQL();
        commandeList = commandeSQL.getCommandeList();

        tableView.getItems().addAll(commandeList);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsCommande(newvalue));

        listInfos.getItems().add(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));

    }

    public void showInformationsCommande(Commande commande) {
        selectedCommande = null;

        if (commande != null) {
            selectedCommande = commande;
			
            delete_btn.setVisible(true);
            edit_btn.setVisible(true);
			
            listInfos.getItems().clear();
            for (ElementPair information : commande.getInformations())
                listInfos.getItems().add(information);
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
    public void editChamp() {
        SwitchView switchView = new SwitchView("add_champ_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

    private void clearAllSelection() {
        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        tableView.getSelectionModel().clearSelection();
        listInfos.getSelectionModel().clearSelection();
    }

    public void log(String msg) {
        System.err.println(msg);
    }

}