/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Airport;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.rest.podaci.Lokacija;

/**
 *
 * @author ivale
 */
public class AirportDAO {

    /**
     *
     * @param bpk
     * @param drzava
     * @return
     */
    public List<Airport> dajSveAerodrome(BP_Konfiguracija bpk, String drzava) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT ident, type, name, elevation_ft, continent, iso_country, "
                + "iso_region, municipality, gps_code, iata_code, local_code, coordinates FROM AIRPORTS ";

        if (drzava != null && !drzava.trim().isEmpty()) {
            if (drzava.compareTo("*") == 0) {
            } else {
                upit += " WHERE iso_country = '" + drzava + "'";
            }
        }

        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {
                List<Airport> aerodromi = new ArrayList<>();
                while (rs.next()) {
                    String ident = rs.getString("ident");
                    String type = rs.getString("type");
                    String name = rs.getString("name");
                    String elevation_ft = rs.getString("elevation_ft");
                    String continent = rs.getString("continent");
                    String iso_country = rs.getString("iso_country");
                    String iso_region = rs.getString("iso_region");
                    String municipality = rs.getString("municipality");
                    String gps_code = rs.getString("gps_code");
                    String iata_code = rs.getString("iata_code");
                    String local_code = rs.getString("local_code");
                    String coordinates = rs.getString("coordinates");
                    Airport a = new Airport(ident, type, name, elevation_ft, continent, iso_country, iso_region, municipality, gps_code, iata_code, local_code, coordinates);

                    aerodromi.add(a);
                }
                return aerodromi;

            } catch (SQLException ex) {
                Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param bpk
     * @param naziv
     * @return
     */
    public List<Aerodrom> dajAerodromeNaziv(BP_Konfiguracija bpk, String naziv) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT ident, name,  iso_country, "
                + " coordinates FROM AIRPORTS WHERE name "
                + "LIKE '" + naziv + "'  ";

        return pribavljanjePodatakaBaza(bpk, url, bpkorisnik, bplozinka, upit);
    }

    private List<Aerodrom> pribavljanjePodatakaBaza(BP_Konfiguracija bpk, String url, String bpkorisnik, String bplozinka, String upit) {
        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {
                List<Aerodrom> aerodromi = new ArrayList<>();
                while (rs.next()) {
                    String ident = rs.getString("ident");
                    String name = rs.getString("name");
                    String drzava = rs.getString("iso_country");
                    Lokacija lokacija = new Lokacija();
                    String[] koordinate = rs.getString("coordinates").split(",");
                    lokacija.postavi(koordinate[1].trim(), koordinate[0].trim());
                    Aerodrom a = new Aerodrom(ident, name, drzava, lokacija);

                    aerodromi.add(a);

                }
                return aerodromi;

            } catch (SQLException ex) {
                Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param bpk
     * @param drzava
     * @return
     */
    public List<Aerodrom> dajAerodromeDrzava(BP_Konfiguracija bpk, String drzava) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT ident, name,  iso_country, "
                + " coordinates FROM AIRPORTS WHERE iso_country "
                + "LIKE '" + drzava + "'  ";

        return pribavljanjePodatakaBaza(bpk, url, bpkorisnik, bplozinka, upit);
    }

    /**
     *
     * @param bpk
     * @param korisnik
     * @return
     */
    public List<Aerodrom> dajAerodromeKorisnika(BP_Konfiguracija bpk, String korisnik) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT myairports.ident, name,  iso_country,  coordinates FROM MYAIRPORTS, AIRPORTS "
                + "WHERE MYAIRPORTS.IDENT=AIRPORTS.IDENT AND MYAIRPORTS.USERNAME='" + korisnik + "'";

        return pribavljanjePodatakaBaza(bpk, url, bpkorisnik, bplozinka, upit);
    }

    /**
     *
     * @param bpk
     * @param korisnik
     * @param icao
     * @return
     */
    public Boolean provjeriMojAerodromIcao(BP_Konfiguracija bpk, String korisnik, String icao) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT * FROM MYAIRPORTS "
                + "WHERE MYAIRPORTS.USERNAME='" + korisnik + "' AND MYAIRPORTS.IDENT='" + icao + "'";

        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {
                while (rs.next()) {
                    return true;
                }
                return false;

            } catch (SQLException ex) {
                Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param bpk
     * @param icao
     * @param korisnik
     * @return
     */
    public Aerodrom dajMojAerodromIcao(BP_Konfiguracija bpk, String icao, String korisnik) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT AIRPORTS.ident, name,  iso_country, coordinates FROM AIRPORTS, "
                + "MYAIRPORTS WHERE AIRPORTS.IDENT=MYAIRPORTS.IDENT AND MYAIRPORTS.IDENT='" + icao + "'"
                + " AND USERNAME='" + korisnik + "'";

        if (pribavljanjePodatakaBaza(bpk, url, bpkorisnik, bplozinka, upit).isEmpty()) {
            return null;
        }
        return pribavljanjePodatakaBaza(bpk, url, bpkorisnik, bplozinka, upit).get(0);
    }

    /**
     *
     * @param bpk
     * @param korisnik
     * @param icao
     * @return
     */
    public Boolean dodajMojAerodrom(BP_Konfiguracija bpk, String korisnik, String icao) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();

        if (!provjeriMojAerodromIcao(bpk, korisnik, icao)) {
            String upit = "INSERT INTO MYAIRPORTS (IDENT, USERNAME, STORED) VALUES('" + icao + "','" + korisnik + "', CURRENT_TIMESTAMP)";

            try {
                Class.forName(bpk.getDriverDatabase(url));

                try (Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                        Statement s = con.createStatement()) {

                    int brojAzuriranja = s.executeUpdate(upit);

                    return brojAzuriranja == 1;

                } catch (SQLException ex) {
                    Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    /**
     *
     * @param bpk
     * @param icao
     * @return
     */
    public Boolean provjeriAerodrom(BP_Konfiguracija bpk, String icao) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT * FROM AIRPORTS WHERE ident='" + icao + "'";
        return !pribavljanjePodatakaBaza(bpk, url, bpkorisnik, bplozinka, upit).isEmpty();
    }

    public boolean provjeriAerodromLog(Airport a, BP_Konfiguracija bpk, String datum) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT * FROM MYAIRPORTSLOG WHERE ident='" + a.getIdent() + "' AND FLIGHTDATE='" + datum + "'";
        try {
            Class.forName(bpk.getDriverDatabase(url));
            try (Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {
                while (rs.next()) {
                    return false;
                }
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public List<Airport> dajMojeAerodrome(BP_Konfiguracija bpk) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT AIRPORTS.ident, type, name, elevation_ft, continent, iso_country, "
                + "iso_region, municipality, gps_code, iata_code, local_code, coordinates FROM AIRPORTS, MYAIRPORTS "
                + "WHERE MYAIRPORTS.ident=AIRPORTS.ident";
        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {
                List<Airport> aerodromi = new ArrayList<>();
                while (rs.next()) {
                    String ident = rs.getString("ident");
                    String type = rs.getString("type");
                    String name = rs.getString("name");
                    String elevation_ft = rs.getString("elevation_ft");
                    String continent = rs.getString("continent");
                    String iso_country = rs.getString("iso_country");
                    String iso_region = rs.getString("iso_region");
                    String municipality = rs.getString("municipality");
                    String gps_code = rs.getString("gps_code");
                    String iata_code = rs.getString("iata_code");
                    String local_code = rs.getString("local_code");
                    String coordinates = rs.getString("coordinates");
                    Airport a = new Airport(ident, type, name, elevation_ft, continent, iso_country, iso_region, municipality, gps_code, iata_code, local_code, coordinates);

                    aerodromi.add(a);
                }
                return aerodromi;

            } catch (SQLException ex) {
                Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean dodajPoadtkeBazeAvioni(AvionLeti avion, BP_Konfiguracija bpk) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "INSERT INTO AIRPLANES (ICAO24, FIRSTSEEN, ESTDEPARTUREAIRPORT, "
                + "LASTSEEN, ESTARRIVALAIRPORT, CALLSIGN, ESTDEPARTUREAIRPORTHORIZDISTANCE, "
                + "ESTDEPARTUREAIRPORTVERTDISTANCE, ESTARRIVALAIRPORTHORIZDISTANCE, "
                + "ESTARRIVALAIRPORTVERTDISTANCE, DEPARTUREAIRPORTCANDIDATESCOUNT, "
                + "ARRIVALAIRPORTCANDIDATESCOUNT , STORED) VALUES "
                + " ('" + avion.getIcao24() + "', " + avion.getFirstSeen() + ", '" + avion.getEstDepartureAirport() + "', "
                + " " + avion.getLastSeen() + ", '" + avion.getEstArrivalAirport() + "', '" + avion.getCallsign() + "', "
                + "" + avion.getEstDepartureAirportHorizDistance() + ", " + avion.getEstDepartureAirportVertDistance() + ", "
                + "" + avion.getEstArrivalAirportHorizDistance() + ", " + avion.getEstArrivalAirportVertDistance() + ", "
                + "" + avion.getDepartureAirportCandidatesCount() + ", " + avion.getArrivalAirportCandidatesCount() + ", "
                + "CURRENT_TIMESTAMP)";
        if (!provjeriAvion(bpk, avion.getIcao24()) && avion.getEstArrivalAirport() != null && !avion.getEstArrivalAirport().trim().isEmpty()) {
            try {
                Class.forName(bpk.getDriverDatabase(url));
                try (Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                        Statement s = con.createStatement()) {
                    int brojAzuriranja = s.executeUpdate(upit);

                    return brojAzuriranja == 1;

                } catch (SQLException ex) {
                    Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    private boolean provjeriAvion(BP_Konfiguracija bpk, String icao24) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT * FROM AIRPLANES WHERE ICAO24='" + icao24 + "'";
        try {
            Class.forName(bpk.getDriverDatabase(url));
            try (Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {
                while (rs.next()) {
                    return true;
                }
                return false;
            } catch (SQLException ex) {
                Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean dodajZapisULog(Airport aerodrom, BP_Konfiguracija bpk, int pocetak) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String datum=millsDatum(pocetak);
        String upit = "INSERT INTO MYAIRPORTSLOG(IDENT, FLIGHTDATE, STORED) VALUES ('"+aerodrom.getIdent()+"', '"+
                datum+"', CURRENT_TIMESTAMP)";
        try {
            Class.forName(bpk.getDriverDatabase(url));
            try (Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement()) {
                int brojAzuriranja = s.executeUpdate(upit);

                return brojAzuriranja == 1;

            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
        /**
     * Metoda pretvara milisekunde u string datuma
     *
     * @param milis
     * @return
     */
    public String millsDatum(int milis) {
        Date date = new Date(milis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(date);
    }

    public List<AvionLeti> dajAvioneLeti(BP_Konfiguracija bpk, String icao, int odVrijeme, int doVrijeme) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT ICAO24, FIRSTSEEN, ESTDEPARTUREAIRPORT, "
                + "LASTSEEN, ESTARRIVALAIRPORT, CALLSIGN, ESTDEPARTUREAIRPORTHORIZDISTANCE, "
                + "ESTDEPARTUREAIRPORTVERTDISTANCE, ESTARRIVALAIRPORTHORIZDISTANCE, "
                + "ESTARRIVALAIRPORTVERTDISTANCE, DEPARTUREAIRPORTCANDIDATESCOUNT, "
                + "ARRIVALAIRPORTCANDIDATESCOUNT , STORED FROM AIRPLANES"
                + " WHERE ESTDEPARTUREAIRPORT='"+icao+"' AND FIRSTSEEN>"+odVrijeme+" AND LASTSEEN<"+doVrijeme+"";
        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {
                List<AvionLeti> avioni = new ArrayList<>();
                while (rs.next()) {
                    String icao24 = rs.getString("icao24");
                    int firstSeen =Integer.parseInt(rs.getString("firstseen"));
                    String estDepartureAirport = rs.getString("estdepartureairport");
                    int lastSeen = Integer.parseInt(rs.getString("lastseen"));
                    String estArrivalAirport = rs.getString("estArrivalairport");
                    String callsign = rs.getString("callsign");
                    int estDepartureAirportHorizDistance = Integer.parseInt(rs.getString("estdepartureairporthorizdistance"));
                    int estDepartureAirportVertDistance = Integer.parseInt(rs.getString("estdepartureairportvertdistance"));
                    int estArrivalAirportHorizDistance = Integer.parseInt(rs.getString("estarrivalairporthorizdistance"));
                    int estArrivalAirportVertDistance = Integer.parseInt(rs.getString("estarrivalairportvertdistance"));
                    int departureAirportCandidatesCount =Integer.parseInt( rs.getString("departureairportcandidatescount"));
                    int arrivalAirportCandidatesCount = Integer.parseInt(rs.getString("arrivalairportcandidatescount"));
                    AvionLeti avionLeti=new AvionLeti(icao24, firstSeen, estDepartureAirport, lastSeen, estArrivalAirport,
                            callsign, estDepartureAirportHorizDistance, estDepartureAirportVertDistance, estArrivalAirportHorizDistance, 
                            estArrivalAirportVertDistance, departureAirportCandidatesCount, arrivalAirportCandidatesCount);

                    avioni.add(avionLeti);
                }
                return avioni;

            } catch (SQLException ex) {
                Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AirportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
