package application.controlleurs;

import application.modeles.EtaSettings;

/**
 * Created by Pyozer on 22/06/2017.
 */
public class CarteController {

    public double getPosEtaX() {
        return EtaSettings.getInfosEta().getPosition().getX();
    }
    public double getPosEtaY() {
        return EtaSettings.getInfosEta().getPosition().getY();
    }
    public String getEtaNom() {
        return EtaSettings.getInfosEta().toString();
    }

    public void log(String msg) {
        System.err.println(msg);
    }

}
