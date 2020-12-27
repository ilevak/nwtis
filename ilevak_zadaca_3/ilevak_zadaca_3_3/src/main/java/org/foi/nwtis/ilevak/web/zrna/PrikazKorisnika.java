/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.zrna;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.ilevak.ejb.eb.Airports;
import org.foi.nwtis.ilevak.ejb.eb.Korisnici;
import org.foi.nwtis.ilevak.ejb.eb.Myairports;
import org.foi.nwtis.ilevak.ejb.eb.Myairportslog;
import org.foi.nwtis.ilevak.ejb.sb.KorisniciFacadeLocal;
import org.foi.nwtis.ilevak.ejb.sb.MyairportslogFacadeLocal;

/**
 *Bean za prikazKorisnika.jxml, sadrži metode za brisanje, dohvaćanje aerodroma i korisnika
 * @author ivale
 */
@Named(value = "prikazKorisnika")
@ViewScoped
public class PrikazKorisnika implements Serializable {

    @EJB
    KorisniciFacadeLocal korisniciFacade;
    @EJB
    MyairportslogFacadeLocal myairportslogFacade;
    @Getter
    List<Korisnici> korisnici = new ArrayList<>();

    @Getter
    @Setter
    List<Myairports> aerodromi = new ArrayList<>();

    @Getter
    @Setter
    List<Myairportslog> myairportslogs = new ArrayList<>();

    @Getter
    @Setter
    boolean prikaziAerodrome;
    @Getter
    @Setter
    boolean prikaziKorisnike;
    @Getter
    @Setter
    boolean prikaziDatume;

    /**
     * Creates a new instance of PrikazKorisnika
     */
    public PrikazKorisnika() {

    }

    /**
     * Dohvaca sve korisnike unutar baze podataka
     */
    @PostConstruct
    private void dohvatiKorisnike() {
        korisnici = korisniciFacade.findAll();
        prikaziAerodrome = false;
        prikaziKorisnike = true;
        prikaziDatume = false;

    }

    /**
     *Dohvaca sve korisnikove aerodrome i sprema ih u listu aerodromi
     * @param k
     */
    public void dohvatiKorisnikoveAerodrome(Korisnici k) {

        if (k != null && !k.getMyairportsList().isEmpty()) {
            prikaziKorisnike = false;
            prikaziAerodrome = true;
            aerodromi = k.getMyairportsList();
        }

    }

    /**
     * Dohvaca sve myAirportlog i sprema ih u listu ti podaci su dohvaceni od
     * odredenog aerodroma--odnosno pojavljivanje aerodroma unutar myariportlog
     * unutar baze
     *
     * @param a
     */
    public void dohvatiDatume(Airports a) {
        if (a != null) {
            prikaziKorisnike = false;
            prikaziAerodrome = true;
            prikaziDatume = true;
            myairportslogs = a.getMyairportslogList();
        }
    }

    /**
     * Vraca pocetne vrijednosti kako bi se ponovo mogla prikazati tablice
     * Korisnici
     */
    public void vratiNaPocetak() {
        prikaziAerodrome = false;
        prikaziKorisnike = true;
        prikaziDatume = false;
    }

    /**
     *Pretvara uneseni datum u formatirani datum za HR
     * @param date
     * @return formatirani string datuma
     */
    public String pretvoriDatum(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        return sdf.format(date);
    }

    /**
     **
     *Pretvara uneseni datum u formatirani datum i vrijeme za HR
     * @param datum
     * @return formatirani string datuma
     */
    public String pretvoriDatumVrijeme(Date datum) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        return sdf2.format(datum);

    }

    /**
     *Brise aerodrom iz baze podataka iz tablice myariportslog
     * @param m
     */
    public void izbrisiAerodrom(Myairportslog m) {
        myairportslogFacade.remove(m);
        myairportslogs.remove(m);
    }
}
