package application.modeles;

import application.classes.ElementPair;

/**
 * Classe pour les type de cultures
 */
public class Culture extends Element {

    private String type_culture; // Type de culture

    public Culture(int id, String type_culture) {
        super(id);
        this.type_culture = type_culture;

        getInformations().add(new ElementPair("Type de culture", type_culture));
    }

    public String getTypeCulture() {
        return type_culture;
    }

    public void setTypeCulture(String type_culture) {
        this.type_culture = type_culture;
    }

    public String toString() {
        return type_culture;
    }
}
