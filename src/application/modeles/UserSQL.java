package application.modeles;

import application.Constant;
import application.classes.SHA1;
import application.database.DBConnection;
import application.database.NamedParameterStatement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe pour la gestion des comptes utilisateurs
 */
public class UserSQL implements Constant {

    public static boolean checkIdentifiants(String email, String password) throws NoSuchAlgorithmException {
        boolean login_ok = false;

        String request = "SELECT COUNT(id_user) as rowCount, email_user, password_user FROM User WHERE email_user=:email AND password_user=:password";
        try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setString("email", email);
            stmt.setString("password", SHA1.cryptToSHA1(password));
            // execute select SQL stetement
            ResultSet rs = stmt.executeQuery();

            rs.next();
            if(rs.getInt("rowCount") > 0) {
                login_ok = true;
            }

            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login_ok;
    }

    public static void addAccount(String nom, String prenom, String email, String password) throws NoSuchAlgorithmException {
        String request = "INSERT INTO User(nom_user, prenom_user, email_user, password_user) VALUES(:nom, :prenom, :email, :password)";
        try {
            NamedParameterStatement addAccount = new NamedParameterStatement(DBConnection.getConnection(), request);

            addAccount.setString("nom", nom);
            addAccount.setString("prenom", prenom);
            addAccount.setString("email", email);
            addAccount.setString("password", SHA1.cryptToSHA1(password));

            // Execute SQL statement
            addAccount.executeUpdate();

            addAccount.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static boolean checkIfExists(String email) {
        int count = 1;

        String request = "SELECT COUNT(id_user) as rowCount FROM User WHERE email_user=:email";

        try {
            NamedParameterStatement checkExists = new NamedParameterStatement(DBConnection.getConnection(), request);
            checkExists.setString("email", email);
            ResultSet res = checkExists.executeQuery();

            res.next();
            count = res.getInt("rowCount");

            res.close();
            checkExists.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count >= 1;
    }

    public static int getNbAccount() {
        int count = 0;

        String request = "SELECT COUNT(id_user) as rowCount FROM `user`";

        try {
            PreparedStatement nbAccount = DBConnection.getConnection().prepareStatement(request);
            ResultSet res = nbAccount.executeQuery();

            res.next();
            count = res.getInt("rowCount");
            System.out.println(count);

            res.close();
            nbAccount.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}