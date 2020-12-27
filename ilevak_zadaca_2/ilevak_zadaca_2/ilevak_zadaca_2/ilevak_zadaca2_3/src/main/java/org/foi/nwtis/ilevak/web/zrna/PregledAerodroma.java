/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.ilevak.rest.klijenti.Zadaca2_2RS;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Icao;
import org.foi.nwtis.podaci.OdgovorAerodrom;
import org.foi.nwtis.rest.klijenti.LIQKlijent;
import org.foi.nwtis.rest.klijenti.OWMKlijent;
import org.foi.nwtis.rest.podaci.Lokacija;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

/**
 *
 * @author ivale
 */
@Named(value = "pregledAerodroma")
@SessionScoped
public class PregledAerodroma implements Serializable {

    
    //01.05.2020. 00:00:01
    //15.05.2020. 00:00:01
    //CYUL
    @Inject
    PrijavaKorisnika prijavaKorisnika;
    
    @Inject
    ServletContext context;
    
    String korisnik = "";
    String lozinka = "";
    Lokacija lok;
    @Getter
    @Setter
    String odVrijeme="";
    @Getter
    @Setter
    String doVrijeme="";
    @Getter
    @Setter
    String gpsBPSirina;
    @Getter
    @Setter
    String gpsNASirina;
    @Getter
    @Setter
    String gpsBPduzina;
    @Getter
    @Setter
    String gpsNAduzina;
    @Getter
    @Setter
    String temperatura;
    @Getter
    @Setter
    String vlaga;
    @Getter
    List<Aerodrom> aerodromi;
    @Getter
    @Setter
    List<Aerodrom> aerodromiKorisnika;
    @Getter
    @Setter
    String icao;

    public void preuzmiPodatkeKorisnika() {
        korisnik = prijavaKorisnika.getKorisnik();
        lozinka = prijavaKorisnika.getLozinka();

    }
    
    public void dajMojAerodromIcao(String icao){
        preuzmiPodatkeKorisnika();
        Zadaca2_2RS zadaca2_2RS = new Zadaca2_2RS(korisnik, lozinka);
        OdgovorAerodrom odgovor = zadaca2_2RS.dajMojAerodromIcao(OdgovorAerodrom.class, icao);
       //long-duz
        gpsBPSirina=odgovor.getOdgovor()[0].getLokacija().getLatitude();
        gpsBPduzina=odgovor.getOdgovor()[0].getLokacija().getLongitude();
        lok=odgovor.getOdgovor()[0].getLokacija();
        
    }
    
    public String dajGPSPodatke(String icao, String naziv){
        dajMojAerodromIcao(icao);
        dajLIQGPS(naziv);
        dajOWMMeteo();
        
        return "";
    }
    
    private void dajOWMMeteo() {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        String apikey = bpk.getKonfig().dajPostavku("OpenWeatherMap.apikey");
        OWMKlijent oWMKlijent=new OWMKlijent(apikey);
        MeteoPodaci meteopodaci=oWMKlijent.getRealTimeWeather(gpsNASirina, gpsNAduzina);
        temperatura=meteopodaci.getTemperatureValue().toString();
        vlaga=meteopodaci.getHumidityValue().toString();
     
     }
    private void dajLIQGPS(String naziv) {
        
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        String token = bpk.getKonfig().dajPostavku("LocationIQ.token");
        LIQKlijent lIQKlijent=new LIQKlijent(token);
        gpsNASirina=lIQKlijent.getGeoLocation(naziv).getLatitude();
        gpsNAduzina=lIQKlijent.getGeoLocation(naziv).getLongitude();
        
    }
 

    public String dajMojeAerodrome() {
        preuzmiPodatkeKorisnika();
        Zadaca2_2RS zadaca2_2RS = new Zadaca2_2RS(korisnik, lozinka);
        OdgovorAerodrom odgovor = zadaca2_2RS.dajAerodomeKorisnika(OdgovorAerodrom.class);
        aerodromiKorisnika = new ArrayList(Arrays.asList(odgovor.getOdgovor()));
        List<Aerodrom> obrisi = new ArrayList<>();
        return "";
    }
    
    
    public void spremiIcao(String icao){
        this.icao=icao;
        System.out.println(icao);
        System.out.println(odVrijeme);
        System.out.println(doVrijeme);
    }

    

   

   

}
