package org.foi.nwtis.ilevak.ejb.sb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.foi.nwtis.ilevak.ejb.eb.Airports;
import org.foi.nwtis.ilevak.ejb.eb.Korisnici;
import org.foi.nwtis.ilevak.ejb.eb.Myairports;

/**
 *
 * @author ivale
 */
@ServerEndpoint("/aerodromi")
@Stateless
public class NadzorAerodroma {

    static Queue<Session> queue = new ConcurrentLinkedQueue<>();

    @Inject
    MyairportsFacadeLocal myairportsFacade;

    @Inject

    KorisniciFacadeLocal korisniciFacadeLocal;
    @Inject

    AirportsFacadeLocal airportsFacadeLocal;

    /**
     *Metoda koja šalje poruku klijentu websocketa
     * @param msg
     */
    public static void send(String msg) {
        try {
            for (Session session : queue) {
                session.getBasicRemote().sendText(msg);
                System.out.println(msg);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *Ako je došlo do zaprimanja poruke, dodaje aerodrom u bazu poadataka i šalje poruku klijentu o broju spremljenih aerodroma
     * @param airport
     * @param session
     */
    @OnMessage
    public void onMessage(String airport, Session session) {
        String[] polje = airport.split(";");
        for (String s : polje) {
            System.out.println("OnMessage=" + s);
        }
        Myairports myairports = new Myairports();
        Korisnici korisnik = korisniciFacadeLocal.find(polje[1]);
        Airports airports = airportsFacadeLocal.find(polje[0]);
        Date date = new Date(System.currentTimeMillis());
        if (provjeriPodatke(airports, korisnik)) {
            myairports.setIdent(airports);
            myairports.setUsername(korisnik);
            myairports.setStored(date);
            myairportsFacade.create(myairports);
            send(String.valueOf(myairportsFacade.count()) + ";" + pretvoriDatumVrijeme(date));
        } else {
            send(String.valueOf(myairportsFacade.count()) + ";" + pretvoriDatumVrijeme(date));
        }
    }

    /**
     *Prilikom otvaranja Websocketa(veze) dodaje se sesija u red i ispisuje se "otvorena veza" 
     * Također prilikom kreiranja automatski šalje poruku o broju spremljenih aerodroma
     * @param session
     */
    @OnOpen
    public void openConnection(Session session) {
        queue.add(session);
        System.out.println("Otvorena veza.");
        send(String.valueOf(myairportsFacade.count()) + ";Nije jos osvjezeno");
    }

    /**
     *Ukoliko je došlo do zatvaranja websocketa, miće sesiju iz reda i ispisuje na ekran "zatvorena veza"
     * @param session
     */
    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
        System.out.println("Zatvorena veza.");
    }

    /**
     *Ukoliko je došlo do pogreške websocketa, miće sesiju iz reda i ispisuje na ekran "zatvorena veza"
     * @param session
     * @param t
     */
    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
        System.out.println("Zatvorena veza.");
    }

    /**
     * Vraća false ako su podaci koje korisnik želi unjeti(dodati aerodrom) već
     * dodani
     *
     * @param airports
     * @param korisnici
     * @return
     */
    private boolean provjeriPodatke(Airports airports, Korisnici korisnici) {
        List<Myairports> myairportses = myairportsFacade.findAll();
        for (Myairports m : myairportses) {
            if (m.getUsername() == korisnici && m.getIdent() == airports) {
                return false;
            }
        }
        return true;
    }

    /**
     * vraća formatirani datum
     *
     * @param datum
     * @return
     */
    private String pretvoriDatumVrijeme(Date datum) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        return sdf2.format(datum);
    }
}
