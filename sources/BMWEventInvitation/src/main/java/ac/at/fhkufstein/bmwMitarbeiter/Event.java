/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class Event {
    
    String name;
    String von;
    String bis;
    String ort;
    int kapazitaet;
    int zusagen;

    public Event(String name, String von, String bis, String ort) {
        this.name = name;
        this.von = von;
        this.bis = bis;
        this.ort = ort;
    }

    public Event(String name, String von, String bis, String ort, int kapazitaet, int zusagen) {
        this.name = name;
        this.von = von;
        this.bis = bis;
        this.ort = ort;
        this.kapazitaet = kapazitaet;
        this.zusagen = zusagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getKapazitaet() {
        return kapazitaet;
    }

    public void setKapazitaet(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public int getZusagen() {
        return zusagen;
    }

    public void setZusagen(int zusagen) {
        this.zusagen = zusagen;
    }
    
    
    
}
