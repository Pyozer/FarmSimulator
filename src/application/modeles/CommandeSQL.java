package application.modeles;

import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeSQL {

    private ObservableList<Commande> commandeList;
    private Connection dbCon;

    public CommandeSQL() {
        commandeList = FXCollections.observableArrayList();
        dbCon = new DBConnection().getConnection();
    }

    public ObservableList<Commande> getCommandeList() {
        String request = "SELECT * FROM Commande INNER JOIN Champ ON Champ.id_champ=Commande.id_champ";

        commandeList.clear();
        try {
            PreparedStatement preparedStatement = dbCon.prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();
            Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));

            Polygon coord_champ = new Polygon(JSONManager.readPolygon(rs.getString("coords_champ")));

            while (rs.next()) {
               commandeList.add(new Commande(
                        Integer.parseInt(rs.getString("id_com")),
                        rs.getString("transp_com"),
                        rs.getString("bott_com"),
                        rs.getString("taille_max_tranps_com"),
                        rs.getString("date_com"),
                        Integer.parseInt(rs.getString("tonne_com")),
                        Integer.parseInt(rs.getString("cout_com")),
                        new Champ(
                                Integer.parseInt(rs.getString("id_agri")),
                                Integer.parseInt(rs.getString("surf_champ")),
                                rs.getString("adr_champ"),
                                coord_center,
                                coord_champ,
                                rs.getString("type_champ"),
                                new Agriculteur(
                                            Integer.parseInt(rs.getString("id_agri")),
                                            rs.getString("prenom_agri"),
                                            rs.getString("nom_agri"),
                                            rs.getString("tel_agri"),
                                            rs.getString("adr_agri"),
                                            rs.getString("email_agri")
                                )
                        )
               ));
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return commandeList;
    }


    public void deleteCommande(Commande commande) {
        String request = "DELETE FROM Commande WHERE id_com=:id";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(dbCon, request);
            preparedStatement.setInt("id", commande.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
