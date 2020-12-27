/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.rest.serveri;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import org.foi.nwtis.ilevak.ws.klijenti.Zadaca2_1WS;
import org.foi.nwtis.ilevak.ws.serveri.Aerodrom;
import org.foi.nwtis.podaci.Icao;

import org.foi.nwtis.podaci.Odgovor;
import org.foi.nwtis.podaci.OdgovorAerodrom;

/**
 * REST Web Service
 *
 * @author ivale
 */
@Path("aerodromi")
public class Zadaca2RestAerodromi {

    @Context
    private UriInfo context;

    
    private int status;
    /**
     * Creates a new instance of Zadaca2RestAerodromi
     */
    public Zadaca2RestAerodromi() {
    }

    /**
     * Vraća aerodrome koje prati korisnik
     *
     * @param korisnik
     * @param lozinka
     * @return an instance of Response
     */
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response dajAerodomeKorisnika(
            @HeaderParam("korisnik") String korisnik,
            @HeaderParam("lozinka") String lozinka) {

        Odgovor odgovor = new Odgovor();
        if (korisnik != null && lozinka != null) {
            Zadaca2_1WS zadaca2_1WS = new Zadaca2_1WS();
            List<Aerodrom> aerodromi = zadaca2_1WS.dajAerodromeKorisnika(korisnik, lozinka);

            if (aerodromi == null) {
                aerodromi = new ArrayList<>();
                odgovor.setStatus("40");
                odgovor.setPoruka("Nema spremljenih vlastitih aerodroma");
            } else {
                odgovor.setStatus("10");
                odgovor.setPoruka("OK");
                odgovor.setOdgovor(aerodromi.toArray());
            }
        } else {
            odgovor.setStatus("40");
            odgovor.setPoruka("Niste upisali dobre podatke!!");
        }
        return Response
                .ok(odgovor)
                .status(200)
                .build();
    }

    /**
     *Dodaje aerodrom korisnika putem icao
     * @param korisnik
     * @param lozinka
     * @param icao
     * @return
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response dodajMojAerodrom(
            @HeaderParam("korisnik") String korisnik,
            @HeaderParam("lozinka") String lozinka,
            Icao icao) {

        Odgovor odgovor = new Odgovor();
        if (korisnik != null && lozinka != null && icao != null) {
            Zadaca2_1WS zadaca2_1WS = new Zadaca2_1WS();
            Boolean rezultat = zadaca2_1WS.dodajMojAerodrom(korisnik, lozinka, icao.getIcao());

            if (rezultat == false) {
                odgovor.setStatus("40");
                odgovor.setPoruka("Nije mogće dodati aerodrom sa icao: " + icao.getIcao()+ " jer on ne postoji ili je vec dodan u vase aerodrome");
            } else {
                odgovor.setStatus("10");
                odgovor.setPoruka("Uspijesno dodan aerodrom u MojeAerodrome");
            }
        } else {
            odgovor.setStatus("40");
            odgovor.setPoruka("Niste upisali dobre podatke!!");
        }
        return Response
                .ok(odgovor)
                .status(200)
                .build();
    }

    /**
     *Vraća određeni aerodrom s obzirom na to je li slan naziv država
     * Ako je su naziv i država prazni onda vraća sve aerodrome
     * @param korisnik
     * @param lozinka
     * @param naziv
     * @param drzava
     * @return
     */
    @GET
    @Path("/svi")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response dajAerodromNazivDrzava(
            @HeaderParam("korisnik") String korisnik,
            @HeaderParam("lozinka") String lozinka,
            @QueryParam("naziv") String naziv,
            @QueryParam("drzava") String drzava) {

        Odgovor odgovor = new Odgovor();
        if (korisnik != null && lozinka != null) {
            Zadaca2_1WS zadaca2_1WS = new Zadaca2_1WS();
            List<Aerodrom> aerodromi = new ArrayList<>();
            if (drzava == null) {
                if (naziv == null) {
                    aerodromi = zadaca2_1WS.dajAerodromeNaziv(korisnik, lozinka, "%");
                } else {
                    aerodromi = zadaca2_1WS.dajAerodromeNaziv(korisnik, lozinka, naziv);
                }
            } else {
                aerodromi = zadaca2_1WS.dajAerodromeDrzava(korisnik, lozinka, drzava);
            }
            if (aerodromi == null && naziv == null) {
                aerodromi = new ArrayList<>();
                odgovor.setStatus("40");
                odgovor.setPoruka("Nema spremljenih aerodroma sa navedenim drzavom: " + drzava);
            } else if (aerodromi == null && drzava == null) {
                aerodromi = new ArrayList<>();
                odgovor.setStatus("40");
                odgovor.setPoruka("Nema spremljenih aerodroma sa navedenom nazivom: " + naziv);
            } else {
                odgovor.setStatus("10");
                
                odgovor.setPoruka("OK");
                odgovor.setOdgovor(aerodromi.toArray());
            }
        } else {
            odgovor.setStatus("40");
            odgovor.setPoruka("Niste upisali dobre podatke!!");
        }
        return Response
                .ok(odgovor)
                .status(200)
                .build();
    }

    /**
     *Vraća određeni aerodrom na temelju icao koji je korisnik upisao
     * @param korisnik
     * @param lozinka
     * @param icao
     * @return
     */
    @GET
    @Path("{icao}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response dajMojAerodromIcao(
            @HeaderParam("korisnik") String korisnik,
            @HeaderParam("lozinka") String lozinka,
            @PathParam("icao") String icao) {

        Odgovor odgovor = new Odgovor();
        if (korisnik != null && lozinka != null && icao!=null) {
            Zadaca2_1WS zadaca2_1WS = new Zadaca2_1WS();
            Aerodrom aerodrom = zadaca2_1WS.dajMojAerodromIcao(korisnik, lozinka, icao);;

            if (aerodrom == null) {
                odgovor.setStatus("40");
                odgovor.setPoruka("Nije mogće dodati aerodrom sa icao: " + icao + " jer on ne postoji ili je vec dodan u vase aerodrome");
            } else {
                odgovor.setStatus("10");
                odgovor.setPoruka("OK");
                Aerodrom[] a = {aerodrom};
                odgovor.setOdgovor(a);

            }
        } else {
            odgovor.setStatus("40");
            odgovor.setPoruka("Niste upisali dobre podatke!!");
        }
        return Response
                .ok(odgovor)
                .status(200)
                .build();
    }
    
    

}
