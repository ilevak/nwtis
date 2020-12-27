/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import java.util.List;
import javax.ejb.Local;
import org.foi.nwtis.ilevak.ejb.eb.Korisnici;

/**
 *
 * @author ivale
 */
@Local
public interface KorisniciFacadeLocal {

    /**
     *
     * @param korisnici
     */
    void create(Korisnici korisnici);

    /**
     *
     * @param korisnici
     */
    void edit(Korisnici korisnici);

    /**
     *
     * @param korisnici
     */
    void remove(Korisnici korisnici);

    /**
     *
     * @param id
     * @return
     */
    Korisnici find(Object id);

    /**
     *
     * @return
     */
    List<Korisnici> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<Korisnici> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
}
