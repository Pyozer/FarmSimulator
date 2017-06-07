package application.modeles;

import application.database.DBConnection;
import application.database.NamedParameterStatement;
import java.sql.SQLException;

/**
 * Classe pour la gestion des rapports de moissons
 */
public class MoissonSQL {

    public static void addMoisson(Commande inputCommande, Vehicule inputVehicule, String intputJ_debut, String inputH_debut, String inputJ_fin, String inputH_fin, Float inputNbKilo, Float inputNbTonne) {
        String request = "UPDATE Ordre SET tonnes=:tonnes, nb_km_ordre=:nbKilo, duree_ordre:duree, heure_arrive_ordre:heureArrive WHERE id_vehi=:vehi AND id_com=:com";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setString("tonnes", "" + inputNbTonne);
            preparedStatement.setString("nbKilo", "" + inputNbKilo );
            preparedStatement.setString("heureArrive", inputJ_fin + " * " +inputH_fin );
            preparedStatement.setFloat("id_vehi", inputVehicule.getId());
            preparedStatement.setInt("com", inputCommande.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void editCommande(Commande commande) {
            String request = "UPDATE Commande SET date_com=:date_com, bott_com=:bott_com, transp_com=:transp_com, taille_max_transp_com=:taille_max_transp_com, id_champ=:id_champ WHERE id_com=:id_com";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setString("date_com", commande.getDate().toString());
            preparedStatement.setString("bott_com", commande.getTypebott());
            preparedStatement.setString("transp_com", commande.getTransport());
            preparedStatement.setFloat("taille_max_transp_com", commande.getTaillemax());
            preparedStatement.setInt("id_champ", commande.getChampCommande().getId());
            preparedStatement.setInt("id_com", commande.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}