/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ilevak.ejb.eb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivale
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airplanes.findAll", query = "SELECT a FROM Airplanes a")})
public class Airplanes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String icao24;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int firstseen;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int lastseen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String estarrivalairport;
    @Size(max = 20)
    @Column(length = 20)
    private String callsign;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int estdepartureairporthorizdistance;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int estdepartureairportvertdistance;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int estarrivalairporthorizdistance;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int estarrivalairportvertdistance;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int departureairportcandidatescount;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int arrivalairportcandidatescount;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stored;
    @JoinColumn(name = "ESTDEPARTUREAIRPORT", referencedColumnName = "IDENT", nullable = false)
    @ManyToOne(optional = false)
    private Airports estdepartureairport;

    /**
     *
     */
    public Airplanes() {
    }

    /**
     *
     * @param id
     */
    public Airplanes(Integer id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param icao24
     * @param firstseen
     * @param lastseen
     * @param estarrivalairport
     * @param estdepartureairporthorizdistance
     * @param estdepartureairportvertdistance
     * @param estarrivalairporthorizdistance
     * @param estarrivalairportvertdistance
     * @param departureairportcandidatescount
     * @param arrivalairportcandidatescount
     * @param stored
     */
    public Airplanes(Integer id, String icao24, int firstseen, int lastseen, String estarrivalairport, int estdepartureairporthorizdistance, int estdepartureairportvertdistance, int estarrivalairporthorizdistance, int estarrivalairportvertdistance, int departureairportcandidatescount, int arrivalairportcandidatescount, Date stored) {
        this.id = id;
        this.icao24 = icao24;
        this.firstseen = firstseen;
        this.lastseen = lastseen;
        this.estarrivalairport = estarrivalairport;
        this.estdepartureairporthorizdistance = estdepartureairporthorizdistance;
        this.estdepartureairportvertdistance = estdepartureairportvertdistance;
        this.estarrivalairporthorizdistance = estarrivalairporthorizdistance;
        this.estarrivalairportvertdistance = estarrivalairportvertdistance;
        this.departureairportcandidatescount = departureairportcandidatescount;
        this.arrivalairportcandidatescount = arrivalairportcandidatescount;
        this.stored = stored;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getIcao24() {
        return icao24;
    }

    /**
     *
     * @param icao24
     */
    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    /**
     *
     * @return
     */
    public int getFirstseen() {
        return firstseen;
    }

    /**
     *
     * @param firstseen
     */
    public void setFirstseen(int firstseen) {
        this.firstseen = firstseen;
    }

    /**
     *
     * @return
     */
    public int getLastseen() {
        return lastseen;
    }

    /**
     *
     * @param lastseen
     */
    public void setLastseen(int lastseen) {
        this.lastseen = lastseen;
    }

    /**
     *
     * @return
     */
    public String getEstarrivalairport() {
        return estarrivalairport;
    }

    /**
     *
     * @param estarrivalairport
     */
    public void setEstarrivalairport(String estarrivalairport) {
        this.estarrivalairport = estarrivalairport;
    }

    /**
     *
     * @return
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     *
     * @param callsign
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    /**
     *
     * @return
     */
    public int getEstdepartureairporthorizdistance() {
        return estdepartureairporthorizdistance;
    }

    /**
     *
     * @param estdepartureairporthorizdistance
     */
    public void setEstdepartureairporthorizdistance(int estdepartureairporthorizdistance) {
        this.estdepartureairporthorizdistance = estdepartureairporthorizdistance;
    }

    /**
     *
     * @return
     */
    public int getEstdepartureairportvertdistance() {
        return estdepartureairportvertdistance;
    }

    /**
     *
     * @param estdepartureairportvertdistance
     */
    public void setEstdepartureairportvertdistance(int estdepartureairportvertdistance) {
        this.estdepartureairportvertdistance = estdepartureairportvertdistance;
    }

    /**
     *
     * @return
     */
    public int getEstarrivalairporthorizdistance() {
        return estarrivalairporthorizdistance;
    }

    /**
     *
     * @param estarrivalairporthorizdistance
     */
    public void setEstarrivalairporthorizdistance(int estarrivalairporthorizdistance) {
        this.estarrivalairporthorizdistance = estarrivalairporthorizdistance;
    }

    /**
     *
     * @return
     */
    public int getEstarrivalairportvertdistance() {
        return estarrivalairportvertdistance;
    }

    /**
     *
     * @param estarrivalairportvertdistance
     */
    public void setEstarrivalairportvertdistance(int estarrivalairportvertdistance) {
        this.estarrivalairportvertdistance = estarrivalairportvertdistance;
    }

    /**
     *
     * @return
     */
    public int getDepartureairportcandidatescount() {
        return departureairportcandidatescount;
    }

    /**
     *
     * @param departureairportcandidatescount
     */
    public void setDepartureairportcandidatescount(int departureairportcandidatescount) {
        this.departureairportcandidatescount = departureairportcandidatescount;
    }

    /**
     *
     * @return
     */
    public int getArrivalairportcandidatescount() {
        return arrivalairportcandidatescount;
    }

    /**
     *
     * @param arrivalairportcandidatescount
     */
    public void setArrivalairportcandidatescount(int arrivalairportcandidatescount) {
        this.arrivalairportcandidatescount = arrivalairportcandidatescount;
    }

    /**
     *
     * @return
     */
    public Date getStored() {
        return stored;
    }

    /**
     *
     * @param stored
     */
    public void setStored(Date stored) {
        this.stored = stored;
    }

    /**
     *
     * @return
     */
    public Airports getEstdepartureairport() {
        return estdepartureairport;
    }

    /**
     *
     * @param estdepartureairport
     */
    public void setEstdepartureairport(Airports estdepartureairport) {
        this.estdepartureairport = estdepartureairport;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airplanes)) {
            return false;
        }
        Airplanes other = (Airplanes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.ilevak.ejb.eb.Airplanes[ id=" + id + " ]";
    }
    
}
