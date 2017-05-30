package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe pour les Tracteurs
 */
public class Tracteur extends Vehicule{
    @Override
    public String toString() {
        return "Tracteur{" +
                "capacite_remorque=" + capacite_remorque +
                "} " + super.toString();
    }

    private SimpleIntegerProperty capacite_remorque; // Capacité de la remorque en KG

    private final static String TYPE = "Tracteur";

    public Tracteur(int id, String marque, String modele, String etat, Point position, int capacite_remorque) {
        super(id, TYPE, marque, modele, etat, position);
        this.capacite_remorque = new SimpleIntegerProperty(capacite_remorque);

        getInformations().add(new ElementPair("Capacité remorque", capacite_remorque + " Litres"));
    }

    public int getCapacite_remorque() {
        return capacite_remorque.get();
    }

    public void setCapacite_remorque(int capacite_remorque) {
        this.capacite_remorque.set(capacite_remorque);
    }
}
