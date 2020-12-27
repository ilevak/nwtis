/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.foi.nwtis.ilevak.ejb.eb.Airports;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.rest.klijenti.OWMKlijent;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

/**
 *Klasa poslovne logike dohvacanja temperature i vlage putem gps-a aerodroma
 * @author ivale
 */
@Stateless
@LocalBean
public class OpenWheather {

    /**
     *Vraća temperaturu prema gps-u aerodroma. Koristi se OWMKlijent i OpenWeatherMap
     * @param bpk
     * @param sirina
     * @param duzina
     * @return
     */
    public String dohvatiTemp(BP_Konfiguracija bpk, String sirina, String duzina) {
        String apikey = bpk.getKonfig().dajPostavku("OpenWeatherMap.apikey");
        OWMKlijent oWMKlijent = new OWMKlijent(apikey);
        return oWMKlijent.getRealTimeWeather(sirina, duzina).getTemperatureValue().toString();
    }
    
    /**
     *Vraća vlagu prema gps-u aerodroma. Koristi se OWMKlijent i OpenWeatherMap
     * @param bpk
     * @param sirina
     * @param duzina
     * @return
     */
    public String dohvatiVlagu(BP_Konfiguracija bpk, String sirina, String duzina) {
        String apikey = bpk.getKonfig().dajPostavku("OpenWeatherMap.apikey");
        OWMKlijent oWMKlijent = new OWMKlijent(apikey);
        return oWMKlijent.getRealTimeWeather(sirina, duzina).getHumidityValue().toString();
    }
}
