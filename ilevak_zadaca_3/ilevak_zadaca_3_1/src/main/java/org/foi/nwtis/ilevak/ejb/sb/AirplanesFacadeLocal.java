/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import java.util.List;
import javax.ejb.Local;
import org.foi.nwtis.ilevak.ejb.eb.Airplanes;

/**
 *
 * @author ivale
 */
@Local
public interface AirplanesFacadeLocal {

    /**
     *
     * @param airplanes
     */
    void create(Airplanes airplanes);

    /**
     *
     * @param airplanes
     */
    void edit(Airplanes airplanes);

    /**
     *
     * @param airplanes
     */
    void remove(Airplanes airplanes);

    /**
     *
     * @param id
     * @return
     */
    Airplanes find(Object id);

    /**
     *
     * @return
     */
    List<Airplanes> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<Airplanes> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();
    
}
