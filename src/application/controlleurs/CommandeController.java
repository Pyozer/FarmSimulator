package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import application.modeles.ChampSQL;
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
public class CommandeController implements Initializable, APIGoogleMap {

    /**
     * Layout
     **/
    @FXML private BorderPane bpane;
    @FXML private TableView<Champ> tableView;
    @FXML private TableColumn<Champ, String> column_type_culture;
    @FXML private TableColumn<Champ, Agriculteur> column_proprietaire;

    @FXML private JFXButton delete_btn;
    @FXML private JFXButton edit_btn;

    @FXML private ListView<ElementPair> listInfos;

    private List<Champ> champList;
    private ChampSQL champSQL;
    private Champ selectedChamp = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        column_type_culture.setCellValueFactory(new PropertyValueFactory<>("type_culture"));
        column_proprietaire.setCellValueFactory(new PropertyValueFactory<>("proprietaire"));

        champSQL = new ChampSQL();
        champList = champSQL.getChampsList();

        tableView.getItems().addAll(champList);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsChamp(newvalue));

        listInfos.getItems().add(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));

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