package application.modeles;

import application.classes.ConvertColor;
import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Pyozer on 23/05/2017.
 *
 */
public class ClientSQL {

    public static ObservableList<Agriculteur> getClientsList() {
        String request = "SELECT * FROM Agriculteur";

        ObservableList<Agriculteur> clientList = FXCollections.observableArrayList();
        try {
            PreparedStatement getClientsStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = getClientsStatement.executeQuery();

            while (rs.next()) {
                clientList.add(new Agriculteur(
                        rs.getInt("id_agri"),
                        rs.getString("prenom_agri"),
                        rs.getString("nom_agri"),
                        rs.getString("tel_agri"),
                        rs.getString("adr_agri"),
                        rs.getString("email_agri"),
                        ConvertColor.webToColorFX(rs.getString("couleur_agri"))
                ));
            }

            rs.close();
            getClientsStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return clientList;
    }

    public static ObservableList<Champ> getClientsChampsList() {
        return getClientsChampsList(-1);
    }

    public static ObservableList<Champ> getClientsChampsList(int id_agri) {
        String request = "SELECT * FROM Agriculteur " +
                "INNER JOIN Champ ON Agriculteur.id_agri=Champ.id_agri " +
                "INNER JOIN Culture ON Champ.type_champ=Culture.id_cul";

        if(id_agri > 0)
            request += " WHERE Agriculteur.id_agri=" + id_agri;

        ObservableList<Champ> clientChampList = FXCollections.observableArrayList();
        try {
            PreparedStatement getClientChampStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = getClientChampStatement.executeQuery();

            while (rs.next()) {
                Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));

                Polygon coord_champ = JSONManager.readPolygon(rs.getString("coords_champ"));

                clientChampList.add(new Champ(
                        rs.getInt("id_champ"),
                        rs.getFloat("surf_champ"),
                        rs.getString("adr_champ"),
                        coord_center,
                        coord_champ,
                        new Culture(
                                rs.getInt("id_cul"),
                                rs.getString("type_cul")
                        ),
                        new Agriculteur(
                                rs.getInt("id_agri"),
                                rs.getString("nom_agri"),
                                rs.getString("prenom_agri"),
                                rs.getString("tel_agri"),
                                rs.getString("adr_agri"),
                                rs.getString("email_agri"),
                                ConvertColor.webToColorFX(rs.getString("couleur_agri")))
                ));
            }

            rs.close();
            getClientChampStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return clientChampList;
    }

    public static void addClient(String inputNom, String inputPrenom, String inputTel, String inputAdresse, String inputEmail, Color inputCouleur) {
        String request = "INSERT INTO Agriculteur(nom_agri, prenom_agri, adr_agri, tel_agri, email_agri, couleur_agri) " +
                "VALUES(:nom, :prenom, :adresse, :tel, :email, :couleur)";

        try {
            NamedParameterStatement addClientStratement = new NamedParameterStatement(DBConnection.getConnection(), request);

            addClientStratement.setString("nom", inputNom);
            addClientStratement.setString("prenom", inputPrenom);
            addClientStratement.setString("tel", inputTel);
            addClientStratement.setString("adresse", inputAdresse);
            addClientStratement.setString("email", inputEmail);
            addClientStratement.setString("couleur", ConvertColor.ColorFXToWeb(inputCouleur));

            // Execute SQL statement
            addClientStratement.executeUpdate();

            addClientStratement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void editClient(Agriculteur agri) {
        String request = "UPDATE Agriculteur SET nom_agri=:nom_agri, prenom_agri=:prenom_agri, adr_agri=:adr_agri, tel_agri=:tel_agri, email_agri=:email_agri , couleur_agri=:couleur_agri " +
                "WHERE id_agri=:id_agri";

        try {
            NamedParameterStatement editClientStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            editClientStatement.setString("nom_agri", agri.getNom());
            editClientStatement.setString("prenom_agri", agri.getPrenom());
            editClientStatement.setString("adr_agri", agri.getAdresse());
            editClientStatement.setString("tel_agri", agri.getNum_tel());
            editClientStatement.setString("email_agri", agri.getEmail());
            editClientStatement.setString("couleur_agri", ConvertColor.ColorFXToWeb(agri.getCouleur()));
            editClientStatement.setInt("id_agri", agri.getId());

            // Execute SQL statement
            editClientStatement.executeUpdate();

            editClientStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void deleteClient(Agriculteur agriculteur) {
        String request = "DELETE FROM Agriculteur WHERE id_agri=:id";

        try {
            NamedParameterStatement deleteClient = new NamedParameterStatement(DBConnection.getConnection(), request);
            deleteClient.setInt("id", agriculteur.getId());
            deleteClient.executeUpdate();

            deleteClient.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
