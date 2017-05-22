package application.controlleurs;

import application.Constant;
import application.classes.*;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import application.modeles.Agriculteur;
import application.modeles.Vehicule;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class ClientController implements Initializable, APIGoogleMap  {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;
    @FXML private TableView<Agriculteur> tableView;
    @FXML private TableColumn<Agriculteur, String> column_nom;
    @FXML private TableColumn<Agriculteur, String> column_prenom;

    @FXML private ListView<ElementPair> listInfos;

    private ObservableList<Agriculteur> clientList = FXCollections.observableArrayList();
    private int idCount = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        MenuApp menuApp = new MenuApp(bpane);
        bpane.setTop(menuApp.getMenuBar());

        GoogleMaps gMaps = new GoogleMaps("maps_client", this);
        gMaps.setParent(googleMaps);

        column_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        column_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        Connection dbCon = new DBConnection().getConnection();
        String request = "SELECT * FROM Agriculteur";
        try {
            NamedParameterStatement stmt = new NamedParameterStatement(dbCon, request);
            // execute select SQL stetement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clientList.add(new Agriculteur(
                            Integer.parseInt(rs.getString("id_agri")),
                            rs.getString("prenom_agri"),
                            rs.getString("nom_agri"),
                            rs.getString("tel_agri"),
                            rs.getString("adr_agri"),
                            rs.getString("email_agri")
                ));
            }
            dbCon.close();
            stmt.close();
            rs.close();
        }
        catch (SQLException ex) {
            System.err.println(ex);
        }
/*
        clientList.add(new Agriculteur(idCount++, "Robert", "Downey Jr", "0652555405", "23 rue saint-martin, 53000 LAVAL", "jean-charles.mousse.etu@univ-lemans.fr"));
        clientList.add(new Agriculteur(idCount++, "Robert", "Downey Jr", "0652555405", "23 rue saint-martin, 53000 LAVAL", "jean-charles.mousse.etu@univ-lemans.fr"));
        clientList.add(new Agriculteur(idCount++, "Robert", "Downey Jr", "0652555405", "23 rue saint-martin, 53000 LAVAL", "jean-charles.mousse.etu@univ-lemans.fr"));
        clientList.add(new Agriculteur(idCount++, "Robert", "Downey Jr", "0652555405", "23 rue saint-martin, 53000 LAVAL", "jean-charles.mousse.etu@univ-lemans.fr"));
        clientList.add(new Agriculteur(idCount++, "Chris", "Evans", "0652555405", "9 rue famille bizot, 72200 La Flèche", "jean-charles.mousse.etu@univ-lemans.fr"));
        clientList.add(new Agriculteur(idCount++, "Chris", "Evans", "0652555405", "9 rue famille bizot, 72200 La Flèche", "jean-charles.mousse.etu@univ-lemans.fr"));
        clientList.add(new Agriculteur(idCount++, "Chris", "Evans", "0652555405", "9 rue famille bizot, 72200 La Flèche", "jean-charles.mousse.etu@univ-lemans.fr"));
        clientList.add(new Agriculteur(idCount++, "Chris", "Evans", "0652555405", "9 rue famille bizot, 72200 La Flèche", "jean-charles.mousse.etu@univ-lemans.fr"));
        clientList.add(new Agriculteur(idCount++, "Chris", "Evans", "0652555405", "9 rue famille bizot, 72200 La Flèche", "jean-charles.mousse.etu@univ-lemans.fr"));
*/
        tableView.getItems().addAll(clientList);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> showInformationsClient(newvalue));

        listInfos.getItems().add(new ElementPair("Aucune information", "Selectionnez un élément du tableau"));

    }

    public void showInformationsClient(Agriculteur agriculteur) {
        listInfos.getItems().clear();
        for(ElementPair information : agriculteur.getInformations())
            listInfos.getItems().add(information);
    }

    @FXML
    public void addClient() {
        SwitchView switchView = new SwitchView("add_client_app", Constant.ADD_CLIENT_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void deleteClient() {
        AlertDialog alert = new AlertDialog("Suppression", null, "Voulez vous vraiment supprimer ce client ?", Alert.AlertType.WARNING);
        alert.show();
    }

    @FXML
    public void editClient() {
        SwitchView switchView = new SwitchView("add_client_app", Constant.ADD_CLIENT_APP_TITLE, bpane);
        switchView.showScene();
    }

}
