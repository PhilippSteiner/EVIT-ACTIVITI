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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "bmw_email_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BmwEmailHistory.findAll", query = "SELECT b FROM BmwEmailHistory b"),
    @NamedQuery(name = "BmwEmailHistory.findById", query = "SELECT b FROM BmwEmailHistory b WHERE b.id = :id"),
    @NamedQuery(name = "BmwEmailHistory.findByUserFrom", query = "SELECT b FROM BmwEmailHistory b WHERE b.userFrom = :userFrom"),
    @NamedQuery(name = "BmwEmailHistory.findByEmailSubject", query = "SELECT b FROM BmwEmailHistory b WHERE b.emailSubject = :emailSubject"),
    @NamedQuery(name = "BmwEmailHistory.findByEmailType", query = "SELECT b FROM BmwEmailHistory b WHERE b.emailType = :emailType"),
    @NamedQuery(name = "BmwEmailHistory.findByEmailTo", query = "SELECT b FROM BmwEmailHistory b WHERE b.emailTo = :emailTo"),
    @NamedQuery(name = "BmwEmailHistory.findByEmailFrom", query = "SELECT b FROM BmwEmailHistory b WHERE b.emailFrom = :emailFrom"),
    @NamedQuery(name = "BmwEmailHistory.findByEmailBcc", query = "SELECT b FROM BmwEmailHistory b WHERE b.emailBcc = :emailBcc"),
    @NamedQuery(name = "BmwEmailHistory.findByEmailCc", query = "SELECT b FROM BmwEmailHistory b WHERE b.emailCc = :emailCc"),
    @NamedQuery(name = "BmwEmailHistory.findByDatetimeSent", query = "SELECT b FROM BmwEmailHistory b WHERE b.datetimeSent = :datetimeSent"),
    @NamedQuery(name = "BmwEmailHistory.findByDatetimeRecieved", query = "SELECT b FROM BmwEmailHistory b WHERE b.datetimeRecieved = :datetimeRecieved"),
    @NamedQuery(name = "BmwEmailHistory.findBySentStatus", query = "SELECT b FROM BmwEmailHistory b WHERE b.sentStatus = :sentStatus")})
public class BmwEmailHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_from")
    private Integer userFrom;
    @Size(max = 255)
    @Column(name = "email_subject")
    private String emailSubject;
    @Size(max = 255)
    @Column(name = "email_type")
    private String emailType;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "email_content")
    private String emailContent;
    @Size(max = 255)
    @Column(name = "email_to")
    private String emailTo;
    @Size(max = 255)
    @Column(name = "email_from")
    private String emailFrom;
    @Size(max = 255)
    @Column(name = "email_bcc")
    private String emailBcc;
    @Size(max = 255)
    @Column(name = "email_cc")
    private String emailCc;
    @Column(name = "datetime_sent")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeSent;
    @Column(name = "datetime_recieved")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeRecieved;
    @Size(max = 255)
    @Column(name = "sent_status")
    private String sentStatus;
    @JoinColumn(name = "user_to", referencedColumnName = "uid")
    @ManyToOne
    private BmwUser userTo;

    public BmwEmailHistory() {
    }

    public BmwEmailHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Integer userFrom) {
        this.userFrom = userFrom;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailBcc() {
        return emailBcc;
    }

    public void setEmailBcc(String emailBcc) {
        this.emailBcc = emailBcc;
    }

    public String getEmailCc() {
        return emailCc;
    }

    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    public Date getDatetimeSent() {
        return datetimeSent;
    }

    public void setDatetimeSent(Date datetimeSent) {
        this.datetimeSent = datetimeSent;
    }

    public Date getDatetimeRecieved() {
        return datetimeRecieved;
    }

    public void setDatetimeRecieved(Date datetimeRecieved) {
        this.datetimeRecieved = datetimeRecieved;
    }

    public String getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(String sentStatus) {
        this.sentStatus = sentStatus;
    }

    public BmwUser getUserTo() {
        return userTo;
    }

    public void setUserTo(BmwUser userTo) {
        this.userTo = userTo;
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
        if (!(object instanceof BmwEmailHistory)) {
            return false;
        }
        BmwEmailHistory other = (BmwEmailHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.BmwEmailHistory[ id=" + id + " ]";
    }
    
}
