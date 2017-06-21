package application.controlleurs.commande;

import application.Constant;
import application.classes.MenuApp;
import application.classes.SwitchView;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static application.Constant.DATE_TIME_FORMAT;

/**
 * Controlleur de la vue de la gestion des affectations de l'Eta
 */
public class AffectationController {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private Label titleCommandeSelected;
	
    @FXML private TableView<Vehicule> tableView;
    @FXML private TableColumn<Vehicule, String> column_type;
    @FXML private TableColumn<Vehicule, String> column_vehicule;

    @FXML private TableView<Moisson> tableView_rapport;
    @FXML private TableColumn<Moisson, String> column_date;
    @FXML private TableColumn<Moisson, String> column_duree;
    @FXML private TableColumn<Moisson, String> column_poids;
    @FXML private TableColumn<Moisson, String> column_nb_km;

	@FXML private JFXButton delete_btn;
	@FXML private JFXButton add_rapport_btn;
    @FXML private JFXButton delete_rapport_btn;

    private Vehicule selectedVehicule = null;
    private Commande selectedCommande = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        column_type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        column_vehicule.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque() + " " + cellData.getValue().getModele()));

        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        column_type.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width
        column_vehicule.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> vehiculeSelected(newvalue));

        column_date.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            return new SimpleStringProperty(cellData.getValue().getDatetimeDebut().format(formatter));
        });
        column_duree.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuree() + " heure(s)"));
        column_poids.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNbTonne() + " tonne(s)"));
        column_nb_km.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNbKm() + " km(s)"));

        tableView_rapport.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        column_date.setMaxWidth( 1f * Integer.MAX_VALUE * 25 ); // 50% width
        column_duree.setMaxWidth( 1f * Integer.MAX_VALUE * 25 ); // 50% width
        column_poids.setMaxWidth( 1f * Integer.MAX_VALUE * 25 ); // 50% width
        column_nb_km.setMaxWidth( 1f * Integer.MAX_VALUE * 25 ); // 50% width

        bpane.setOnMouseClicked(event -> clearAllSelection());

    }

    private void vehiculeSelected(Vehicule vehicule) {
		selectedVehicule = null;
		
		if(vehicule != null) {
			selectedVehicule = vehicule;

            defineStateElements(true);
            defineStateDeleteRapport(MoissonSQL.isRapportExist(selectedCommande, selectedVehicule));

            fillTableViewRapport();
		}
    }

    @FXML
    public void addAffect() {
        SwitchView switchView = new SwitchView("commande/add_affectation_app", Constant.ADD_VEHICULE_APP_TITLE);
        switchView.setPopUp();
        AddAffectationController addAffectationController = switchView.getFxmlLoader().getController();
        addAffectationController.defineCommandeSelected(selectedCommande);
        addAffectationController.defineAffectController(this);
        switchView.showScene();
    }

    @FXML
    public void deleteAffect() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression affectation");
        alert.setHeaderText("Confirmation de suppression de l'affectation");
        alert.setContentText("Voulez-vous vraiment supprimer le véhicule\n" + selectedVehicule + "\npour la commande\n" + selectedCommande);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            AffectationSQL.deleteAffect(selectedCommande, selectedVehicule);
            tableView.getItems().remove(selectedVehicule);
            clearAllSelection();
        } else {
            alert.close();
        }
    }

    @FXML
    public void newRapport() {
        SwitchView switchView = new SwitchView("commande/edit_moisson_app", Constant.RAPPORT_MOISSON_APP_TITLE);
        switchView.setPopUp();
        EditMoissonController editMoissonController = switchView.getFxmlLoader().getController();

        boolean isRapportExist = MoissonSQL.isRapportExist(selectedCommande, selectedVehicule);
        editMoissonController.setEditionMode(isRapportExist);

        if(isRapportExist)
            editMoissonController.initView(MoissonSQL.getMoissonSelected(selectedCommande, selectedVehicule));

        editMoissonController.defineCommandeController(this);
        editMoissonController.defineVariableMoisson(selectedCommande, selectedVehicule);
        switchView.showScene();
    }

    @FXML
    public void deleteRapport() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression affectation");
        alert.setHeaderText("Confirmation de suppression du rapport de moisson");
        alert.setContentText("Voulez-vous vraiment supprimer le rapport de moisson de \n" + selectedVehicule + "\npour la commande\n" + selectedCommande);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            MoissonSQL.deleteMoisson(selectedVehicule, selectedCommande);
            defineStateDeleteRapport(false);
        } else {
            alert.close();
        }
    }

    public void defineCommandeSelected(Commande commande) {
        selectedCommande = commande;
        titleCommandeSelected.setText(selectedCommande.toString());
        tableView.getItems().setAll(AffectationSQL.getVehiculeAffect(selectedCommande));
    }

    public void defineVehiculeSelected(Vehicule vehicule) {
        selectedVehicule = vehicule;
        tableView.getSelectionModel().select(vehicule);
        fillTableViewRapport();
    }
	
	private void clearAllSelection() {
        defineStateElements(false);
        defineStateDeleteRapport(false);
        tableView.getSelectionModel().clearSelection();
        hideMoissonTable();
    }

    private void fillTableViewRapport() {
        Moisson moisson = MoissonSQL.getMoissonSelected(selectedCommande, selectedVehicule);
        if(moisson != null) {
            tableView_rapport.setManaged(true);
            tableView_rapport.setVisible(true);
            tableView_rapport.getItems().setAll(MoissonSQL.getMoissonSelected(selectedCommande, selectedVehicule));
        } else {
            hideMoissonTable();
        }
    }

    private void hideMoissonTable() {
        tableView_rapport.getSelectionModel().clearSelection();
        tableView_rapport.getItems().clear();
        tableView_rapport.setVisible(false);
        tableView_rapport.setManaged(false);
    }

    private void defineStateElements(boolean state) {
        delete_btn.setVisible(state);
        delete_btn.setManaged(state);
        add_rapport_btn.setVisible(state);
        add_rapport_btn.setManaged(state);
    }

    private void defineStateDeleteRapport(boolean state) {
        delete_rapport_btn.setVisible(state);
        delete_rapport_btn.setManaged(state);
    }


}