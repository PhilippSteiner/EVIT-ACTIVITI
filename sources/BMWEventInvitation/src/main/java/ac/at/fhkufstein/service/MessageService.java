/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author mike
 */
public class MessageService {

    public static void showInfo(FacesContext context, String message) {
        showInfo(context, null, message);
    }
    public static void showInfo(FacesContext context, String summary, String message) {
        System.out.println(message);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, summary, message));
    }

    public static void showError(FacesContext context, String message) {
        showError(context, null, message);
    }

    public static void showError(FacesContext context, String summary, String message) {
        System.out.println(message);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, message));
    }
}
