/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mike
 */
public enum ParticipantStatus {

    INVITED("eingeladen"),
    ACCEPTED("zugesagt"),
    REFUSED("abgesagt"),
    SUBSTITUTE("vertretung"),
    SUBSTITUTE_SELECTED("vertretung_gewaehlt"),
    ARRIVAL_ON_ONES_OWN("selbstanreise"),
    FLIGHT_SELECTED("flugausgewaehlt"),
    SPECIAL_FLIGHT("spezialflug"),
    TICKET("ticket");
    
    private final String name;
    
    private ParticipantStatus(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
       return name;
    }
    
    private static List<ParticipantStatus> getAllStati() {
        List<ParticipantStatus> stati = new ArrayList<ParticipantStatus>();
        
        stati.add(INVITED);
        stati.add(ACCEPTED);
        stati.add(REFUSED);
        stati.add(SUBSTITUTE);
        stati.add(SUBSTITUTE_SELECTED);
        stati.add(FLIGHT_SELECTED);
        stati.add(SPECIAL_FLIGHT);
        stati.add(ARRIVAL_ON_ONES_OWN);
        stati.add(TICKET);
        
        return stati;
    }
    
    public static ParticipantStatus getStatus(String statusString) {
        
        for(ParticipantStatus status : getAllStati()) {
            if(status.toString().equals(statusString)) {
                return status;
            }
        }
        
        return null;
    }
}
