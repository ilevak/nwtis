/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import java.util.List;
import javax.ejb.Local;
import org.foi.nwtis.ilevak.ejb.eb.KorisniciGrupe;

/**
 *
 * @author ivale
 */
@Local
public interface KorisniciGrupeFacadeLocal {

    /**
     *
     * @param korisniciGrupe
     */
    void create(KorisniciGrupe korisniciGrupe);

    /**
     *
     * @param korisniciGrupe
     */
    void edit(KorisniciGrupe korisniciGrupe);

    /**
     *
     * @param korisniciGrupe
     */
    void remove(KorisniciGrupe korisniciGrupe);

    /**
     *
     * @param id
     * @return
     */
    KorisniciGrupe find(Object id);

    /**
     *
     * @return
     */
    List<KorisniciGrupe> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<KorisniciGrupe> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
}
