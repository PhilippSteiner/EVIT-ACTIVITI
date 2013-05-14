/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philipp
 */
@Entity
@Table(name = "Event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
    @NamedQuery(name = "Event.findByName", query = "SELECT e FROM Event e WHERE e.name = :name"),
    @NamedQuery(name = "Event.findByStartEventdate", query = "SELECT e FROM Event e WHERE e.startEventdate = :startEventdate"),
    @NamedQuery(name = "Event.findByEndEventdate", query = "SELECT e FROM Event e WHERE e.endEventdate = :endEventdate"),
    @NamedQuery(name = "Event.findByMaxParticipants", query = "SELECT e FROM Event e WHERE e.maxParticipants = :maxParticipants"),
    @NamedQuery(name = "Event.findByLocation", query = "SELECT e FROM Event e WHERE e.location = :location"),
    @NamedQuery(name = "Event.findByUrgencyDayLimit", query = "SELECT e FROM Event e WHERE e.urgencyDayLimit = :urgencyDayLimit"),
    @NamedQuery(name = "Event.findByEventUpdated", query = "SELECT e FROM Event e WHERE e.eventUpdated = :eventUpdated"),
    @NamedQuery(name = "Event.findByEventCreated", query = "SELECT e FROM Event e WHERE e.eventCreated = :eventCreated"),
    @NamedQuery(name = "Event.findByUserUpdated", query = "SELECT e FROM Event e WHERE e.userUpdated = :userUpdated"),
    @NamedQuery(name = "Event.findByUserCreated", query = "SELECT e FROM Event e WHERE e.userCreated = :userCreated"),
    @NamedQuery(name = "Event.findByProgress", query = "SELECT e FROM Event e WHERE e.progress = :progress"),
    @NamedQuery(name = "Event.findByProcessId", query = "SELECT e FROM Event e WHERE e.processId = :processId")})
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_eventdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startEventdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_eventdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endEventdate;
    @Column(name = "max_participants")
    private Integer maxParticipants;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 250)
    @Column(name = "location")
    private String location;
    @Column(name = "urgency_day_limit")
    private Integer urgencyDayLimit;
    @Column(name = "event_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventUpdated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "event_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventCreated;
    @Column(name = "user_updated")
    private Integer userUpdated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_created")
    private int userCreated;
    @Column(name = "progress")
    private Integer progress;
    @Column(name = "process_id")
    private Integer processId;

    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Event(Integer id, String name, Date startEventdate, Date endEventdate, Date eventCreated, int userCreated) {
        this.id = id;
        this.name = name;
        this.startEventdate = startEventdate;
        this.endEventdate = endEventdate;
        this.eventCreated = eventCreated;
        this.userCreated = userCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartEventdate() {
        return startEventdate;
    }

    public void setStartEventdate(Date startEventdate) {
        this.startEventdate = startEventdate;
    }

    public Date getEndEventdate() {
        return endEventdate;
    }

    public void setEndEventdate(Date endEventdate) {
        this.endEventdate = endEventdate;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getUrgencyDayLimit() {
        return urgencyDayLimit;
    }

    public void setUrgencyDayLimit(Integer urgencyDayLimit) {
        this.urgencyDayLimit = urgencyDayLimit;
    }

    public Date getEventUpdated() {
        return eventUpdated;
    }

    public void setEventUpdated(Date eventUpdated) {
        this.eventUpdated = eventUpdated;
    }

    public Date getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated(Date eventCreated) {
        this.eventCreated = eventCreated;
    }

    public Integer getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(Integer userUpdated) {
        this.userUpdated = userUpdated;
    }

    public int getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(int userCreated) {
        this.userCreated = userCreated;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.Event[ id=" + id + " ]";
    }
    
}
