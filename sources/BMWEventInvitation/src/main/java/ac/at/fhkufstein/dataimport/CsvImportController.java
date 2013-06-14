/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.dataimport;

import ac.at.fhkufstein.bean.BmwEventController;
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
import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author mike
 */
@ManagedBean(name = "csvImportController")
@ViewScoped
public class CsvImportController {

    private UploadedFile csvFile;
    private final char CSV_SEPERATOR = ';';
    private final char CSV_QUOTECHAR = '"';
    private final String EMPTY_ERROR = "Die CSV-Datei enthählt keine Einträge";

    public UploadedFile getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(UploadedFile csvFile) {
        this.csvFile = csvFile;
    }

    public void upload() {

        if (csvFile != null) {
            try {
                importCsv(csvFile.getInputstream());

                FacesMessage msg = new FacesMessage("Erfolg", "Die Datei " + csvFile.getFileName() + " wurde erfolgreich importiert.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (Exception ex) {
                Logger.getLogger(CsvImportController.class.getName()).log(Level.SEVERE, null, ex);

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Die Datei " + csvFile.getFileName() + " konnte nicht importiert werden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Die Datei konnte nicht hochgeladen werden.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private void importCsv(InputStream is) throws Exception {

        importCsv(new BufferedReader(new InputStreamReader(is)));

    }

    private void importCsv(BufferedReader br) throws Exception {

        PersonenController personenController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{personenController}", PersonenController.class);
        personenController.init();

        SimpleDateFormat datum_sdfToDate = new SimpleDateFormat("dd.MM.yy");
        // Not used now
        SimpleDateFormat zeit_sec_sdfToDate = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        SimpleDateFormat zeit_sdfToDate = new SimpleDateFormat("dd.MM.yy HH:mm");

        CSVReader reader = new CSVReader(br, CSV_SEPERATOR, CSV_QUOTECHAR);
        String[] line = reader.readNext();

        if (line == null || line.length <= 0) {
            throw new Exception(EMPTY_ERROR);
        }

        Field[] personenFields = Personen.class.getDeclaredFields();
        Method[] tempPersonenMethods = Personen.class.getMethods();
        Method[] personenMethods = new Method[line.length];

        int personenFieldIndex = 0;

        for (int csvIndex = 0; csvIndex < line.length; csvIndex++) {
            for (Field field : personenFields) {
                Column column = field.getAnnotation(Column.class);

                if (column != null) {
                    if (line[csvIndex].equals(column.name())) {

                        for (Method personenMethod : tempPersonenMethods) {
                            if (personenMethod.getName().toLowerCase().equals("set" + field.getName().toLowerCase())) {
                                personenMethods[personenFieldIndex++] = personenMethod;
                                System.out.println(csvIndex + " " + line[csvIndex] + " " + field.getName() + " " + personenMethod.getName());
                            }
                        }
                    }
                }

            }
        }


        int lineIndex = 0;
        while ((line = reader.readNext()) != null) {
            lineIndex++;

            Personen person = null;
            int fieldIndex = 0;
             for (Method method : personenMethods) {
                 if (method.getName().endsWith("PersonalID")) {
                    person = personenController.getFacade().find(line[fieldIndex]);
                    break;
                 }
                fieldIndex++;
             }

            boolean newEntry = false;
            if (person == null) {
                newEntry = true;
                person = personenController.prepareCreate(null);
            }

            fieldIndex = -1;
            for (Method method : personenMethods) {


                fieldIndex++;

                if (method.getName().endsWith("PersonalID")) {
                    if (newEntry == true) {
                        method.invoke(person, getUniqueidentifierString(line[fieldIndex]));
                    }
                    continue;
                }

                if (method.getName().endsWith("Geburtsdatum") || method.getName().endsWith("FuehrerscheinDatum") || method.getName().endsWith("LetzteAenderungAm")) {
                    if (!line[fieldIndex].isEmpty()) {
                        try {
                            method.invoke(person, zeit_sec_sdfToDate.parse(line[fieldIndex]));
                        } catch (ParseException zeitsecParseEx) {
                            try {
                                method.invoke(person, zeit_sdfToDate.parse(line[fieldIndex]));
                            } catch (ParseException zeitParseEx) {
                                try {
                                    method.invoke(person, datum_sdfToDate.parse(line[fieldIndex]));
                                } catch (ParseException datumParseEx) {
                                    throw new Exception("Das Datum \""+line[fieldIndex]+ "\" in der " + (lineIndex + 1) + ". Zeile kann nicht gelesen werden.");
                                }
                            }
                        }

                    }
                    continue;
                }

                if (method.getParameterTypes()[0].equals(Boolean.TYPE)) {
                    method.invoke(person, Boolean.parseBoolean(line[fieldIndex]));
                    continue;
                }

                method.invoke(person, line[fieldIndex]);

            }


            if (newEntry) {
                personenController.saveNew(null);
            } else {
                personenController.setSelected(person);
                personenController.save(null);
            }
        }

        if (lineIndex == 0) {
            throw new Exception(EMPTY_ERROR);
        }
    }

    /**
     * returns uniqueidentifier without braces
     *
     * @param csvInput
     * @return
     */
    public static String getUniqueidentifierString(String csvInput) {
        return csvInput.replace('{', ' ').replace('}', ' ').trim();
    }
    
}
