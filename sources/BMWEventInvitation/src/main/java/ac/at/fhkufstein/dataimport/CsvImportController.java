/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.dataimport;

import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.Personen;
import au.com.bytecode.opencsv.CSVReader;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.text.SimpleDateFormat;

/**
 *
 * @author mike
 */
@ManagedBean(name = "csvImportController")
@ViewScoped
public class CsvImportController {

    private UploadedFile csvFile;
    private boolean columnNames;
    private final char CSV_SEPERATOR = ';';
    private final char CSV_QUOTECHAR = '"';

    public UploadedFile getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(UploadedFile csvFile) {
        this.csvFile = csvFile;
    }

    public void upload() {

        if (csvFile != null) {
            try {
                importCsv(csvFile.getInputstream(), columnNames ? 1 : 0);

                FacesMessage msg = new FacesMessage("Erfolg", "Die Datei "+csvFile.getFileName() + " wurde erfolgreich importiert.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (Exception ex) {
                Logger.getLogger(CsvImportController.class.getName()).log(Level.SEVERE, null, ex);

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Die Datei "+csvFile.getFileName() + " konnte nicht importiert werden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Die Datei konnte nicht hochgeladen werden.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private void importCsv(InputStream is, int firstLine) throws IOException {

        importCsv(new BufferedReader(new InputStreamReader(is)), firstLine);

    }

    private void importCsv(BufferedReader br, int firstLine) throws IOException {


        PersonenController personenController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{personenController}", PersonenController.class);
        personenController.init();

        SimpleDateFormat datum_sdfToDate = new SimpleDateFormat("dd.MM.yy");
        SimpleDateFormat zeit_sdfToDate = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

        CSVReader reader = new CSVReader(br, CSV_SEPERATOR, CSV_QUOTECHAR, firstLine);
        String[] line;
        int i = 0;

        while ((line = reader.readNext()) != null) {
            i++;

            Personen person = personenController.getFacade().find(line[0]);

            boolean newEntry = false;
            if (person == null) {
                newEntry = true;
                person = personenController.prepareCreate(null);
                person.setPersonalID(getUniqueidentifierString(line[0]));
            }

            // put column values to members
            person.setSpeichernUnter(line[1]);
            person.setOrgEinheit(line[2]);
            person.setFirma(line[3]);
            person.setAnrede(line[4]);
            person.setAkadTitel(line[5]);
            person.setBerufstitel(line[6]);
            person.setVorname(line[7]);
            person.setNachname(line[8]);
            person.setNameVollstaendig(line[9]);
            person.setAbteilung(line[10]);
            person.setPosition(line[11]);
            person.setStrasse(line[12]);
            person.setPlz(line[13]);
            person.setOrt(line[14]);
            person.setLand(line[15]);
            person.setBriefkopf(line[16]);
            person.setWeitereStrasse(line[17]);
            person.setWeiterePLZ(line[18]);
            person.setWeitererOrt(line[19]);
            person.setWeiteresLand(line[20]);
            person.setWeitererBriefkopf(line[21]);
            person.setBriefanredeSie(line[22]);
            person.setEMail1(line[23]);
            person.setEMail2(line[24]);
            person.setWebseite(line[25]);
            person.setNotizen(line[26]);
            person.setTelefonGeschaeftlich(line[27]);
            person.setTelefonPrivat(line[28]);
            person.setFax(line[29]);
            person.setTelefonMobil(line[30]);
            try {
                if (!line[31].isEmpty()) {
                    person.setGeburtsdatum(datum_sdfToDate.parse(line[31]));
                }
            } catch (ParseException ex) {
                Logger.getLogger(CsvImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            person.setKinderPartner(line[32]);
            person.setHobby(line[33]);
            person.setKategorien(line[34]);
            person.setVerteiler(line[35]);
            person.setFotoName(line[36]);
            person.setKIDSKontaktNr(line[37]);
            person.setFuehrerscheinNummer(line[38]);
            person.setFuehrerscheinKlassen(line[39]);
            try {
                if (!line[40].isEmpty()) {
                    person.setFuehrerscheinDatum(datum_sdfToDate.parse(line[40]));
                }
            } catch (ParseException ex) {
                Logger.getLogger(CsvImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            person.setFuehrerscheinBehoerde(line[41]);
            person.setGeloescht(Boolean.parseBoolean(line[42]));
            try {
                if (!line[43].isEmpty()) {
                    person.setLetzteAenderungAm(datum_sdfToDate.parse(line[43]));
                }
            } catch (ParseException ex) {
                Logger.getLogger(CsvImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            person.setLetzteAenderungDurch(line[44]);


            if (newEntry) {
                personenController.saveNew(null);
            } else {
                personenController.setSelected(person);
                personenController.save(null);
            }
        }
    }

    public static String getUniqueidentifierString(String csvInput) {
        return csvInput.substring(1, 37);
    }

    /**
     * @return the columnNames
     */
    public boolean isColumnNames() {
        return columnNames;
    }

    /**
     * @param columnNames the columnNames to set
     */
    public void setColumnNames(boolean columnNames) {
        this.columnNames = columnNames;
    }
}
