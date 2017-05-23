package application.modeles;

import application.classes.ElementPair;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe pour les Moissonneuses
 */
public class Moissonneuse extends Vehicule {

    private SimpleIntegerProperty capacite_tremis; // Capacité du trémis en Litre
    private SimpleIntegerProperty capacite_reservoir; // Capacité du réservoir en Litre
    private SimpleDoubleProperty largeur; // Largeur en Mètre
    private SimpleDoubleProperty hauteur; // Hauteur en Mètre
    private SimpleDoubleProperty taille_coupe; // Taille de coupe en Mètre
    private SimpleIntegerProperty conso_fonctionnement; // Consommation en fonctionnement en Litre par Heure
    private SimpleIntegerProperty conso_route; // Consommation sur route en Litre par Heure
    private SimpleDoubleProperty poids; // Poids en Tonne

    private final static String TYPE = "Moissonneuse";

    public Moissonneuse(int id, String marque, String modele, String etat, int capacite_tremis, int capacite_reservoir, double largeur, double hauteur, double taille_coupe, int conso_fonctionnement, int conso_route, double poids) {
        super(id, TYPE, marque, modele, etat);

        this.capacite_tremis = new SimpleIntegerProperty(capacite_tremis);
        this.capacite_reservoir = new SimpleIntegerProperty(capacite_reservoir);
        this.largeur = new SimpleDoubleProperty(largeur);
        this.hauteur = new SimpleDoubleProperty(hauteur);
        this.taille_coupe = new SimpleDoubleProperty(taille_coupe);
        this.conso_fonctionnement = new SimpleIntegerProperty(conso_fonctionnement);
        this.conso_route = new SimpleIntegerProperty(conso_route);
        this.poids = new SimpleDoubleProperty(poids);

        getInformations().add(new ElementPair("Capacité trémis", capacite_tremis + " Litres"));
        getInformations().add(new ElementPair("Capacité réservoir", capacite_reservoir + " Litres"));
        getInformations().add(new ElementPair("Largeur", largeur + " Mètres"));
        getInformations().add(new ElementPair("Hauteur", hauteur + " Mètres"));
        getInformations().add(new ElementPair("Taille de coupe", taille_coupe + " Mètres"));
        getInformations().add(new ElementPair("Consommation fonctionnement", conso_fonctionnement + " Litres/100"));
        getInformations().add(new ElementPair("Consommation sur route", conso_route + " Litres/100"));
        getInformations().add(new ElementPair("Poids", poids + " Tonnes"));
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

    public double getLargeur() {
        return largeur.get();
    }

    public void setLargeur(float largeur) {
        this.largeur.set(largeur);
    }

    public double getHauteur() {
        return hauteur.get();
    }

    public void setHauteur(float hauteur) {
        this.hauteur.set(hauteur);
    }

    public double getTaille_coupe() {
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

    public double getPoids() {
        return poids.get();
    }

    public void setPoids(float poids) {
        this.poids.set(poids);
    }
}
