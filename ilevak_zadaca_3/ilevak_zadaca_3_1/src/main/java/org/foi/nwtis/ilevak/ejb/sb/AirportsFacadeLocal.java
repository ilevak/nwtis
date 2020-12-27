/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import java.util.List;
import javax.ejb.Local;
import javax.transaction.Transactional;
import org.foi.nwtis.ilevak.ejb.eb.Airports;
import org.foi.nwtis.podaci.Korisnik;
/**
 *
 * @author ivale
 */
@Local
public interface AirportsFacadeLocal {

    /**
     *
     * @param airports
     */
    void create(Airports airports);

    /**
     *
     * @param airports
     */
    void edit(Airports airports);

    /**
     *
     * @param airports
     */
    void remove(Airports airports);

    /**
     *
     * @param id
     * @return
     */
    Airports find(Object id);

    /**
     *
     * @return
     */
    List<Airports> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<Airports> findRange(int[] range);
    
    /**
     *
     * @return
     */
    int count();
    
    /**
     *
     * @param name
     * @return
     */
    
    List<Airports> findByName_CAPI(String name);
    
    
    List<Airports> findByName_JPQL(String name);
}
