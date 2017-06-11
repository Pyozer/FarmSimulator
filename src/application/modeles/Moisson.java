package application.modeles;

/**
 * Classe pour les Moisson
 */
public class Moisson {

    private int id;
    private Commande commande;
    private Vehicule vehicule;
    private String datetime_fin, datetime_debut;
    private Float nbKilo, nbTonne;


    public Moisson(int id, Commande commande, Vehicule vehicule, String datetime_debut, String datetime_fin, Float nbKilo, Float nbTonne) {
        this.id = id;
        this.commande = commande;
        this.vehicule = vehicule;
        this.datetime_fin = datetime_fin;
        this.datetime_debut = datetime_debut;
        this.nbKilo = nbKilo;
        this.nbTonne = nbTonne;
    }


    public String getDatetimeDebut() {
        return datetime_debut;
    }

    public void setDatetimeDebut(String datetime_debut) {
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

    public String getDatetimeFin() {
        return datetime_fin;
    }

    public void setDatetimeFin(String datetime_fin) {
        this.datetime_fin = datetime_fin;
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
