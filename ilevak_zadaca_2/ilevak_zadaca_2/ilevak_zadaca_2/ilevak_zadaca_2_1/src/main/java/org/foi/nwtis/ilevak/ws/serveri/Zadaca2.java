/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ws.serveri;

import java.util.List;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletContext;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.ilevak.web.podaci.AirportDAO;
import org.foi.nwtis.ilevak.web.podaci.KorisnikDAO;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Korisnik;
import org.foi.nwtis.rest.klijenti.OSKlijent;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.rest.podaci.AvionLetiPozicije;
import org.foi.nwtis.rest.podaci.LetPozicija;
import org.foi.nwtis.rest.podaci.Pozicije;

/**
 *
 * @author ivale
 */
@WebService(serviceName = "Zadaca2")
public class Zadaca2 {

//    @Inject
//    @ApplicationMap
//    private Map<String, Object> applicationMap;
    @Inject
    ServletContext context;

    /**
     * dodaje novog korisnika (šalje Korisnik), vraća Boolean. Podaci za
     * datumKreiranja i datumPromjene nemaju ulogu te se ignoriraju. Ostali
     * podaci u objektu Korisnik ne smiju biti null ili prazni.
     *
     * @param noviKorisnik
     * @return
     */
    @WebMethod(operationName = "dodajKorisnika")
    public Boolean dodajKorisnika(@WebParam(name = "noviKorisnik") Korisnik noviKorisnik) {

        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        KorisnikDAO korisnikDAO = new KorisnikDAO();
        if (noviKorisnik != null) {
            return korisnikDAO.dodajKorisnika(noviKorisnik, bpk);
        }
        return false;
    }

    /**
     * provjerava korisničke podatke za autentikaciju, vraća Boolean
     *
     * @param korisnik
     * @param lozinka
     * @return
     */
    @WebMethod(operationName = "provjeraKorisnika")
    public Boolean provjeraKorisnika(@WebParam(name = "korisnik") String korisnik,
            @WebParam(name = "lozinka") String lozinka) {

        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        KorisnikDAO korisnikDAO = new KorisnikDAO();
        Korisnik k = korisnikDAO.dohvatiKorisnika(korisnik, lozinka, Boolean.TRUE, bpk);
        return k != null;
    }

    /**
     * vraća popis svih korisnika, vraća java.util.List<Korisnik>. Kod lozinke
     * se stavlja“******“ zbog privatnosti i sigurnosti.
     *
     * @param korisnik
     * @param lozinka
     * @return
     */
    @WebMethod(operationName = "dajKorisnike")
    public List<Korisnik> dajKorisnike(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        KorisnikDAO korisnikDAO = new KorisnikDAO();
        if (korisnik != null) {
            return korisnikDAO.dohvatiKorisnike(korisnik, lozinka, bpk);
        }
        return null;
    }

    /**
     * ažurira korisničke podatke (šalje Korisnik), vraća Boolean. Ako je
     * lozinka u objektu Korisnik null ili je prazna, tada se ona ne mijenja.
     * Korisničko ime u objektu mora odgovarati parametru za korisničko ime.
     * Podaci za datumKreiranja i datumPromjene nemaju ulogu te se ignoriraju
     *
     * @param korisnik
     * @return
     */
    @WebMethod(operationName = "azurirajKorisnika")
    public Boolean azurirajKorisnika(@WebParam(name = "korisnik") Korisnik korisnik) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        KorisnikDAO korisnikDAO = new KorisnikDAO();
        if (korisnik != null) {
            return korisnikDAO.azurirajKorisnika(korisnik, bpk);
        }
        return false;
    }

    /**
     * 7. vraća popis aerodroma koji imaju sličan naziv koji se traži (šalje
     * naziv), vraća java.util.List<Aerodrom>.
     *
     * @param korisnik
     * @param lozinka
     * @param naziv
     * @return
     */
    @WebMethod(operationName = "dajAerodromeNaziv")
    public List<Aerodrom> dajAerodromeNaziv(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "naziv") String naziv) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        AirportDAO airportDAO = new AirportDAO();
        if (korisnik != null) {
            return airportDAO.dajAerodromeNaziv(bpk, naziv);
        }
        return null;
    }

    /**
     * Wvraća popis aerodroma koji su iz određene države (šalje kod države),
     * vraća java.util.List<Aerodrom>.
     *
     * @param korisnik
     * @param lozinka
     * @param iso_drzave
     * @return
     */
    @WebMethod(operationName = "dajAerodromeDrzava")
    public java.util.List<Aerodrom> dajAerodromeDrzava(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "iso_drzave") String iso_drzave) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        AirportDAO airportDAO = new AirportDAO();
        if (korisnik != null) {
            return airportDAO.dajAerodromeDrzava(bpk, iso_drzave);
        }
        return null;
    }

    /**
     * mojiAerodromi daj vlastite aerodrome korisnika vraća popis svih vlastitih
     * aerodroma, vraća java.util.List<Aerodrom>. Ako nema ni jednog aerodroma
     * vraća null
     *
     * @param korisnik
     * @param lozinka
     * @return
     */
    @WebMethod(operationName = "dajAerodromeKorisnika")
    public java.util.List<Aerodrom> dajAerodromeKorisnika(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        AirportDAO airportDAO = new AirportDAO();
        if (korisnik != null) {
            return airportDAO.dajAerodromeKorisnika(bpk, korisnik);
        }
        return null;
    }

    /**
     * Popis svih korisnika koji imaju vlastite aerodrome korisnciAerodroma
     * vraća popis svih korisnika koji imaju vlastite aerodrome, vraća
     * java.util.List<Korisnik>. Ako nema ni jednog korisnika vraća null.
     *
     * @param korisnik
     * @param lozinka
     * @return
     */
    @WebMethod(operationName = "dajKorisnikeAerodroma")
    public java.util.List<Korisnik> dajKorisnikeAerodroma(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        KorisnikDAO korisnikDAO = new KorisnikDAO();
        if (korisnik != null) {
            return korisnikDAO.dohvatiKorisnikeSAerodromom(korisnik, lozinka, bpk);
        }
        return null;
    }

    /**
     * 11. vraća traženi aerodrom iz vlastitih aerodroma, vraća Aerodrom (šalje
     * se ICAO kod). Ako nema aerodroma vraća null.
     *
     * @param korisnik
     * @param lozinka
     * @param icao
     * @return
     */
    @WebMethod(operationName = "dajMojAerodromIcao")
    public Aerodrom dajMojAerodromIcao(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "icao") String icao) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        AirportDAO airportDAO = new AirportDAO();
        if (korisnik != null) {
            return airportDAO.dajMojAerodromIcao(bpk, icao, korisnik);
        }
        return null;
    }

    /**
     * provjerava da li je aerodrom u njegovoj kolekciji aerodroma koje prati,
     * vraća Boolean (šalje ICAO kod)
     *
     * @param korisnik
     * @param lozinka
     * @param icao
     * @return
     */
    @WebMethod(operationName = "provjeriMojAerodromIcao")
    public Boolean provjeriMojAerodromIcao(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "icao") String icao) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        AirportDAO airportDAO = new AirportDAO();
        if (korisnik != null) {
            return airportDAO.provjeriMojAerodromIcao(bpk, korisnik, icao);
        }
        return false;
    }

    /**
     * dodaje aerodrom u vlastite aerodrome (šalje se ICAO kod), vraća Boolean.
     * Mora postojati aerodrom u tablici AIRPORTS s tim kodom (ICAO = ident).
     *
     * @param korisnik
     * @param lozinka
     * @param icao
     * @return
     */
    @WebMethod(operationName = "dodajMojAerodrom")
    public Boolean dodajMojAerodrom(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "icao") String icao) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        AirportDAO airportDAO = new AirportDAO();
        if (korisnik != null) {
            return airportDAO.dodajMojAerodrom(bpk, korisnik, icao);
        }
        return false;
    }

    /**
     * vraća najveću visinu leta odabranog aviona i njenu geo lokaciju (šalje se
     * ICAO24 kod, vrijeme) kao UNIX timestamp, vraća LetPozicija. Ako nema ni
     * jedne pozicije leta aviona vraća null. Podaci svih pozicija leta
     * preuzimaju se od web servisa Open Sky Network putem klase OSKlijent i
     * njegove metode getTracks(...). Potrebno je pronaći koja od njih ima
     * najveću vrijednost te se ona vraća
     *
     * @param korisnik
     * @param lozinka
     * @param icao24
     * @param zaVrijeme
     * @return
     */
    @WebMethod(operationName = "najvecaVisinaLetaAviona")
    public LetPozicija najvecaVisinaLetaAviona(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka,
            @WebParam(name = "icao24") String icao24, @WebParam(name = "zaVrijeme") long zaVrijeme) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        String osnKorisnik = bpk.getKonfig().dajPostavku("OpenSkyNetwork.korisnik");
        String osnLozinka = bpk.getKonfig().dajPostavku("OpenSkyNetwork.lozinka");

        OSKlijent osklijent = new OSKlijent(osnKorisnik, osnLozinka);

        AvionLetiPozicije pozicijeLetaAviona = osklijent.getTracks(icao24, zaVrijeme);
        LetPozicija letPozicija = new LetPozicija();
        letPozicija.setBaro_altitude(0);
        for (LetPozicija let : pozicijeLetaAviona.getPath()) {
            if (let.getBaro_altitude() > letPozicija.getBaro_altitude()) {
                letPozicija = let;
            }
        }
        System.out.println(letPozicija.getBaro_altitude());
        return letPozicija;
    }

    /**
     * vraća popis svih aviona koji su polijetali sa zadanog aerodroma u
     * određenom razdoblju (šalje se ICAO kod, od, do). Od i do su UNIX
     * timestamp, vraća java.util.List<AvionLeti>. Ako nema ni jednog aviona
     * vraća null.
     *
     * @param korisnik
     * @param lozinka
     * @param doVrijeme
     * @param odVrijeme
     * @param icao
     * @return
     */
    @WebMethod(operationName = "poletjeliAvioniAerodromi")
    public List<AvionLeti> poletjeliAvioniAerodromi(@WebParam(name = "korisnik") String korisnik, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "do") int doVrijeme, @WebParam(name = "od") int odVrijeme, @WebParam(name = "icao") String icao) {
        //AvionLeti(String icao24, int firstSeen, String estDepartureAirport, int lastSeen, String estArrivalAirport, String callsign, int estDepartureAirportHorizDistance, 
        //int estDepartureAirportVertDistance, int estArrivalAirportHorizDistance, int estArrivalAirportVertDistance, int departureAirportCandidatesCount, int arrivalAirportCandidatesCount) 
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        AirportDAO airportDAO = new AirportDAO();
        if (korisnik != null) {
            return airportDAO.dajAvioneLeti(bpk, icao, odVrijeme, doVrijeme);
        }
        return null;
    }
}
