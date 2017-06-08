package application.modeles;

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

public class MoissonSQL {

    public static void editMoisson(Commande inputCommande, Vehicule inputVehicule, Float inputDuree, String inputH_fin, Float inputNbKilo, Float inputNbTonne) {
        String request = "UPDATE Ordre SET tonnes_ordre=:tonnes, nb_km_ordre=:nbKilo, duree_ordre=:duree, heure_arrive_ordre=:heureArrive" +
                "WHERE id_vehi=:vehi AND id_com=:com";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            System.out.println("id : " + inputVehicule.getId());
            preparedStatement.setFloat("tonnes", inputNbTonne);
            preparedStatement.setFloat("nbKilo", inputNbKilo );
            preparedStatement.setString("heureArrive", inputH_fin);
            preparedStatement.setFloat("duree",  inputDuree);
            preparedStatement.setInt("vehi", inputVehicule.getId());
            preparedStatement.setInt("com", inputCommande.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void addMoisson(Commande inputCommande, Vehicule inputVehicule) {
            String request = "INSERT INTO Ordre(id_vehi, id_com) VALUES (:id_vehi, :id_com)";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setString("id_vehi", "" + inputVehicule.getId());
            preparedStatement.setString("id_com", "" + inputCommande.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static ObservableList<Moisson> getMoissonList() {

        String request = "SELECT * FROM Ordre " +
                        "INNER JOIN Commande ON Commande.id_com = Ordre.id_com"+
                        "INNER JOIN Champ ON Champ.id_champ=Commande.id_champ " +
                        "INNER JOIN Agriculteur ON Agriculteur.id_agri=Champ.id_agri" +
                        "INNER JOIN Culture ON Culture.id_cul=Champ.type_champ ";

        ObservableList<Moisson> moissonList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Vehicule vehi_com = null;

                Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));
                Polygon coord_champ = new Polygon(JSONManager.readPolygon(rs.getString("coords_champ")));
                Point position = JSONManager.readPoint(rs.getString("position_vehi"));

                if(rs.getString("type_bott") != null){
                    vehi_com = new Botteleuse(
                            Integer.parseInt(rs.getString("id_vehi")),
                            rs.getString("marque_vehi"),
                            rs.getString("modele_vehi"),
                            rs.getString("etat_vehi"),
                            position,
                            Boolean.parseBoolean(rs.getString("type_bott"))
                    );
                }
                else if(rs.getString("taille_tremis_moi") != null){
                    vehi_com = new Moissonneuse(
                            Integer.parseInt(rs.getString("id_vehi")),
                            rs.getString("marque_vehi"),
                            rs.getString("modele_vehi"),
                            rs.getString("etat_vehi"),
                            position,
                            Integer.parseInt(rs.getString("taille_tremis_moi")),
                            Integer.parseInt(rs.getString("taille_reserve_moi")),
                            Integer.parseInt(rs.getString("largeur_route_moi")),
                            Integer.parseInt(rs.getString("hauteur_moi")),
                            Integer.parseInt(rs.getString("largeur_coupe_moi")),
                            Integer.parseInt(rs.getString("conso_fonct_moi")),
                            Integer.parseInt(rs.getString("conso_route_moi")),
                            Integer.parseInt(rs.getString("poids_moi"))
                    );
                }
                else if(rs.getString("cap_rem_tract") != null){
                    vehi_com = new Tracteur(
                            Integer.parseInt(rs.getString("id_vehi")),
                            rs.getString("marque_vehi"),
                            rs.getString("modele_vehi"),
                            rs.getString("etat_vehi"),
                            position,
                            Integer.parseInt(rs.getString("cap_rem_tract"))
                    );
                }

                moissonList.add(new Moisson(Integer.parseInt(rs.getString("id_ordre")),
                        new Commande(
                                Integer.parseInt(rs.getString("id_com")),
                                rs.getString("transp_com"),
                                rs.getString("bott_com"),
                                Float.parseFloat(rs.getString("taille_max_transp_com")),
                                rs.getString("date_com"),
                                Float.parseFloat(rs.getString("tonne_com")),
                                Float.parseFloat(rs.getString("cout_com")),
                                new Champ(
                                        Integer.parseInt(rs.getString("id_agri")),
                                        Float.parseFloat(rs.getString("surf_champ")),
                                        rs.getString("adr_champ"),
                                        coord_center,
                                        coord_champ,
                                        rs.getString("type_cul"),
                                        new Agriculteur(
                                                Integer.parseInt(rs.getString("id_agri")),
                                                rs.getString("prenom_agri"),
                                                rs.getString("nom_agri"),
                                                rs.getString("tel_agri"),
                                                rs.getString("adr_agri"),
                                                rs.getString("email_agri")
                                        )
                                ),
                                rs.getBoolean("effectuer_com")
                        ),
                        vehi_com,
                        rs.getString("heure_arrive_ordre"),
                        Float.parseFloat(rs.getString("duree_ordre")),
                        Float.parseFloat(rs.getString("nb_km_ordre")),
                        Float.parseFloat(rs.getString("tonnes_ordre"))
                    ));

            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return moissonList;
    }

   /* public static ObservableList<Moisson> getMoissonList() {
        return getMoissonList();
    }*/

    public static void deleteMoisson(Moisson moisson) {
        String request = "DELETE FROM Ordre WHERE id_com=:id";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            preparedStatement.setInt("id", moisson.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
