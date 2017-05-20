package application.modeles;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe pour les Tracteurs
 */
public class Tracteur extends Vehicule{

    private SimpleIntegerProperty capacite_remorque; // Capacité de la remorque en KG

    private final static String TYPE = "Tracteur";

    public Tracteur(int id, String marque, String modele, String etat, int capacite_remorque) {
        super(id, TYPE, marque, modele, etat);
        this.capacite_remorque = new SimpleIntegerProperty(capacite_remorque);
    }

    public int getCapacite_remorque() {
        return capacite_remorque.get();
    }

    public void setCapacite_remorque(int capacite_remorque) {
        this.capacite_remorque.set(capacite_remorque);
    }
}
