/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

/**
 *
 * @author Alex
 */
public class JournalistEvent {
    
    private String name;
    private String von;
    private String bis;

    public JournalistEvent(String name, String von, String bis) {
        
        this.name = name;
        this.von = von;
        this.bis = bis;
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
    
    
}
