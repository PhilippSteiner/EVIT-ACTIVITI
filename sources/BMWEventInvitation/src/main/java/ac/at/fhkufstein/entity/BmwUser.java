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
    @NamedQuery(name = "BmwUser.findByPersonenID", query = "SELECT b FROM BmwUser b WHERE b.personenID = :personenID"),
    @NamedQuery(name = "BmwUser.findByPwd", query = "SELECT b FROM BmwUser b WHERE b.pwd = :pwd"),
    @NamedQuery(name = "BmwUser.findByEmail", query = "SELECT b FROM BmwUser b WHERE b.email = :email"),
    @NamedQuery(name = "BmwUser.findByUsername", query = "SELECT b FROM BmwUser b WHERE b.username = :username"),
    @NamedQuery(name = "BmwUser.findByRating", query = "SELECT b FROM BmwUser b WHERE b.rating = :rating"),
    @NamedQuery(name = "BmwUser.findByRole", query = "SELECT b FROM BmwUser b WHERE b.role = :role"),
    @NamedQuery(name = "BmwUser.findBySeatingPriority", query = "SELECT b FROM BmwUser b WHERE b.seatingPriority = :seatingPriority")})
public class BmwUser implements Serializable {
    @Column(name = "loginSent")
    private Boolean loginSent = false;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @Column(name = "role")
    private Integer role;
    @Size(max = 50)
    @Column(name = "frequentflyer")
    private String frequentflyer;
    @Size(max = 50)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 50)
    @Column(name = "lastName")
    private String lastName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "phone")
    private String phone;
    @Size(max = 200)
    @Column(name = "companyName")
    private String companyName;
    @Size(max = 5)
    @Column(name = "postalCode")
    private String postalCode;
    @Size(max = 50)
    @Column(name = "city")
    private String city;
    @Size(max = 50)
    @Column(name = "street")
    private String street;
    @OneToMany(mappedBy = "userTo")
    private Collection<BmwEmailHistory> bmwEmailHistoryCollection;
    @OneToMany(mappedBy = "travelAgency")
    private Collection<BmwEvent> bmwEventCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@Basic(optional = false)
//    @NotNull
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

    public Boolean getLoginSent() {
        return loginSent;
    }

    public void setLoginSent(Boolean loginSent) {
        this.loginSent = loginSent;
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getFrequentflyer() {
        return frequentflyer;
    }

    public void setFrequentflyer(String frequentflyer) {
        this.frequentflyer = frequentflyer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @XmlTransient
    public Collection<BmwEmailHistory> getBmwEmailHistoryCollection() {
        return bmwEmailHistoryCollection;
    }

    public void setBmwEmailHistoryCollection(Collection<BmwEmailHistory> bmwEmailHistoryCollection) {
        this.bmwEmailHistoryCollection = bmwEmailHistoryCollection;
    }

    @XmlTransient
    public Collection<BmwEvent> getBmwEventCollection() {
        return bmwEventCollection;
    }

    public void setBmwEventCollection(Collection<BmwEvent> bmwEventCollection) {
        this.bmwEventCollection = bmwEventCollection;
    }
    
}
