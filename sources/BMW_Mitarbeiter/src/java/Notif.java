/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class Notif {
    
    String event;
    String name;
    String vertretung;

    public Notif(String event, String name, String vertretung) {
        this.event = event;
        this.name = name;
        this.vertretung = vertretung;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVertretung() {
        return vertretung;
    }

    public void setVertretung(String vertretung) {
        this.vertretung = vertretung;
    }

    
}
