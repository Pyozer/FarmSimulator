package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Classe pour les Moisson
 */
public class Moisson {

    private Commande commande;
    private Vehicule vehicule;
    private String j_debut, h_debut, j_fin, h_fin;
    private Float nbKilo, nbTonne;

    public Moisson(Commande commande, Vehicule vehicule, String j_debut, String h_debut, String j_fin, String h_fin, Float nbKilo, Float nbTonne) {
        this.commande = commande;
        this.vehicule = vehicule;
        this.j_debut = j_debut;
        this.h_debut = h_debut;
        this.j_fin = j_fin;
        this.h_fin = h_fin;
        this.nbKilo = nbKilo;
        this.nbTonne = nbTonne;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public String getJ_debut() {
        return j_debut;
    }

    public void setJ_debut(String j_debut) {
        this.j_debut = j_debut;
    }

    public String getH_debut() {
        return h_debut;
    }

    public void setH_debut(String h_debut) {
        this.h_debut = h_debut;
    }

    public String getJ_fin() {
        return j_fin;
    }

    public void setJ_fin(String j_fin) {
        this.j_fin = j_fin;
    }

    public String getH_fin() {
        return h_fin;
    }

    public void setH_fin(String h_fin) {
        this.h_fin = h_fin;
    }

    public Float getNbKilo() {
        return nbKilo;
    }

    public void setNbKilo(Float nbKilo) {
        this.nbKilo = nbKilo;
    }

    public Float getNbTonne() {
        return nbTonne;
    }

    public void setNbTonne(Float nbTonne) {
        this.nbTonne = nbTonne;
    }
}
