/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "order")
@SessionScoped
public class FlugAddBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String von;
    private String bis;
    private String abflugzeit;
    private String ankunftzeit;

    //init noch
    private static final ArrayList<FlugAddBean.Flug> orderList = new ArrayList<FlugAddBean.Flug>();
            

    public ArrayList<FlugAddBean.Flug> getOrderList() {

        return orderList;

    }

    public String addAction() {
        
        System.out.println("");

        FlugAddBean.Flug order = new FlugAddBean.Flug(this.von, this.bis,
                this.abflugzeit, this.abflugzeit);

        orderList.add(order);
        return null;
    }

    public String deleteAction(FlugAddBean.Flug order) {

        orderList.remove(order);
        return null;
    }

    public String getVon() {
        return von;
    }

    public void setVon(String von) {
        this.von = von;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public String getAbflugzeit() {
        return abflugzeit;
    }

    public void setAbflugzeit(String abflugzeit) {
        this.abflugzeit = abflugzeit;
    }

    public String getAnkunftzeit() {
        return ankunftzeit;
    }

    public void setAnkunftzeit(String ankunftzeit) {
        this.ankunftzeit = ankunftzeit;
    }
    
    

    public static class Flug {

        /*String orderNo;
        String productName;
        BigDecimal price;
        int qty;*/
        
        String von;
        String bis;
        String abflugzeit;
        String ankunftzeit;

        public Flug(String von, String bis, String abflugzeit, String ankunftzeit) {
            this.von = von;
            this.bis = bis;
            this.abflugzeit = abflugzeit;
            this.ankunftzeit = ankunftzeit;
        }

        public String getVon() {
            return von;
        }

        public void setVon(String von) {
            this.von = von;
        }

        public String getBis() {
            return bis;
        }

        public void setBis(String bis) {
            this.bis = bis;
        }

        public String getAbflugzeit() {
            return abflugzeit;
        }

        public void setAbflugzeit(String abflugzeit) {
            this.abflugzeit = abflugzeit;
        }

        public String getAnkunftzeit() {
            return ankunftzeit;
        }

        public void setAnkunftzeit(String ankunftzeit) {
            this.ankunftzeit = ankunftzeit;
        }

        
    }
}