/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.zrna;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.ilevak.Zadaca2_1WS;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.ilevak.ws.serveri.AvionLeti;
import org.foi.nwtis.ilevak.ws.serveri.LetPozicija;
import org.foi.nwtis.rest.klijenti.LIQKlijent;
import org.foi.nwtis.rest.podaci.Adresa;
import org.foi.nwtis.rest.podaci.Lokacija;

/**
 *
 * @author ivale
 */
@Named(value = "pregledAviona")
@SessionScoped
public class PregledAviona implements Serializable {

    @Inject
    PrijavaKorisnika prijavaKorisnika;
    @Inject
    PregledAerodroma pregledAerodroma;

    @Inject
    ServletContext context;

    String korisnik = "";
    String lozinka = "";
    Lokacija lok;
    @Getter
    @Setter
    int odVrijeme=0;
    @Getter
    @Setter
    int doVrijeme=0;
    @Getter
    @Setter
    String icao="";
    @Getter
    List<AvionLeti> avioni = new ArrayList<>();

    @Getter
    LetPozicija letPozicija = new LetPozicija();
    @Getter
    String visina = "";
    @Getter
    String vrijeme = "";
    @Getter
    String gPSsirina = "";
    @Getter
    String gPSvisina = "";
    @Getter
    String adresa = "";

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");

    public void preuzmiPodatke() {
        korisnik = prijavaKorisnika.getKorisnik();
        lozinka = prijavaKorisnika.getLozinka();
        //String korisnik, String lozinka, INT odVrijeme, INT doVrijeme, String icao
        icao = pregledAerodroma.getIcao();

        odVrijeme = pretvoriDatumInteger(pregledAerodroma.getOdVrijeme());
        doVrijeme = pretvoriDatumInteger(pregledAerodroma.getDoVrijeme());

    }

    private int pretvoriDatumInteger(String vrijeme) {

        int vrijednost = 0;
        try {
            Date datum = format.parse(vrijeme);
            vrijednost = (int) (datum.getTime() / 1000);
            System.out.println(vrijednost);
        } catch (ParseException ex) {
            Logger.getLogger(PregledAviona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vrijednost;

    }

    public String dajAvione() {
        preuzmiPodatke();
        Zadaca2_1WS zadaca2_1WS = new Zadaca2_1WS();

        avioni = zadaca2_1WS.poletjeliAvioniAerodromi(korisnik, lozinka, odVrijeme, doVrijeme, icao);

        return "";
    }

    public String najvecaVisinaLeta(String icao24, long min, long max) {

        Zadaca2_1WS zadaca2_1WS = new Zadaca2_1WS();
        long medium = (min + max) / 2;
        letPozicija = zadaca2_1WS.najvecaVisinaLetaAviona(korisnik, lozinka, icao24, medium);

        visina = String.valueOf(letPozicija.getBaroAltitude());
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmm"+visina);

        vrijeme = format.format(letPozicija.getTime());
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmm"+vrijeme);
        gPSsirina = String.valueOf(letPozicija.getLatitude());
        
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmm"+gPSsirina);
        gPSvisina = String.valueOf(letPozicija.getLongitude());
        
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmm"+gPSvisina);
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        String token = bpk.getKonfig().dajPostavku("LocationIQ.token");
        LIQKlijent lIQKlijent = new LIQKlijent(token);
        Lokacija loc = new Lokacija(gPSsirina, gPSvisina);
        Adresa adress = lIQKlijent.getAddress(loc);

        adresa = adress.getNaziv();
        
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmm"+adresa);

        return "";
    }

    public String millsDatum(long milis) {
        System.out.println(milis);
        Date date = new Date(milis*1000);
        return this.format.format(date);
    }

}
