package application.modeles;

import application.classes.ConvertColor;
import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandeSQL {

    public static void addCommande(String inputDate, String inputTypeBott, String inputTransport, float inputTailleMax, Champ inputChamp) {
        String request = "INSERT INTO Commande(date_com, bott_com, transp_com, taille_max_transp_com, id_champ) VALUES(:date, :bott, :transp, :tMaxTransp, :champ)";

        try {
            NamedParameterStatement addCommandeStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            addCommandeStatement.setString("date", inputDate);
            addCommandeStatement.setString("bott", inputTypeBott);
            addCommandeStatement.setString("transp", inputTransport);
            addCommandeStatement.setFloat("tMaxTransp", inputTailleMax);
            addCommandeStatement.setInt("champ", inputChamp.getId());

            // Execute SQL statement
            addCommandeStatement.executeUpdate();

            addCommandeStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void editCommande(Commande commande) {
        String request = "UPDATE Commande SET date_com=:date_com, bott_com=:bott_com, transp_com=:transp_com, taille_max_transp_com=:taille_max_transp_com, effectuer_com=:effectuer_com WHERE id_com=:id_com";

        try {
            NamedParameterStatement editCommandeStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            editCommandeStatement.setString("date_com", commande.getDate().toString());
            editCommandeStatement.setString("bott_com", commande.getTypebott());
            editCommandeStatement.setString("transp_com", commande.getTransport());
            editCommandeStatement.setFloat("taille_max_transp_com", commande.getTaillemax());
            editCommandeStatement.setInt("id_com", commande.getId());
            editCommandeStatement.setInt("effectuer_com", commande.isEffectuer() ? 1 : 0);

            // Execute SQL statement
            editCommandeStatement.executeUpdate();

            editCommandeStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static ObservableList<Commande> getCommandeMakedList(boolean commandMaked) {
        int statut = (commandMaked) ? 1 : 0;

        String request = "SELECT * FROM Commande " +
                "INNER JOIN Champ ON Champ.id_champ=Commande.id_champ " +
                "INNER JOIN Agriculteur ON Agriculteur.id_agri=Champ.id_agri " +
                "INNER JOIN Culture ON Culture.id_cul=Champ.type_champ " +
                "WHERE Commande.effectuer_com=" + statut + " ORDER BY date_com ASC";
        return getCommandeFromRequest(request);
    }

    private static ObservableList<Commande> getCommandeFromRequest(String request) {

        ObservableList<Commande> commandeList = FXCollections.observableArrayList();

        try {
            PreparedStatement getCommandeStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = getCommandeStatement.executeQuery();

            while (rs.next()) {
                Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));
                Polygon coord_champ = JSONManager.readPolygon(rs.getString("coords_champ"));

                String requestGetSomme = "SELECT COUNT(*), SUM(tonnes_ordre) as tonne_get FROM Ordre INNER JOIN Commande ON Commande.id_com=Ordre.id_com GROUP BY Commande.id_com";
                PreparedStatement getTonneRecolte = DBConnection.getConnection().prepareStatement(requestGetSomme);
                // Execute SQL statement
                ResultSet tonneGet = getTonneRecolte.executeQuery();
                tonneGet.next();

                commandeList.add(new Commande(
                        rs.getInt("id_com"),
                        rs.getString("transp_com"),
                        rs.getString("bott_com"),
                        rs.getFloat("taille_max_transp_com"),
                        rs.getString("date_com"),
                        tonneGet.getFloat("tonne_get"),
                        rs.getFloat("cout_com"),
                        new Champ(
                                rs.getInt("id_agri"),
                                rs.getFloat("surf_champ"),
                                rs.getString("adr_champ"),
                                coord_center,
                                coord_champ,
                                new Culture(rs.getInt("id_cul"), rs.getString("type_cul")),
                                new Agriculteur(
                                        rs.getInt("id_agri"),
                                        rs.getString("prenom_agri"),
                                        rs.getString("nom_agri"),
                                        rs.getString("tel_agri"),
                                        rs.getString("adr_agri"),
                                        rs.getString("email_agri"),
                                        ConvertColor.webToColorFX(rs.getString("couleur_agri"))
                                )
                        ),
                        rs.getBoolean("effectuer_com")
                ));
            }

            rs.close();
            getCommandeStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return commandeList;
    }

    public static void deleteCommande(Commande commande) {
        String request = "DELETE FROM Commande WHERE id_com=:id";

        try {
            NamedParameterStatement deleteCommandeStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            deleteCommandeStatement.setInt("id", commande.getId());
            deleteCommandeStatement.executeUpdate();

            deleteCommandeStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
