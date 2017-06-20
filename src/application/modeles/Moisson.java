package application.modeles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Classe pour les Moisson
 */
public class Moisson {

    private int id;
    private Commande commande;
    private Vehicule vehicule;
    private LocalDateTime datetime_fin, datetime_debut;
    private Float nbKm, nbTonne;


    public Moisson(int id, Commande commande, Vehicule vehicule, LocalDateTime datetime_debut, LocalDateTime datetime_fin, Float nbKm, Float nbTonne) {
        this.id = id;
        this.commande = commande;
        this.vehicule = vehicule;
        this.datetime_fin = datetime_fin;
        this.datetime_debut = datetime_debut;
        this.nbKm = nbKm;
        this.nbTonne = nbTonne;
    }


    public LocalDateTime getDatetimeDebut() {
        return datetime_debut;
    }

    public void setDatetimeDebut(LocalDateTime datetime_debut) {
        this.datetime_debut = datetime_debut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getDatetimeFin() {
        return datetime_fin;
    }

    public void setDatetimeFin(LocalDateTime datetime_fin) {
        this.datetime_fin = datetime_fin;
    }

    public Float getNbKm() {
        return nbKm;
    }

    public void setNbKm(Float nbKm) {
        this.nbKm = nbKm;
    }

    public Float getNbTonne() {
        return nbTonne;
    }

    public void setNbTonne(Float nbTonne) {
        this.nbTonne = nbTonne;
    }

    public double getDuree() {
        return ChronoUnit.HOURS.between(datetime_debut, datetime_fin);
    }
}
