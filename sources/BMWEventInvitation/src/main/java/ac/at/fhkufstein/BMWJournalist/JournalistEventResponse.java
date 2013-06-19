/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.Location;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "JournalistEventResponse")
@SessionScoped
public class JournalistEventResponse implements Serializable {

    private static final long serialVersionUID = -1776353555799643520L;
    private List<SelectItem> auswahlmoeglichkeiten;
    private String selectedAuswahlmoeglichkeit;

    public JournalistEventResponse() {
        auswahlmoeglichkeiten = new ArrayList<SelectItem>();
    }

    @PostConstruct
    private void initialize() {
        
        auswahlmoeglichkeiten.add(new SelectItem("Zusagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Absagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Vertetung schicken"));
    }

    public List<SelectItem> getAuswahlmoeglichkeiten() {
        return auswahlmoeglichkeiten;
    }

    public void setAuswahlmoeglichkeiten(List<SelectItem> auswahlmoeglichkeiten) {
        this.auswahlmoeglichkeiten = auswahlmoeglichkeiten;
    }

    public String getSelectedAuswahlmoeglichkeit() {
        return selectedAuswahlmoeglichkeit;
    }

    public void setSelectedAuswahlmoeglichkeit(String selectedAuswahlmoeglichkeit) {
        this.selectedAuswahlmoeglichkeit = selectedAuswahlmoeglichkeit;
        
        System.out.println("Slected: "+this.selectedAuswahlmoeglichkeit);
    }

    //************************************************************************************
    
        //************************************************************************************
    
        //************************************************************************************
    
        //************************************************************************************
    
    public void einladungBeantworten(){
    
        System.out.println("eantwortet mit: "+this.selectedAuswahlmoeglichkeit);
    
    }
    
    
    public void stateChangeListener(ValueChangeEvent event) {
        
        System.out.println("State Listener Selected State: "+this.selectedAuswahlmoeglichkeit);
        
    }
}
