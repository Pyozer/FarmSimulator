package application.modeles;

import application.classes.ElementPair;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe pour les Moissonneuses
 */
public class Moissonneuse extends Vehicule {

    private SimpleIntegerProperty capacite_tremis; // Capacité du trémis en Litre
    private SimpleIntegerProperty capacite_reservoir; // Capacité du réservoir en Litre
    private SimpleFloatProperty largeur; // Largeur en Mètre
    private SimpleFloatProperty hauteur; // Hauteur en Mètre
    private SimpleFloatProperty taille_coupe; // Taille de coupe en Mètre
    private SimpleIntegerProperty conso_fonctionnement; // Consommation en fonctionnement en Litre par Heure
    private SimpleIntegerProperty conso_route; // Consommation sur route en Litre par Heure
    private SimpleFloatProperty poids; // Poids en Tonne

    private final static String TYPE = "Moissonneuse";

    public Moissonneuse(int id, String marque, String modele, String etat, int capacite_tremis, int capacite_reservoir, float largeur, float hauteur, float taille_coupe, int conso_fonctionnement, int conso_route, float poids) {
        super(id, TYPE, marque, modele, etat);

        this.capacite_tremis = new SimpleIntegerProperty(capacite_tremis);
        this.capacite_reservoir = new SimpleIntegerProperty(capacite_reservoir);
        this.largeur = new SimpleFloatProperty(largeur);
        this.hauteur = new SimpleFloatProperty(hauteur);
        this.taille_coupe = new SimpleFloatProperty(taille_coupe);
        this.conso_fonctionnement = new SimpleIntegerProperty(conso_fonctionnement);
        this.conso_route = new SimpleIntegerProperty(conso_route);
        this.poids = new SimpleFloatProperty(poids);

        getInformations().add(new ElementPair("Capacité trémis", capacite_tremis));
        getInformations().add(new ElementPair("Capacité réservoir", capacite_reservoir));
        getInformations().add(new ElementPair("Largeur", largeur));
        getInformations().add(new ElementPair("Hauteur", hauteur));
        getInformations().add(new ElementPair("Taille de coupe", taille_coupe));
        getInformations().add(new ElementPair("Consommation fonctionnement", conso_fonctionnement));
        getInformations().add(new ElementPair("Consommation sur route", conso_route));
        getInformations().add(new ElementPair("Poids", poids));
    }

    public int getCapacite_tremis() {
        return capacite_tremis.get();
    }

    public void setCapacite_tremis(int capacite_tremis) {
        this.capacite_tremis.set(capacite_tremis);
    }

    public int getCapacite_reservoir() {
        return capacite_reservoir.get();
    }

    public void setCapacite_reservoir(int capacite_reservoir) {
        this.capacite_reservoir.set(capacite_reservoir);
    }

    public float getLargeur() {
        return largeur.get();
    }

    public void setLargeur(float largeur) {
        this.largeur.set(largeur);
    }

    public float getHauteur() {
        return hauteur.get();
    }

    public void setHauteur(float hauteur) {
        this.hauteur.set(hauteur);
    }

    public float getTaille_coupe() {
        return taille_coupe.get();
    }

    public void setTaille_coupe(float taille_coupe) {
        this.taille_coupe.set(taille_coupe);
    }

    public int getConso_fonctionnement() {
        return conso_fonctionnement.get();
    }

    public void setConso_fonctionnement(int conso_fonctionnement) {
        this.conso_fonctionnement.set(conso_fonctionnement);
    }

    public int getConso_route() {
        return conso_route.get();
    }

    public void setConso_route(int conso_route) {
        this.conso_route.set(conso_route);
    }

    public float getPoids() {
        return poids.get();
    }

    public void setPoids(float poids) {
        this.poids.set(poids);
    }
}
