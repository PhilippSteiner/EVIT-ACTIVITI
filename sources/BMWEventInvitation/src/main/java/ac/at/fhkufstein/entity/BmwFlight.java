/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Philipp
 */
@Entity
@Table(name = "bmw_flight")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BmwFlight.findAll", query = "SELECT b FROM BmwFlight b"),
    @NamedQuery(name = "BmwFlight.findById", query = "SELECT b FROM BmwFlight b WHERE b.id = :id"),
    @NamedQuery(name = "BmwFlight.findByEventId", query = "SELECT b FROM BmwFlight b WHERE b.eventId = :eventId"),
    @NamedQuery(name = "BmwFlight.findByFlightNumber", query = "SELECT b FROM BmwFlight b WHERE b.flightNumber = :flightNumber"),
    @NamedQuery(name = "BmwFlight.findByDepartureLocation", query = "SELECT b FROM BmwFlight b WHERE b.departureLocation = :departureLocation"),
    @NamedQuery(name = "BmwFlight.findByArrivalLocation", query = "SELECT b FROM BmwFlight b WHERE b.arrivalLocation = :arrivalLocation"),
    @NamedQuery(name = "BmwFlight.findByDepartureTime", query = "SELECT b FROM BmwFlight b WHERE b.departureTime = :departureTime"),
    @NamedQuery(name = "BmwFlight.findByArrivalTime", query = "SELECT b FROM BmwFlight b WHERE b.arrivalTime = :arrivalTime")})
public class BmwFlight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "flight_number")
    private String flightNumber;
    @Size(max = 150)
    @Column(name = "departure_location")
    private String departureLocation;
    @Size(max = 150)
    @Column(name = "arrival_location")
    private String arrivalLocation;
    @Column(name = "departure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;
    @Column(name = "arrival_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BmwEvent eventId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flightId")
    private Collection<BmwTravel> bmwTravelCollection;

    public BmwFlight() {
    }

    public BmwFlight(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BmwEvent getEventId() {
        return eventId;
    }

    public void setEventId(BmwEvent eventId) {
        this.eventId = eventId;
    }

    @XmlTransient
    public Collection<BmwTravel> getBmwTravelCollection() {
        return bmwTravelCollection;
    }

    public void setBmwTravelCollection(Collection<BmwTravel> bmwTravelCollection) {
        this.bmwTravelCollection = bmwTravelCollection;
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
        if (!(object instanceof BmwFlight)) {
            return false;
        }
        BmwFlight other = (BmwFlight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.BmwFlight[ id=" + id + " ]";
    }

}
