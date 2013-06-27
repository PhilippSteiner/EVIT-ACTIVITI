/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.entity;

import ac.at.fhkufstein.mailing.EmailTemplateable;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philipp
 */
@Entity
@Table(name = "bmw_email_templates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BmwEmailTemplates.findAll", query = "SELECT b FROM BmwEmailTemplates b"),
    @NamedQuery(name = "BmwEmailTemplates.findById", query = "SELECT b FROM BmwEmailTemplates b WHERE b.id = :id"),
    @NamedQuery(name = "BmwEmailTemplates.findBySubject", query = "SELECT b FROM BmwEmailTemplates b WHERE b.subject = :subject"),
    @NamedQuery(name = "BmwEmailTemplates.findByType", query = "SELECT b FROM BmwEmailTemplates b WHERE b.type = :type")})
public class BmwEmailTemplates implements Serializable, EmailTemplateable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "subject")
    private String subject;
    @Size(max = 50)
    @Column(name = "type")
    private String type;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "email_content")
    private String emailContent;

    public BmwEmailTemplates() {
    }

    public BmwEmailTemplates(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
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
        if (!(object instanceof BmwEmailTemplates)) {
            return false;
        }
        BmwEmailTemplates other = (BmwEmailTemplates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.BmwEmailTemplates[ id=" + id + " ]";
    }

}
