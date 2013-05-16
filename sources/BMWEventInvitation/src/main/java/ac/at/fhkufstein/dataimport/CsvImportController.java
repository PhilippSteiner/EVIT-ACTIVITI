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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author mike
 */
@ManagedBean
@RequestScoped
public class CsvImportController {

    private UploadedFile csvFile;
    private boolean columnNames;
    private final char CSV_SEPERATOR = ',';
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

                FacesMessage msg = new FacesMessage("Succesful", csvFile.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (IOException ex) {
                Logger.getLogger(CsvImportController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void importCsv(InputStream is, int firstLine) throws IOException {

        importCsv(new BufferedReader(new InputStreamReader(is)), firstLine);

    }

    private void importCsv(BufferedReader br, int firstLine) throws IOException {

        CSVReader reader = new CSVReader(br, CSV_SEPERATOR, CSV_QUOTECHAR, firstLine);
        String[] line;
        while ((line = reader.readNext()) != null) {

            PersonenController personenController = new PersonenController();
            Personen person = new Personen();


            // put column values to members
            person.setPersonalID(line[0]);


            personenController.setSelected(person);
            personenController.saveNew(new ActionEvent(null));
        }
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
