/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import java.util.List;
import javax.ejb.Local;
import org.foi.nwtis.ilevak.ejb.eb.Myairportslog;

/**
 *
 * @author ivale
 */
@Local
public interface MyairportslogFacadeLocal {

    /**
     *
     * @param myairportslog
     */
    void create(Myairportslog myairportslog);

    /**
     *
     * @param myairportslog
     */
    void edit(Myairportslog myairportslog);

    /**
     *
     * @param myairportslog
     */
    void remove(Myairportslog myairportslog);

    /**
     *
     * @param id
     * @return
     */
    Myairportslog find(Object id);

    /**
     *
     * @return
     */
    List<Myairportslog> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<Myairportslog> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
}
