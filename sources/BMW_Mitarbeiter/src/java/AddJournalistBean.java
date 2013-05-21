/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "AddJournalistBean")
@SessionScoped
public class AddJournalistBean implements Serializable {

    private final static String[] vornamen;
    private final static String[] nachnamen;
    private final static String[] firma;
    private List<Journalist> carsSmall;
    private List<Journalist> droppedCars;

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
        firma[2] = "Playboy";
        firma[3] = "Mein Auto und die Berge";

    }

    public AddJournalistBean() {

        carsSmall = new ArrayList<Journalist>();
        droppedCars = new ArrayList<Journalist>();

        populateRandomCars(carsSmall, 4);
    }

    private void populateRandomCars(List<Journalist> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Journalist(vornamen[i],nachnamen[i], firma[i] ));
        }
    }

    public List<Journalist> getCarsSmall() {
        return carsSmall;
    }

    public void setCarsSmall(List<Journalist> carsSmall) {
        this.carsSmall = carsSmall;
    }

    public List<Journalist> getDroppedCars() {
        return droppedCars;
    }

    public void setDroppedCars(List<Journalist> droppedCars) {
        this.droppedCars = droppedCars;
    }



    public void onCarDrop(DragDropEvent ddEvent) {
        
        Journalist car = ((Journalist) ddEvent.getData());

        droppedCars.add(car);
        carsSmall.remove(car);
        
        System.out.println("DROP DONE!");
    }

}