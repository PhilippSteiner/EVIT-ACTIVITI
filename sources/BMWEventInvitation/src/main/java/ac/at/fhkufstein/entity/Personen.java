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
@Table(name = "Personen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personen.findAll", query = "SELECT p FROM Personen p"),
    @NamedQuery(name = "Personen.findByPersonalID", query = "SELECT p FROM Personen p WHERE p.personalID = :personalID"),
    @NamedQuery(name = "Personen.findBySpeichernUnter", query = "SELECT p FROM Personen p WHERE p.speichernUnter = :speichernUnter"),
    @NamedQuery(name = "Personen.findByOrgEinheit", query = "SELECT p FROM Personen p WHERE p.orgEinheit = :orgEinheit"),
    @NamedQuery(name = "Personen.findByFirma", query = "SELECT p FROM Personen p WHERE p.firma = :firma"),
    @NamedQuery(name = "Personen.findByAnrede", query = "SELECT p FROM Personen p WHERE p.anrede = :anrede"),
    @NamedQuery(name = "Personen.findByAkadTitel", query = "SELECT p FROM Personen p WHERE p.akadTitel = :akadTitel"),
    @NamedQuery(name = "Personen.findByBerufstitel", query = "SELECT p FROM Personen p WHERE p.berufstitel = :berufstitel"),
    @NamedQuery(name = "Personen.findByVorname", query = "SELECT p FROM Personen p WHERE p.vorname = :vorname"),
    @NamedQuery(name = "Personen.findByNachname", query = "SELECT p FROM Personen p WHERE p.nachname = :nachname"),
    @NamedQuery(name = "Personen.findByNameVollstaendig", query = "SELECT p FROM Personen p WHERE p.nameVollstaendig = :nameVollstaendig"),
    @NamedQuery(name = "Personen.findByAbteilung", query = "SELECT p FROM Personen p WHERE p.abteilung = :abteilung"),
    @NamedQuery(name = "Personen.findByPosition", query = "SELECT p FROM Personen p WHERE p.position = :position"),
    @NamedQuery(name = "Personen.findByStrasse", query = "SELECT p FROM Personen p WHERE p.strasse = :strasse"),
    @NamedQuery(name = "Personen.findByPlz", query = "SELECT p FROM Personen p WHERE p.plz = :plz"),
    @NamedQuery(name = "Personen.findByOrt", query = "SELECT p FROM Personen p WHERE p.ort = :ort"),
    @NamedQuery(name = "Personen.findByLand", query = "SELECT p FROM Personen p WHERE p.land = :land"),
    @NamedQuery(name = "Personen.findByBriefkopf", query = "SELECT p FROM Personen p WHERE p.briefkopf = :briefkopf"),
    @NamedQuery(name = "Personen.findByWeitereStrasse", query = "SELECT p FROM Personen p WHERE p.weitereStrasse = :weitereStrasse"),
    @NamedQuery(name = "Personen.findByWeiterePLZ", query = "SELECT p FROM Personen p WHERE p.weiterePLZ = :weiterePLZ"),
    @NamedQuery(name = "Personen.findByWeitererOrt", query = "SELECT p FROM Personen p WHERE p.weitererOrt = :weitererOrt"),
    @NamedQuery(name = "Personen.findByWeiteresLand", query = "SELECT p FROM Personen p WHERE p.weiteresLand = :weiteresLand"),
    @NamedQuery(name = "Personen.findByWeitererBriefkopf", query = "SELECT p FROM Personen p WHERE p.weitererBriefkopf = :weitererBriefkopf"),
    @NamedQuery(name = "Personen.findByBriefanredeSie", query = "SELECT p FROM Personen p WHERE p.briefanredeSie = :briefanredeSie"),
    @NamedQuery(name = "Personen.findByEMail1", query = "SELECT p FROM Personen p WHERE p.eMail1 = :eMail1"),
    @NamedQuery(name = "Personen.findByEMail2", query = "SELECT p FROM Personen p WHERE p.eMail2 = :eMail2"),
    @NamedQuery(name = "Personen.findByWebseite", query = "SELECT p FROM Personen p WHERE p.webseite = :webseite"),
    @NamedQuery(name = "Personen.findByNotizen", query = "SELECT p FROM Personen p WHERE p.notizen = :notizen"),
    @NamedQuery(name = "Personen.findByTelefonGeschaeftlich", query = "SELECT p FROM Personen p WHERE p.telefonGeschaeftlich = :telefonGeschaeftlich"),
    @NamedQuery(name = "Personen.findByTelefonPrivat", query = "SELECT p FROM Personen p WHERE p.telefonPrivat = :telefonPrivat"),
    @NamedQuery(name = "Personen.findByFax", query = "SELECT p FROM Personen p WHERE p.fax = :fax"),
    @NamedQuery(name = "Personen.findByTelefonMobil", query = "SELECT p FROM Personen p WHERE p.telefonMobil = :telefonMobil"),
    @NamedQuery(name = "Personen.findByGeburtsdatum", query = "SELECT p FROM Personen p WHERE p.geburtsdatum = :geburtsdatum"),
    @NamedQuery(name = "Personen.findByKinderPartner", query = "SELECT p FROM Personen p WHERE p.kinderPartner = :kinderPartner"),
    @NamedQuery(name = "Personen.findByHobby", query = "SELECT p FROM Personen p WHERE p.hobby = :hobby"),
    @NamedQuery(name = "Personen.findByKategorien", query = "SELECT p FROM Personen p WHERE p.kategorien = :kategorien"),
    @NamedQuery(name = "Personen.findByVerteiler", query = "SELECT p FROM Personen p WHERE p.verteiler = :verteiler"),
    @NamedQuery(name = "Personen.findByFotoName", query = "SELECT p FROM Personen p WHERE p.fotoName = :fotoName"),
    @NamedQuery(name = "Personen.findByKIDSKontaktNr", query = "SELECT p FROM Personen p WHERE p.kIDSKontaktNr = :kIDSKontaktNr"),
    @NamedQuery(name = "Personen.findByFuehrerscheinNummer", query = "SELECT p FROM Personen p WHERE p.fuehrerscheinNummer = :fuehrerscheinNummer"),
    @NamedQuery(name = "Personen.findByFuehrerscheinKlassen", query = "SELECT p FROM Personen p WHERE p.fuehrerscheinKlassen = :fuehrerscheinKlassen"),
    @NamedQuery(name = "Personen.findByFuehrerscheinDatum", query = "SELECT p FROM Personen p WHERE p.fuehrerscheinDatum = :fuehrerscheinDatum"),
    @NamedQuery(name = "Personen.findByFuehrerscheinBehoerde", query = "SELECT p FROM Personen p WHERE p.fuehrerscheinBehoerde = :fuehrerscheinBehoerde"),
    @NamedQuery(name = "Personen.findByGeloescht", query = "SELECT p FROM Personen p WHERE p.geloescht = :geloescht"),
    @NamedQuery(name = "Personen.findByLetzteAenderungAm", query = "SELECT p FROM Personen p WHERE p.letzteAenderungAm = :letzteAenderungAm"),
    @NamedQuery(name = "Personen.findByLetzteAenderungDurch", query = "SELECT p FROM Personen p WHERE p.letzteAenderungDurch = :letzteAenderungDurch")})
public class Personen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "PersonalID")
    private String personalID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SpeichernUnter")
    private String speichernUnter;
    @Size(max = 50)
    @Column(name = "OrgEinheit")
    private String orgEinheit;
    @Size(max = 100)
    @Column(name = "Firma")
    private String firma;
    @Size(max = 20)
    @Column(name = "Anrede")
    private String anrede;
    @Size(max = 30)
    @Column(name = "AkadTitel")
    private String akadTitel;
    @Size(max = 150)
    @Column(name = "Berufstitel")
    private String berufstitel;
    @Size(max = 40)
    @Column(name = "Vorname")
    private String vorname;
    @Size(max = 50)
    @Column(name = "Nachname")
    private String nachname;
    @Size(max = 150)
    @Column(name = "NameVollstaendig")
    private String nameVollstaendig;
    @Size(max = 50)
    @Column(name = "Abteilung")
    private String abteilung;
    @Size(max = 100)
    @Column(name = "Position")
    private String position;
    @Size(max = 100)
    @Column(name = "Strasse")
    private String strasse;
    @Size(max = 10)
    @Column(name = "PLZ")
    private String plz;
    @Size(max = 50)
    @Column(name = "Ort")
    private String ort;
    @Size(max = 40)
    @Column(name = "Land")
    private String land;
    @Size(max = 255)
    @Column(name = "Briefkopf")
    private String briefkopf;
    @Size(max = 100)
    @Column(name = "WeitereStrasse")
    private String weitereStrasse;
    @Size(max = 10)
    @Column(name = "WeiterePLZ")
    private String weiterePLZ;
    @Size(max = 50)
    @Column(name = "WeitererOrt")
    private String weitererOrt;
    @Size(max = 40)
    @Column(name = "WeiteresLand")
    private String weiteresLand;
    @Size(max = 255)
    @Column(name = "WeitererBriefkopf")
    private String weitererBriefkopf;
    @Size(max = 100)
    @Column(name = "BriefanredeSie")
    private String briefanredeSie;
    @Size(max = 50)
    @Column(name = "EMail1")
    private String eMail1;
    @Size(max = 50)
    @Column(name = "EMail2")
    private String eMail2;
    @Size(max = 250)
    @Column(name = "Webseite")
    private String webseite;
    @Size(max = 2147483647)
    @Column(name = "Notizen")
    private String notizen;
    @Size(max = 50)
    @Column(name = "TelefonGeschaeftlich")
    private String telefonGeschaeftlich;
    @Size(max = 50)
    @Column(name = "TelefonPrivat")
    private String telefonPrivat;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Fax")
    private String fax;
    @Size(max = 50)
    @Column(name = "TelefonMobil")
    private String telefonMobil;
    @Column(name = "Geburtsdatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date geburtsdatum;
    @Size(max = 100)
    @Column(name = "Kinder_Partner")
    private String kinderPartner;
    @Size(max = 100)
    @Column(name = "Hobby")
    private String hobby;
    @Size(max = 2147483647)
    @Column(name = "Kategorien")
    private String kategorien;
    @Size(max = 2147483647)
    @Column(name = "Verteiler")
    private String verteiler;
    @Size(max = 255)
    @Column(name = "FotoName")
    private String fotoName;
    @Size(max = 6)
    @Column(name = "KIDSKontaktNr")
    private String kIDSKontaktNr;
    @Size(max = 20)
    @Column(name = "FuehrerscheinNummer")
    private String fuehrerscheinNummer;
    @Size(max = 50)
    @Column(name = "FuehrerscheinKlassen")
    private String fuehrerscheinKlassen;
    @Column(name = "FuehrerscheinDatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fuehrerscheinDatum;
    @Size(max = 50)
    @Column(name = "FuehrerscheinBehoerde")
    private String fuehrerscheinBehoerde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Geloescht")
    private boolean geloescht;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LetzteAenderungAm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date letzteAenderungAm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "LetzteAenderungDurch")
    private String letzteAenderungDurch;

    public Personen() {
    }

    public Personen(String personalID) {
        this.personalID = personalID;
    }

    public Personen(String personalID, String speichernUnter, boolean geloescht, Date letzteAenderungAm, String letzteAenderungDurch) {
        this.personalID = personalID;
        this.speichernUnter = speichernUnter;
        this.geloescht = geloescht;
        this.letzteAenderungAm = letzteAenderungAm;
        this.letzteAenderungDurch = letzteAenderungDurch;
    }

    public String getPersonalID() {
        return personalID;
    }

    public void setPersonalID(String personalID) {
        this.personalID = personalID;
    }

    public String getSpeichernUnter() {
        return speichernUnter;
    }

    public void setSpeichernUnter(String speichernUnter) {
        this.speichernUnter = speichernUnter;
    }

    public String getOrgEinheit() {
        return orgEinheit;
    }

    public void setOrgEinheit(String orgEinheit) {
        this.orgEinheit = orgEinheit;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getAkadTitel() {
        return akadTitel;
    }

    public void setAkadTitel(String akadTitel) {
        this.akadTitel = akadTitel;
    }

    public String getBerufstitel() {
        return berufstitel;
    }

    public void setBerufstitel(String berufstitel) {
        this.berufstitel = berufstitel;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getNameVollstaendig() {
        return nameVollstaendig;
    }

    public void setNameVollstaendig(String nameVollstaendig) {
        this.nameVollstaendig = nameVollstaendig;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getBriefkopf() {
        return briefkopf;
    }

    public void setBriefkopf(String briefkopf) {
        this.briefkopf = briefkopf;
    }

    public String getWeitereStrasse() {
        return weitereStrasse;
    }

    public void setWeitereStrasse(String weitereStrasse) {
        this.weitereStrasse = weitereStrasse;
    }

    public String getWeiterePLZ() {
        return weiterePLZ;
    }

    public void setWeiterePLZ(String weiterePLZ) {
        this.weiterePLZ = weiterePLZ;
    }

    public String getWeitererOrt() {
        return weitererOrt;
    }

    public void setWeitererOrt(String weitererOrt) {
        this.weitererOrt = weitererOrt;
    }

    public String getWeiteresLand() {
        return weiteresLand;
    }

    public void setWeiteresLand(String weiteresLand) {
        this.weiteresLand = weiteresLand;
    }

    public String getWeitererBriefkopf() {
        return weitererBriefkopf;
    }

    public void setWeitererBriefkopf(String weitererBriefkopf) {
        this.weitererBriefkopf = weitererBriefkopf;
    }

    public String getBriefanredeSie() {
        return briefanredeSie;
    }

    public void setBriefanredeSie(String briefanredeSie) {
        this.briefanredeSie = briefanredeSie;
    }

    public String getEMail1() {
        return eMail1;
    }

    public void setEMail1(String eMail1) {
        this.eMail1 = eMail1;
    }

    public String getEMail2() {
        return eMail2;
    }

    public void setEMail2(String eMail2) {
        this.eMail2 = eMail2;
    }

    public String getWebseite() {
        return webseite;
    }

    public void setWebseite(String webseite) {
        this.webseite = webseite;
    }

    public String getNotizen() {
        return notizen;
    }

    public void setNotizen(String notizen) {
        this.notizen = notizen;
    }

    public String getTelefonGeschaeftlich() {
        return telefonGeschaeftlich;
    }

    public void setTelefonGeschaeftlich(String telefonGeschaeftlich) {
        this.telefonGeschaeftlich = telefonGeschaeftlich;
    }

    public String getTelefonPrivat() {
        return telefonPrivat;
    }

    public void setTelefonPrivat(String telefonPrivat) {
        this.telefonPrivat = telefonPrivat;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelefonMobil() {
        return telefonMobil;
    }

    public void setTelefonMobil(String telefonMobil) {
        this.telefonMobil = telefonMobil;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getKinderPartner() {
        return kinderPartner;
    }

    public void setKinderPartner(String kinderPartner) {
        this.kinderPartner = kinderPartner;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getKategorien() {
        return kategorien;
    }

    public void setKategorien(String kategorien) {
        this.kategorien = kategorien;
    }

    public String getVerteiler() {
        return verteiler;
    }

    public void setVerteiler(String verteiler) {
        this.verteiler = verteiler;
    }

    public String getFotoName() {
        return fotoName;
    }

    public void setFotoName(String fotoName) {
        this.fotoName = fotoName;
    }

    public String getKIDSKontaktNr() {
        return kIDSKontaktNr;
    }

    public void setKIDSKontaktNr(String kIDSKontaktNr) {
        this.kIDSKontaktNr = kIDSKontaktNr;
    }

    public String getFuehrerscheinNummer() {
        return fuehrerscheinNummer;
    }

    public void setFuehrerscheinNummer(String fuehrerscheinNummer) {
        this.fuehrerscheinNummer = fuehrerscheinNummer;
    }

    public String getFuehrerscheinKlassen() {
        return fuehrerscheinKlassen;
    }

    public void setFuehrerscheinKlassen(String fuehrerscheinKlassen) {
        this.fuehrerscheinKlassen = fuehrerscheinKlassen;
    }

    public Date getFuehrerscheinDatum() {
        return fuehrerscheinDatum;
    }

    public void setFuehrerscheinDatum(Date fuehrerscheinDatum) {
        this.fuehrerscheinDatum = fuehrerscheinDatum;
    }

    public String getFuehrerscheinBehoerde() {
        return fuehrerscheinBehoerde;
    }

    public void setFuehrerscheinBehoerde(String fuehrerscheinBehoerde) {
        this.fuehrerscheinBehoerde = fuehrerscheinBehoerde;
    }

    public boolean getGeloescht() {
        return geloescht;
    }

    public void setGeloescht(boolean geloescht) {
        this.geloescht = geloescht;
    }

    public Date getLetzteAenderungAm() {
        return letzteAenderungAm;
    }

    public void setLetzteAenderungAm(Date letzteAenderungAm) {
        this.letzteAenderungAm = letzteAenderungAm;
    }

    public String getLetzteAenderungDurch() {
        return letzteAenderungDurch;
    }

    public void setLetzteAenderungDurch(String letzteAenderungDurch) {
        this.letzteAenderungDurch = letzteAenderungDurch;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personalID != null ? personalID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personen)) {
            return false;
        }
        Personen other = (Personen) object;
        if ((this.personalID == null && other.personalID != null) || (this.personalID != null && !this.personalID.equals(other.personalID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.Personen[ personalID=" + personalID + " ]";
    }
}
