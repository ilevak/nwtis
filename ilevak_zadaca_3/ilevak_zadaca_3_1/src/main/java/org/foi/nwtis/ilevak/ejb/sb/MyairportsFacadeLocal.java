/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.WithAnnotations;
import org.foi.nwtis.ilevak.ejb.eb.Myairports;

/**
 *
 * @author ivale
 */
@RequestScoped
@LocalBean
public interface MyairportsFacadeLocal {

    /**
     *
     * @param myairports
     */
    void create(Myairports myairports);

    /**
     *
     * @param myairports
     */
    void edit(Myairports myairports);

    /**
     *
     * @param myairports
     */
    void remove(Myairports myairports);

    /**
     *
     * @param id
     * @return
     */
    Myairports find(Object id);

    /**
     *
     * @return
     */
    List<Myairports> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<Myairports> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
}
