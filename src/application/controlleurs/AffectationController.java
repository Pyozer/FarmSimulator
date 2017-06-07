package application.controlleurs;

import application.Constant;
import application.classes.MenuApp;
import application.classes.SwitchView;
import application.modeles.AffectationSQL;
import application.modeles.Commande;
import application.modeles.Vehicule;
import application.modeles.VehiculeSQL;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.Optional;

/**
 * Controlleur de la vue de la gestion des affectations de l'ETA
 */
public class AffectationController {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private Label titleCommandeSelected;
	
    @FXML private TableView<Vehicule> tableView;
    @FXML private TableColumn<Vehicule, String> column_type;
    @FXML private TableColumn<Vehicule, String> column_vehicule;

	@FXML private JFXButton delete_btn;

    private Vehicule selectedVehicule = null;
    private Commande selectedCommande = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        column_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        column_vehicule.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque() + " " + cellData.getValue().getModele()));


        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> vehiculeSelected(newvalue));

        bpane.setOnMouseClicked(event -> clearAllSelection());
    }

    private void vehiculeSelected(Vehicule vehicule) {
		selectedVehicule = null;
		
		if(vehicule != null) {
			selectedVehicule = vehicule;

			delete_btn.setVisible(true);
		}
    }

    @FXML
    public void addAffect() {
        SwitchView switchView = new SwitchView("choix_vehicule_app", Constant.ADD_VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void deleteAffect() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppresion affectation");
        alert.setHeaderText("Confirmation de suppression de l'affectation");
        alert.setContentText("Voulez-vous vraiment supprimer le véhicule\n" + selectedVehicule + "\npour la commande\n" + selectedCommande);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            AffectationSQL.deleteAffect(selectedCommande, selectedVehicule);
            tableView.getItems().remove(selectedVehicule);
        } else {
            alert.close();
        }
    }

    public void defineCommandeSelected(Commande commande) {
        selectedCommande = commande;
        titleCommandeSelected.setText(selectedCommandve.toString());
        tableView.getItems().addAll(AffectationSQL.getVehiculeAffect(selectedCommande));
    }
	
	private void clearAllSelection() {
        delete_btn.setVisible(false);
        tableView.getSelectionModel().clearSelection();
    }

}