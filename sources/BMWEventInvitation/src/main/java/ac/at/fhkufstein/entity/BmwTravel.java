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
@Table(name = "bmw_travel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BmwTravel.findAll", query = "SELECT b FROM BmwTravel b"),
    @NamedQuery(name = "BmwTravel.findById", query = "SELECT b FROM BmwTravel b WHERE b.id = :id"),
    @NamedQuery(name = "BmwTravel.findByType", query = "SELECT b FROM BmwTravel b WHERE b.type = :type"),
    @NamedQuery(name = "BmwTravel.findByFlight", query = "SELECT b FROM BmwTravel b WHERE b.flightId = :flightId"),
    @NamedQuery(name = "BmwTravel.findByArrivalDatetime", query = "SELECT b FROM BmwTravel b WHERE b.arrivalDatetime = :arrivalDatetime"),
    @NamedQuery(name = "BmwTravel.findByPdfTicketUrl", query = "SELECT b FROM BmwTravel b WHERE b.pdfTicketUrl = :pdfTicketUrl"),
    @NamedQuery(name = "BmwTravel.findAllFlightsWithPdfTicketUrl", query = "SELECT b FROM BmwTravel b WHERE b.flightId IS NOT NULL AND b.pdfTicketUrl IS NULL"),
    @NamedQuery(name = "BmwTravel.findFlightsWithPdfTicketUrl", query = "SELECT b FROM BmwTravel b WHERE b.flightId IS NOT NULL AND b.pdfTicketUrl IS NULL")})
public class BmwTravel implements Serializable {
    @Size(max = 50)
    @Column(name = "comment")
    private String comment;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "type")
    private String type;
    @Column(name = "arrival_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDatetime;
    @Size(max = 250)
    @Column(name = "pdf_ticket_url")
    private String pdfTicketUrl;
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BmwFlight flightId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "travelId")
    private Collection<BmwParticipants> bmwParticipantsCollection;

    public BmwTravel() {
    }

    public BmwTravel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getArrivalDatetime() {
        return arrivalDatetime;
    }

    public void setArrivalDatetime(Date arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
    }

    public String getPdfTicketUrl() {
        return pdfTicketUrl;
    }

    public void setPdfTicketUrl(String pdfTicketUrl) {
        this.pdfTicketUrl = pdfTicketUrl;
    }

    public BmwFlight getFlightId() {
        return flightId;
    }

    public void setFlightId(BmwFlight flightId) {
        this.flightId = flightId;
    }

    @XmlTransient
    public Collection<BmwParticipants> getBmwParticipantsCollection() {
        return bmwParticipantsCollection;
    }

    public void setBmwParticipantsCollection(Collection<BmwParticipants> bmwParticipantsCollection) {
        this.bmwParticipantsCollection = bmwParticipantsCollection;
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
        if (!(object instanceof BmwTravel)) {
            return false;
        }
        BmwTravel other = (BmwTravel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.BmwTravel[ id=" + id + " ]";
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
