/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.ilevak.Zadaca2_1WS;

/**
 *
 * @author ivale
 */
@Named(value = "prijavaKorisnika")
@SessionScoped
public class PrijavaKorisnika implements Serializable {

    
    //TODO:tu treba daodati povlacenje tih podataka kao stvarni podaci
    @Getter
    @Setter
    String korisnik="";
    @Getter
    @Setter
    String lozinka="";
    @Getter
    String poruka="";
    /**
     * Creates a new instance of PrijavaKorisnika
     */
    public PrijavaKorisnika() {
        
        
    }
    public String provjeriKorisnika(){
        Zadaca2_1WS zadaca2_1WS=new Zadaca2_1WS();
        if(zadaca2_1WS.provjeraKorisnika(korisnik, lozinka)){
            return "index.html";
        }
        poruka="Krivo upisani podaci!";
        return "";
    }

    
    
    
    
}
