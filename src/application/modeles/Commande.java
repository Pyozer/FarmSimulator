package application.modeles;

import application.classes.ElementPair;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe pour les Agriculteurs
 */
public class Commande extends Element {

    private SimpleStringProperty nom; // Nom de l'agriculteur
    private SimpleStringProperty prenom; // Prénom de l'agriculteur
    private SimpleStringProperty num_tel; // Numéro de téléphone de l'agriculteur
    private SimpleStringProperty adresse; // Adresse de l'agriculteur
    private SimpleStringProperty email; // Adresse email de l'agriculteur

    public Commande(int id, String nom, String prenom, String num_tel, String adresse, String email) {
        super(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.num_tel = new SimpleStringProperty(num_tel);
        this.adresse = new SimpleStringProperty(adresse);
        this.email = new SimpleStringProperty(email);

        getInformations().add(new ElementPair("Nom", nom));
        getInformations().add(new ElementPair("Prénom", prenom));
        getInformations().add(new ElementPair("Numéro téléphone", num_tel));
        getInformations().add(new ElementPair("Adresse", adresse));
        getInformations().add(new ElementPair("Email", email));
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
        return nom.get() + " " + prenom.get();
    }
}
