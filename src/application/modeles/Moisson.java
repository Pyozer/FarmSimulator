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
    private String duree, h_fin;
    private Float nbKilo, nbTonne;

    public Moisson(Commande commande, Vehicule vehicule, String h_fin, String duree, Float nbKilo, Float nbTonne) {
        this.commande = commande;
        this.vehicule = vehicule;
        this.duree = duree;
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

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
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
