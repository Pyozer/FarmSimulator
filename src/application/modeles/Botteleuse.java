package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;

import static application.Constant.TYPE_BOTT_CARRE;
import static application.Constant.TYPE_BOTT_ROND;

/**
 * Classe pour les Botteleuse
 */
public class Botteleuse extends Vehicule {

    private boolean botte_ronde; // Si bottellage rond ou carré

    private final static String TYPE = "Botteleuse";

    public Botteleuse(int id, String marque, String modele, String etat, Point position, boolean botte_ronde) {
        super(id, TYPE, marque, modele, etat, position);
        this.botte_ronde = botte_ronde;

        getInformations().add(new ElementPair("Botte ronde", (botte_ronde) ? TYPE_BOTT_ROND : TYPE_BOTT_CARRE));
    }

    public boolean isBotte_ronde() {
        return botte_ronde;
    }

    public void setBotte_ronde(boolean botte_ronde) {
        this.botte_ronde = botte_ronde;
    }
}
