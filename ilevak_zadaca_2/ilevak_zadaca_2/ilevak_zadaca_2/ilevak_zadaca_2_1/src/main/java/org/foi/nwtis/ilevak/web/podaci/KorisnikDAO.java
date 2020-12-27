/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.podaci;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ilevak.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.podaci.Korisnik;

/**
 *
 * @author dkermek
 */
public class KorisnikDAO {

    /**
     *
     * @param korisnik
     * @param lozinka
     * @param prijava
     * @param bpk
     * @return
     */
    public Korisnik dohvatiKorisnika(String korisnik, String lozinka, Boolean prijava, BP_Konfiguracija bpk) {

        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT IME, PREZIME, EMAIL_ADRESA, KOR_IME, LOZINKA, DATUM_KREIRANJA, DATUM_PROMJENE FROM korisnici WHERE "
                + "KOR_IME = '" + korisnik + "'";

        if (prijava) {
            upit += " and LOZINKA = '" + lozinka + "'";
        }

        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {

                while (rs.next()) {
                    String korime = rs.getString("kor_ime");
                    String ime = rs.getString("ime");
                    String prezime = rs.getString("prezime");
                    String email = rs.getString("email_adresa");
                    Timestamp datumKreiranja = null;
                    Timestamp datumPromjene = null;
                    datumKreiranja = rs.getTimestamp("datum_kreiranja");
                    datumPromjene = rs.getTimestamp("datum_promjene");

                    Korisnik k = new Korisnik(korime, ime, prezime, "******", email, datumKreiranja, datumPromjene);
                    return k;
                }

            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Korisnik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     *
     * @param korisnik
     * @param lozinka
     * @param bpk
     * @return
     */
    public List<Korisnik> dohvatiKorisnike(String korisnik, String lozinka, BP_Konfiguracija bpk) {

        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT IME, PREZIME, EMAIL_ADRESA, KOR_IME, LOZINKA, DATUM_KREIRANJA, DATUM_PROMJENE FROM korisnici";
        List<Korisnik> korisnici=new ArrayList<Korisnik>();
        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {

                while (rs.next()) {
                    String korime = rs.getString("kor_ime");
                    String ime = rs.getString("ime");
                    String prezime = rs.getString("prezime");
                    String email = rs.getString("email_adresa");
                    Timestamp datumKreiranja = null;
                    Timestamp datumPromjene = null;
                    datumKreiranja = rs.getTimestamp("datum_kreiranja");
                    datumPromjene = rs.getTimestamp("datum_promjene");

                    Korisnik k = new Korisnik(korime, ime, prezime, "******", email, datumKreiranja, datumPromjene);
                    korisnici.add(k);
                }
                return korisnici;
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Korisnik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param k
     * @param bpk
     * @return
     */
    public boolean azurirajKorisnika(Korisnik k, BP_Konfiguracija bpk) {
        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();

        String upit = "UPDATE korisnici SET IME = '" + k.getIme() + "', PREZIME = '" + k.getPrezime()
                + "', EMAIL_ADRESA = '" + k.getEmailAdresa() + "'";

        if (!provjeriKorisnika(k)) {
            return false;
        }
        if (!k.getLozinka().trim().isEmpty() && k.getLozinka() != null) {
            upit=upit+", LOZINKA = '"+k.getLozinka()+"'";
        }
        upit=upit+" WHERE KOR_IME='"+k.getKorIme()+"' ";

        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
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
     *
     * @param korisnik
     * @param lozinka
     * @param bpk
     * @return
     */
    public List<Korisnik> dohvatiKorisnikeSAerodromom(String korisnik, String lozinka, BP_Konfiguracija bpk) {

        String url = bpk.getServerDatabase() + bpk.getUserDatabase();
        String bpkorisnik = bpk.getUserUsername();
        String bplozinka = bpk.getUserPassword();
        String upit = "SELECT IME, PREZIME, KOR_IME, LOZINKA, EMAIL_ADRESA, DATUM_KREIRANJA, "
                + "DATUM_PROMJENE FROM KORISNICI, (SELECT USERNAME FROM MYAIRPORTS GROUP BY USERNAME) x "
                + "WHERE KORISNICI.KOR_IME=x.USERNAME ";
        List<Korisnik> korisnici=new ArrayList<Korisnik>();
        try {
            Class.forName(bpk.getDriverDatabase(url));

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {

                while (rs.next()) {
                    String korime = rs.getString("kor_ime");
                    String ime = rs.getString("ime");
                    String prezime = rs.getString("prezime");
                    String email = rs.getString("email_adresa");
                    Timestamp datumKreiranja = null;
                    Timestamp datumPromjene = null;
                    datumKreiranja = rs.getTimestamp("datum_kreiranja");
                    datumPromjene = rs.getTimestamp("datum_promjene");

                    Korisnik k = new Korisnik(korime, ime, prezime, "******", email, datumKreiranja, datumPromjene);
                    korisnici.add(k);
                }
                return korisnici;
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Korisnik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param k
     * @param bpk
     * @return
     */
    public boolean dodajKorisnika(Korisnik k, BP_Konfiguracija bpk) {
        if (provjeriKorisnika(k) && !k.getLozinka().trim().isEmpty() && k.getLozinka() != null) {
            String url = bpk.getServerDatabase() + bpk.getUserDatabase();
            String bpkorisnik = bpk.getUserUsername();
            String bplozinka = bpk.getUserPassword();
            String upit = "INSERT INTO korisnici (IME, PREZIME, EMAIL_ADRESA, KOR_IME, LOZINKA, DATUM_KREIRANJA, DATUM_PROMJENE) VALUES ("
                    + "'" + k.getIme() + "', '" + k.getPrezime()
                    + "', '" + k.getEmailAdresa() + "', '" + k.getKorIme() + "', '" + k.getLozinka() + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

            try {
                Class.forName(bpk.getDriverDatabase(url));

                try (
                        Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
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
        return false;

    }

    private boolean provjeriKorisnika(Korisnik k) {
        if (k.getIme() != null && k.getEmailAdresa() != null && k.getKorIme() != null && k.getPrezime() != null
                && !k.getIme().trim().isEmpty() && !k.getEmailAdresa().trim().isEmpty() && !k.getPrezime().trim().isEmpty()
                && !k.getKorIme().trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
