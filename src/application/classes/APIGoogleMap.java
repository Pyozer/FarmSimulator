package application.classes;

import application.modeles.EtaSettings;

/**
 * Created by justin on 20/05/2017.
 */
public interface APIGoogleMap {

    void log(String msg);

    double getPosEtaX();
    double getPosEtaY();
    String getEtaNom();

}