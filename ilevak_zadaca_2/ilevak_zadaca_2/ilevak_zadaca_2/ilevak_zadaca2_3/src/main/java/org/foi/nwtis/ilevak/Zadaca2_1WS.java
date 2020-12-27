/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.ilevak.ws.serveri.Aerodrom;
import org.foi.nwtis.ilevak.ws.serveri.AvionLeti;
import org.foi.nwtis.ilevak.ws.serveri.Korisnik;
import org.foi.nwtis.ilevak.ws.serveri.LetPozicija;

/**
 *
 * @author ivale
 */
public class Zadaca2_1WS {

    public List<Aerodrom> dajAerodromeKorisnika(String korisnik, String lozinka) {
        List<Aerodrom> aerodromi = null;

        try {
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();

            aerodromi = port.dajAerodromeKorisnika(korisnik, lozinka);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return aerodromi;

    }

    public Boolean dodajMojAerodrom(String korisnik, String lozinka, String icao) {
        boolean rezultat = false;
        try {
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();
            rezultat = port.dodajMojAerodrom(korisnik, lozinka, icao);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return rezultat;
    }

    public List<Aerodrom> dajAerodromeNaziv(String korisnik, String lozinka, String naziv) {
        List<Aerodrom> aerodromi = null;
        try {
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();
            aerodromi = port.dajAerodromeNaziv(korisnik, lozinka, naziv);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return aerodromi;
    }

    public List<Aerodrom> dajAerodromeDrzava(String korisnik, String lozinka, String drzava) {
        List<Aerodrom> aerodromi = null;
        try {
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();
            aerodromi = port.dajAerodromeDrzava(korisnik, lozinka, drzava);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return aerodromi;
    }

    public Aerodrom dajMojAerodromIcao(String korisnik, String lozinka, String icao) {
        Aerodrom aerodrom = null;

        try { // Call Web Service Operation
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();
            aerodrom = port.dajMojAerodromIcao(korisnik, lozinka, icao);

        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }

        return aerodrom;
    }

    public List<AvionLeti> poletjeliAvioniAerodromi(String korisnik, String lozinka, int odVrijeme, int doVrijeme, String icao) {
        List<AvionLeti> avioni = null;

        try {
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();
            avioni = port.poletjeliAvioniAerodromi(korisnik, lozinka, doVrijeme, odVrijeme, icao);
        } catch (Exception ex) {
        }

        return avioni;
    }

    public LetPozicija najvecaVisinaLetaAviona(String korisnik, String lozinka, String icao24, long zaVrijeme) {
        LetPozicija let = null;

        try {
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();

            let = port.najvecaVisinaLetaAviona(korisnik, lozinka, icao24, zaVrijeme);

            return let;

        } catch (Exception ex) {
        }
        return let;
    }

    public Boolean dodajKorisnika(Korisnik noviKorisnik) {
        boolean rezultat = false;
        try {
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();
            rezultat = port.dodajKorisnika(noviKorisnik);
        } catch (Exception ex) {
        }
        return rezultat;
    }

    public Boolean provjeraKorisnika(String korisnik, String lozinka) {
        //true ako je uspio dohvatit korisnika
        boolean rezultat = false;
        try {
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service service = new org.foi.nwtis.ilevak.ws.serveri.Zadaca2_Service();
            org.foi.nwtis.ilevak.ws.serveri.Zadaca2 port = service.getZadaca2Port();
            rezultat = port.provjeraKorisnika(korisnik, lozinka);
        } catch (Exception ex) {
        }

        return rezultat;
    }

}
