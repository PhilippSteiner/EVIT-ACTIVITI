/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "AddJournalistBean")
@RequestScoped
public class AddJournalistBean implements Serializable {

    private final static String[] vornamen;
    private final static String[] nachnamen;
    private final static String[] firma;
    private List<Journalist> allJournalists;
    private List<Journalist> addedJournalistst;
    private List<Journalist> filteredJournalistst;

    static {
        vornamen = new String[4];
        vornamen[0] = "Hans";
        vornamen[1] = "Susi";
        vornamen[2] = "Klaus";
        vornamen[3] = "Toni";

        nachnamen = new String[4];
        nachnamen[0] = "Meier";
        nachnamen[1] = "Huber";
        nachnamen[2] = "Richter";
        nachnamen[3] = "Berg";

        firma = new String[4];
        firma[0] = "Auto Bild";
        firma[1] = "Automotive";
        firma[2] = "Cars";
        firma[3] = "Mein Auto und die Berge";

    }

    public AddJournalistBean() {

        allJournalists = new ArrayList<Journalist>();
        addedJournalistst = new ArrayList<Journalist>();

        populateRandomCars(allJournalists, 4);
    }

    private void populateRandomCars(List<Journalist> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Journalist(vornamen[i],nachnamen[i], firma[i] ));
        }
    }

    public List<Journalist> getAllJournalists() {
        return allJournalists;
    }

    public void setAllJournalists(List<Journalist> allJournalists) {
        this.allJournalists = allJournalists;
    }

    public List<Journalist> getAddedJournalistst() {
        return addedJournalistst;
    }

    public void setAddedJournalistst(List<Journalist> addedJournalistst) {
        this.addedJournalistst = addedJournalistst;
    }

   
    public List<Journalist> getFilteredJournalistst() {
        return filteredJournalistst;
    }

    public void setFilteredJournalistst(List<Journalist> filteredJournalistst) {
        this.filteredJournalistst = filteredJournalistst;
    }

    

    public void onCarDrop(DragDropEvent ddEvent) {
        
        Journalist car = ((Journalist) ddEvent.getData());

        addedJournalistst.add(car);
        allJournalists.remove(car);
        
        System.out.println("DROP DONE!");
    }

}