package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;

/**
 * Classe pour les Tracteurs
 */
public class Tracteur extends Vehicule {

    private int capacite_remorque; // Capacité de la remorque en KG

    private final static String TYPE = "Tracteur";

    public Tracteur(int id, String marque, String modele, String etat, Point position, int capacite_remorque) {
        super(id, TYPE, marque, modele, etat, position);
        this.capacite_remorque = capacite_remorque;

        getInformations().add(new ElementPair("Capacité remorque", capacite_remorque + " Litres"));
    }

    public int getCapacite_remorque() {
        return capacite_remorque;
    }

    public void setCapacite_remorque(int capacite_remorque) {
        this.capacite_remorque = capacite_remorque;
    }
}
