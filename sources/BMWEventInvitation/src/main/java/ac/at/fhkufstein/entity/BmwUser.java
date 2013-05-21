/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Philipp
 */
@Entity
@Table(name = "bmw_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BmwUser.findAll", query = "SELECT b FROM BmwUser b"),
    @NamedQuery(name = "BmwUser.findByUid", query = "SELECT b FROM BmwUser b WHERE b.uid = :uid"),
    @NamedQuery(name = "BmwUser.findByPwd", query = "SELECT b FROM BmwUser b WHERE b.pwd = :pwd"),
    @NamedQuery(name = "BmwUser.findByRating", query = "SELECT b FROM BmwUser b WHERE b.rating = :rating"),
    @NamedQuery(name = "BmwUser.findBySeatingPriority", query = "SELECT b FROM BmwUser b WHERE b.seatingPriority = :seatingPriority")})
public class BmwUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "uid")
    private Integer uid;
    @Size(max = 50)
    @Column(name = "pwd")
    private String pwd;
    @Column(name = "rating")
    private Integer rating;
    @Size(max = 50)
    @Column(name = "seating_priority")
    private String seatingPriority;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<BmwParticipants> bmwParticipantsCollection;
    @JoinColumn(name = "PersonenID", referencedColumnName = "PersonalID")
    @ManyToOne(optional = false)
    private Personen personenID;

    public BmwUser() {
    }

    public BmwUser(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getSeatingPriority() {
        return seatingPriority;
    }

    public void setSeatingPriority(String seatingPriority) {
        this.seatingPriority = seatingPriority;
    }

    @XmlTransient
    public Collection<BmwParticipants> getBmwParticipantsCollection() {
        return bmwParticipantsCollection;
    }

    public void setBmwParticipantsCollection(Collection<BmwParticipants> bmwParticipantsCollection) {
        this.bmwParticipantsCollection = bmwParticipantsCollection;
    }

    public Personen getPersonenID() {
        return personenID;
    }

    public void setPersonenID(Personen personenID) {
        this.personenID = personenID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BmwUser)) {
            return false;
        }
        BmwUser other = (BmwUser) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.BmwUser[ uid=" + uid + " ]";
    }
    
}
