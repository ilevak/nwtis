/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.sb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.foi.nwtis.ilevak.ejb.eb.Airports;

/**
 *
 * @author ivale
 */
@Stateless
public class AirportsFacade extends AbstractFacade<Airports> implements AirportsFacadeLocal {

    @PersistenceContext(unitName = "NWTiS_DZ3_PU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public AirportsFacade() {
        super(Airports.class);
    }

    /**
     * Vrača listu aviona koji su filtrirani prema nazivu koje je korisnik unio
     *
     * @param name
     * @return
     */
    @Override
    public List<Airports> findByName_CAPI(String name) {
        name = name + "%";
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Airports.class);
        Root<Airports> aerodromi = cq.from(Airports.class);
        Expression<String> naziv = aerodromi.get("name");
        cq.where(cb.and(cb.like(cb.lower(naziv), name.toLowerCase())));
        Query q = em.createQuery(cq);
        return q.getResultList();
    }

    /**
     * Metoda dohvaca listu airports i vraca ju prema JPQL upitu i nazivu airports-a
     * @param name
     * @param username
     * @return 
     */
    @Override
    public List<Airports> findByName_JPQL(String name) {
        name = name + "%";
        return em.createNamedQuery("Airports.findByName_JPQL")
                .setParameter("name", name)
                .getResultList();

    }

}
