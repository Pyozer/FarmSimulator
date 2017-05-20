package application.modeles;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe pour les Agriculteurs
 */
public class Agriculteur {

    private SimpleIntegerProperty id; // ID de l'agriculteur
    private SimpleStringProperty nom; // Nom de l'agriculteur
    private SimpleStringProperty prenom; // Prénom de l'agriculteur
    private SimpleStringProperty num_tel; // Numéro de téléphone de l'agriculteur
    private SimpleStringProperty adresse; // Adresse de l'agriculteur
    private SimpleStringProperty email; // Adresse email de l'agriculteur

    public Agriculteur(int id, String nom, String prenom, String num_tel, String adresse, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.num_tel = new SimpleStringProperty(num_tel);
        this.adresse = new SimpleStringProperty(adresse);
        this.email = new SimpleStringProperty(email);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getNum_tel() {
        return num_tel.get();
    }

    public void setNum_tel(String num_tel) {
        this.num_tel.set(num_tel);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String toString() {
        return nom.get() + "  " + prenom.get();
    }
}
