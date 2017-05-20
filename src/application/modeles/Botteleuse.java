package application.modeles;

import application.classes.ElementPair;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour les Botteleuse
 */
public class Botteleuse extends Vehicule {

    private SimpleBooleanProperty botte_ronde; // Si bottellage rond ou carré

    private final static String TYPE = "Botteleuse";

    public Botteleuse(int id, String marque, String modele, String etat, boolean botte_ronde) {
        super(id, TYPE, marque, modele, etat);
        this.botte_ronde = new SimpleBooleanProperty(botte_ronde);

        getInformations().add(new ElementPair("Botte ronde", (botte_ronde) ? "Rond" : "Carré"));
    }

    public boolean isBotte_ronde() {
        return botte_ronde.get();
    }

    public void setBotte_ronde(boolean botte_ronde) {
        this.botte_ronde.set(botte_ronde);
    }
}
