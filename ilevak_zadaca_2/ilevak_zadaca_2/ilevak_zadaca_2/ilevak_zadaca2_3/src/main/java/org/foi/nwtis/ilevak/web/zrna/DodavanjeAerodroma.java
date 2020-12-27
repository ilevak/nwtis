/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.ilevak.rest.klijenti.Zadaca2_2RS;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Icao;
import org.foi.nwtis.podaci.OdgovorAerodrom;
import org.primefaces.component.carousel.CarouselBase;

/**
 *
 * @author ivale
 */
@Named(value = "dodavanjeAerodroma")
@SessionScoped
public class DodavanjeAerodroma implements Serializable {

    @Inject
    PrijavaKorisnika prijavaKorisnika;

    String korisnik = "";
    String lozinka = "";

    @Getter
    @Setter
    String naziv="";
    @Getter
    @Setter
    String drzava;
    @Getter
    List<Aerodrom> aerodromi;
    @Getter
    @Setter
    List<Aerodrom> aerodromiKorisnika;
  

    public void preuzmiPodatkeKorisnika() {
        korisnik = prijavaKorisnika.getKorisnik();
        lozinka = prijavaKorisnika.getLozinka();

    }

    public String dajAerodromeDrzava() {
        //kada radimo s korisnicima uvijek ide ova metoda
        preuzmiPodatkeKorisnika();
        Zadaca2_2RS zadaca2_2RS = new Zadaca2_2RS(korisnik, lozinka);
        OdgovorAerodrom odgovor = zadaca2_2RS.dajAerodromNazivDrzava(OdgovorAerodrom.class, drzava, "");
        aerodromi = new ArrayList(Arrays.asList(odgovor.getOdgovor()));

        dajMojeAerodrome();

        aerodromi.removeAll(aerodromiKorisnika);
        //prazan stirng vraca isti pogled
        return "";
    }

    public String dajAerodromeNaziv() {
        preuzmiPodatkeKorisnika();
        Zadaca2_2RS zadaca2_2RS = new Zadaca2_2RS(korisnik, lozinka);
        OdgovorAerodrom odgovor;
        if(naziv.isEmpty()){
            odgovor = zadaca2_2RS.dajAerodromNazivDrzava(OdgovorAerodrom.class, null, null);
        }
        else{
            
            odgovor = zadaca2_2RS.dajAerodromNazivDrzava(OdgovorAerodrom.class, null, naziv);
            
        }
        aerodromi = new ArrayList(Arrays.asList(odgovor.getOdgovor()));
        System.out.println(Arrays.toString(odgovor.getOdgovor()));
        dajMojeAerodrome();

        return "";
    }

    public String dajMojeAerodrome() {
        preuzmiPodatkeKorisnika();
        Zadaca2_2RS zadaca2_2RS = new Zadaca2_2RS(korisnik, lozinka);
        OdgovorAerodrom odgovor = zadaca2_2RS.dajAerodomeKorisnika(OdgovorAerodrom.class);
        aerodromiKorisnika = new ArrayList(Arrays.asList(odgovor.getOdgovor()));
        List<Aerodrom> obrisi = new ArrayList<>();
        for (Aerodrom a : aerodromi) {
            for (Aerodrom a1 : aerodromiKorisnika) {
                if (a.getNaziv().equals(a1.getNaziv())) {
                    obrisi.add(a);
                }
            }
        }
        aerodromi.removeAll(obrisi);
        return "";
    }

    public String dodajMojAerodromIcao(String icaoOdabrani) {
        //TODO treba ovdje obaviti dodaj aerodrom u kolekciju aerodroma u bazi podataka

        preuzmiPodatkeKorisnika();
        Zadaca2_2RS zadaca2_2RS = new Zadaca2_2RS(korisnik, lozinka);
        if (icaoOdabrani == null) {
            System.out.println("Krivo upisani podaci");
        } else {
            System.out.println(icaoOdabrani);
            Icao icao = new Icao(icaoOdabrani);

            Response odgovor = zadaca2_2RS.dodajMojAerodrom(icao);

            System.out.println(odgovor);;
            //TODO PRIKAZI NESZ BLABLA
            Aerodrom a1 = null;
            System.out.println("DODANO");
            for (Aerodrom a : aerodromi) {
                if (a.getIcao().compareTo(icaoOdabrani) == 0) {
                    aerodromiKorisnika.add(a);
                    a1 = a;
                }
            }
            aerodromi.remove(a1);

        }
        return "";
    }

}
