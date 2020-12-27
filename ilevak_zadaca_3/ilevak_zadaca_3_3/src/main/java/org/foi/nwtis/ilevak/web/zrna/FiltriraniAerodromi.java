/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.ilevak.ejb.eb.Airports;
import org.foi.nwtis.ilevak.ejb.eb.Korisnici;
import org.foi.nwtis.ilevak.ejb.eb.Myairports;
import org.foi.nwtis.ilevak.ejb.sb.AirportsFacade;
import org.foi.nwtis.ilevak.ejb.sb.AirportsFacadeLocal;
import org.foi.nwtis.ilevak.ejb.sb.KorisniciFacade;
import org.foi.nwtis.ilevak.ejb.sb.KorisniciFacadeLocal;
import org.foi.nwtis.ilevak.ejb.sb.MyairportsFacade;
import org.foi.nwtis.ilevak.ejb.sb.MyairportsFacadeLocal;
import org.foi.nwtis.ilevak.ejb.sb.NadzorAerodroma;
import org.foi.nwtis.ilevak.ejb.sb.OpenWheather;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.rest.klijenti.OWMKlijent;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

/**
 * Klasa koja služi kao bean za prikaz na stranici filtriraniAerodromi.jsf
 * Filtrira aerodrome, dodaje aerodrome određenom korisniku
 *
 * @author ivale
 */
@Named(value = "filtriraniAerodromi")
@ViewScoped
public class FiltriraniAerodromi implements Serializable {

    @Inject
    ServletContext context;

    @EJB
    AirportsFacadeLocal airportsFacade;

    @EJB
    MyairportsFacadeLocal myairportsFacade;

    @EJB
    OpenWheather op;

    @EJB
    KorisniciFacadeLocal korisniciFacade;

    @Getter
    List<Korisnici> korisnici = new ArrayList<>();

    @Getter
    List<Airports> airports;

    @Getter
    @Setter
    String odabraniKorisnik = null;

    @Getter
    @Setter
    String nazivAerodroma = null;

    @Getter
    @Setter
    String porukaGreske = null;

    @Getter
    @Setter
    boolean prikaziAerodrome = false;

    BP_Konfiguracija bpk;

    /**
     * Creates a new instance of PrikazKorisnika
     */
    public FiltriraniAerodromi() {

    }

    /**
     * Dohvaca sve korisnike unutar baze podataka
     */
    @PostConstruct
    private void init() {
        korisnici = korisniciFacade.findAll();
        bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
    }

    /**
     * Dohvaća aerodrome prema upitu CAPI
     *
     * @return
     */
    public String dohvatiAerodrome() {
        airports = new ArrayList<>();
        if (!airportsFacade.findByName_CAPI(nazivAerodroma).isEmpty()) {
            porukaGreske = "Pričekajte, učitavaju se meteopodaci!";
            airports = airportsFacade.findByName_CAPI(nazivAerodroma);
            List<Airports> mojiAerodromi = new ArrayList<>();
            for (Myairports m : myairportsFacade.findAll()) {
                if (m.getUsername().getKorIme().equals(odabraniKorisnik)) {

                    mojiAerodromi.add(m.getIdent());
                }
                System.out.println(m.getIdent().getIdent());
            }
            for (int i = 0; i < mojiAerodromi.size(); i++) {
                for (int j = 0; j < airports.size(); j++) {
                    if (mojiAerodromi.get(i).getIdent() == null ? airports.get(j).getIdent() == null : mojiAerodromi.get(i).getIdent().equals(airports.get(j).getIdent())) {
                        airports.remove(j);
                    }
                }
            }
            prikaziAerodrome = true;
        } else {
            porukaGreske = "Nije pronađen nijedan aerodrom naziva: " + nazivAerodroma;
        }
        System.out.println(airports.toString());
        return "";
    }
    
    /**
     * Dohvaca metodu JPQL upita i aerodrome s određenim nazivom
     * @return 
     */
    public String dohvatiAerodromeJPQL(){
        airports=new ArrayList<>();
        if(!airportsFacade.findByName_JPQL(nazivAerodroma).isEmpty()){
            porukaGreske = "Pričekajte, učitavaju se meteopodaci!";
            airports = airportsFacade.findByName_JPQL(nazivAerodroma);
            List<Airports> mojiAerodromi = new ArrayList<>();
            for (Myairports m : myairportsFacade.findAll()) {
                if (m.getUsername().getKorIme().equals(odabraniKorisnik)) {

                    mojiAerodromi.add(m.getIdent());
                }
                System.out.println(m.getIdent().getIdent());
            }
            for (int i = 0; i < mojiAerodromi.size(); i++) {
                for (int j = 0; j < airports.size(); j++) {
                    if (mojiAerodromi.get(i).getIdent() == null ? airports.get(j).getIdent() == null : mojiAerodromi.get(i).getIdent().equals(airports.get(j).getIdent())) {
                        airports.remove(j);
                    }
                }
            }
            prikaziAerodrome = true;
        }else {
            porukaGreske = "Nije pronađen nijedan aerodrom naziva: " + nazivAerodroma;
        }
        System.out.println(airports.toString());
     return "";   
    }
    
    
    
    /**
     * Prima objekt tipa Airports, te dohvaca temperaturu preko klase
     * OpenWheatherMap i metode dohvatiTemp
     *
     * @param a
     * @return
     */
    public String dohvatiTemperaturu(Airports a) {
        String[] gps = a.getCoordinates().split(", ");
        return op.dohvatiTemp(bpk, gps[1], gps[0]);
    }

    /**
     * Prima objekt tipa Airports, te dohvaca vlagu preko klase OpenWheatherMap
     * i metode dohvatiTemp
     *
     * @param a
     * @return
     */
    public String dohvatiVlagu(Airports a) {
        String[] gps = a.getCoordinates().split(", ");
        return op.dohvatiVlagu(bpk, gps[1], gps[0]);
    }
    
   

    /**
     * Klasa sa testiranje
     *
     * @return
     */
    public String ispisines(Airports a) {
        airports = airportsFacade.findByName_CAPI(nazivAerodroma);
        System.out.println("Usao u ovaj kod");
        return "";
    }
    
    
    

}
