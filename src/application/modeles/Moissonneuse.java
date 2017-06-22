package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;

/**
 * Classe pour les Moissonneuses
 */
public class Moissonneuse extends Vehicule {

    private int capacite_tremis; // Capacité du trémis en Litre
    private int capacite_reservoir; // Capacité du réservoir en Litre
    private float largeur; // Largeur en Mètre
    private float hauteur; // Hauteur en Mètre
    private float taille_coupe; // Taille de coupe en Mètre
    private int conso_fonctionnement; // Consommation en fonctionnement en Litre par Heure
    private int conso_route; // Consommation sur route en Litre par Heure
    private float poids; // Poids en Tonne

    private final static String TYPE = "Moissonneuse";

    public Moissonneuse(int id, String marque, String modele, String etat, Point position, int capacite_tremis, int capacite_reservoir, float largeur, float hauteur, float taille_coupe, int conso_fonctionnement, int conso_route, float poids) {
        super(id, TYPE, marque, modele, etat, position);

        this.capacite_tremis = capacite_tremis;
        this.capacite_reservoir = capacite_reservoir;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.taille_coupe = taille_coupe;
        this.conso_fonctionnement = conso_fonctionnement;
        this.conso_route = conso_route;
        this.poids = poids;

        getInformations().add(new ElementPair("Capacité trémis", capacite_tremis + " Litres"));
        getInformations().add(new ElementPair("Capacité réservoir", capacite_reservoir + " Litres"));
        getInformations().add(new ElementPair("Largeur", largeur + " Mètres"));
        getInformations().add(new ElementPair("Hauteur", hauteur + " Mètres"));
        getInformations().add(new ElementPair("Taille de coupe", taille_coupe + " Mètres"));
        getInformations().add(new ElementPair("Consommation fonctionnement", conso_fonctionnement + " Litres/100"));
        getInformations().add(new ElementPair("Consommation sur route", conso_route + " Litres/100"));
        getInformations().add(new ElementPair("Poids", poids + " Tonnes"));
    }

    public int getCapaciteTremis() {
        return capacite_tremis;
    }

    public void setCapacite_tremis(int capacite_tremis) {
        this.capacite_tremis = capacite_tremis;
    }

    public int getCapaciteReservoir() {
        return capacite_reservoir;
    }

    public void setCapacite_reservoir(int capacite_reservoir) {
        this.capacite_reservoir = capacite_reservoir;
    }

    public float getLargeur() {
        return largeur;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    public float getHauteur() {
        return hauteur;
    }

    public void setHauteur(float hauteur) {
        this.hauteur = hauteur;
    }

    public float getTailleCoupe() {
        return taille_coupe;
    }

    public void setTaille_coupe(float taille_coupe) {
        this.taille_coupe = taille_coupe;
    }

    public int getConsoFonctionnement() {
        return conso_fonctionnement;
    }

    public void setConso_fonctionnement(int conso_fonctionnement) {
        this.conso_fonctionnement = conso_fonctionnement;
    }

    public int getConsoRoute() {
        return conso_route;
    }

    public void setConso_route(int conso_route) {
        this.conso_route = conso_route;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }
}
