/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import java.util.List;
import javax.ejb.Local;
import org.foi.nwtis.ilevak.ejb.eb.Grupe;

/**
 *
 * @author ivale
 */
@Local
public interface GrupeFacadeLocal {

    /**
     *
     * @param grupe
     */
    void create(Grupe grupe);

    /**
     *
     * @param grupe
     */
    void edit(Grupe grupe);

    /**
     *
     * @param grupe
     */
    void remove(Grupe grupe);

    /**
     *
     * @param id
     * @return
     */
    Grupe find(Object id);

    /**
     *
     * @return
     */
    List<Grupe> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<Grupe> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
}
