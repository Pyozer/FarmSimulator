package application.modeles;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Classe pour les Botteleuse
 */
public class Botteleuse extends Vehicule {

    private SimpleBooleanProperty botte_ronde; // Si bottellage rond ou carré

    private final static String TYPE = "Botteleuse";

    public Botteleuse(int id, String marque, String modele, String etat, boolean botte_ronde) {
        super(id, TYPE, marque, modele, etat);
        this.botte_ronde = new SimpleBooleanProperty(botte_ronde);
    }

    public boolean isBotte_ronde() {
        return botte_ronde.get();
    }

    public void setBotte_ronde(boolean botte_ronde) {
        this.botte_ronde.set(botte_ronde);
    }
}
