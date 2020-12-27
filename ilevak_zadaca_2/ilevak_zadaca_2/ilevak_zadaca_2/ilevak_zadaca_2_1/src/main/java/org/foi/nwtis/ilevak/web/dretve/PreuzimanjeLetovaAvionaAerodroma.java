/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.dretve;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.annotation.ApplicationMap;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.ilevak.web.podaci.AirportDAO;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Airport;
import org.foi.nwtis.rest.klijenti.OSKlijent;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.rest.podaci.AvioniKolekcija;

/**
 *
 * @author ivale
 */
public class PreuzimanjeLetovaAvionaAerodroma extends Thread {

    private BP_Konfiguracija konf;

    //TODO potrebne varijable/atributi
    OSKlijent klijent;
    String korime;
    String lozinka;
    Date datumPreuzimanja;
    Date krajDatePreuzimanja;
    int pocetak;
    int kraj;
    //interval je u milisekundama
    long intervalCiklusa;
    long trajanjeCiklusa;
    long pauza;
    Boolean krajPreuzimanja = false;

    @Override
    public void interrupt() {
        krajPreuzimanja = true;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param konf
     */
    public PreuzimanjeLetovaAvionaAerodroma(BP_Konfiguracija konf) {
        this.konf = konf;
    }

    @Override
    public void run() {

        int brojac = 0;
        AirportDAO airportDAO = new AirportDAO();
        List<Airport> aerodromi;
        //pitanje je jeli ide tu dajMojeAerodrome ili dajSveAerodrome
        aerodromi = airportDAO.dajMojeAerodrome(konf);
        while (!krajPreuzimanja) {
            int krajDana = 0;
            for (Airport a : aerodromi) {
                krajDana = pocetak + (24 * 60 * 60);

                if (airportDAO.provjeriAerodromLog(a, konf, millsDatum(pocetak))) {
                    OSKlijent osKlijent = new OSKlijent(korime, lozinka);
                    System.out.println("Provjera za aerodrom:" + a.getName() + " od: " + pocetak + " do:  " + krajDana);
                    List<AvionLeti> ak = osKlijent.getDepartures(a.getIdent(), pocetak, krajDana);
                    for (AvionLeti avion : ak) {
                        airportDAO.dodajPoadtkeBazeAvioni(avion, konf);
                    }
                    airportDAO.dodajZapisULog(a, konf, pocetak);
                }
                try {
                    Thread.sleep(pauza);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PreuzimanjeLetovaAvionaAerodroma.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            pocetak = krajDana;
            if (pocetak == kraj) {
                intervalCiklusa=24*60*60*1000;
            } else {
                intervalCiklusa=trajanjeCiklusa;
            }
            try {
                    Thread.sleep(intervalCiklusa);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PreuzimanjeLetovaAvionaAerodroma.class.getName()).log(Level.SEVERE, null, ex);
                }
            brojac++;

        }
    }

    /*
    *
     */
    @Override
    public synchronized void start() {
        // Dodati atribute za početni datum preuzimanja, trajanje ciklusa dretve i trajanje pauze između dva aerodroma
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        lozinka = konf.getKonfig().dajPostavku("OpenSkyNetwork.lozinka");
        korime = konf.getKonfig().dajPostavku("OpenSkyNetwork.korisnik");
        try {
            datumPreuzimanja = format.parse(konf.getKonfig().dajPostavku("preuzimanje.pocetak"));
            krajDatePreuzimanja = format.parse(konf.getKonfig().dajPostavku("preuzimanje.kraj"));
        } catch (ParseException ex) {
            Logger.getLogger(PreuzimanjeLetovaAvionaAerodroma.class.getName()).log(Level.SEVERE, null, ex);
        }
        trajanjeCiklusa = Long.parseLong(konf.getKonfig().dajPostavku("preuzimanje.ciklus").trim()) * 1000;
        pauza = Long.parseLong(konf.getKonfig().dajPostavku("preuzimanje.pauza").trim());
        klijent = new OSKlijent(korime, lozinka);
        pocetak = (int) (datumPreuzimanja.getTime() / 1000);
        System.out.println(pocetak);
        kraj = (int) krajDatePreuzimanja.getTime() / 1000;

        super.start();

    }

    /**
     * Metoda pretvara milisekunde u string datuma
     *
     * @param milis
     * @return
     */
    public String millsDatum(long milis) {
        
        Date date = new Date(milis*1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(date);
    }
}
