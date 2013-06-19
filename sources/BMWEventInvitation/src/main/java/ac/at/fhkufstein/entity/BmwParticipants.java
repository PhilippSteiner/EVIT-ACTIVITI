/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philipp
 */
@Entity
@Table(name = "bmw_participants")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BmwParticipants.findAll", query = "SELECT b FROM BmwParticipants b"),
    @NamedQuery(name = "BmwParticipants.findById", query = "SELECT b FROM BmwParticipants b WHERE b.id = :id"),
	@NamedQuery(name = "BmwParticipants.findByEventId", query = "SELECT b FROM BmwParticipants b WHERE b.eventId = :id"),
        @NamedQuery(name = "BmwParticipants.findByUserId", query = "SELECT b FROM BmwParticipants b WHERE b.userId = :userId"),
    @NamedQuery(name = "BmwParticipants.findByPState", query = "SELECT b FROM BmwParticipants b WHERE b.pState = :pState"),
    @NamedQuery(name = "BmwParticipants.findByRepId", query = "SELECT b FROM BmwParticipants b WHERE b.repId = :repId"),
    @NamedQuery(name = "BmwParticipants.findByBmwAuth", query = "SELECT b FROM BmwParticipants b WHERE b.bmwAuth = :bmwAuth")})
public class BmwParticipants implements Serializable {
    @Column(name = "processId")
    private Integer processId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "p_state")
    private String pState;
    @Column(name = "rep_id")
    private Integer repId;
    @Column(name = "bmw_auth")
    private Integer bmwAuth;
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private BmwUser userId;
    @JoinColumn(name = "travel_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BmwTravel travelId;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BmwEvent eventId;

    public BmwParticipants() {
    }

    public BmwParticipants(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPState() {
        return pState;
    }

    public void setPState(String pState) {
        this.pState = pState;
    }

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }

    public Integer getBmwAuth() {
        return bmwAuth;
    }

    public void setBmwAuth(Integer bmwAuth) {
        this.bmwAuth = bmwAuth;
    }

    public BmwUser getUserId() {
        return userId;
    }

    public void setUserId(BmwUser userId) {
        this.userId = userId;
    }

    public BmwTravel getTravelId() {
        return travelId;
    }

    public void setTravelId(BmwTravel travelId) {
        this.travelId = travelId;
    }

    public BmwEvent getEventId() {
        return eventId;
    }

    public void setEventId(BmwEvent eventId) {
        this.eventId = eventId;
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
        if (!(object instanceof BmwParticipants)) {
            return false;
        }
        BmwParticipants other = (BmwParticipants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.BmwParticipants[ id=" + id + " ]";
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
    
}
