/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.persistence;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.AbstractController;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.entity.BmwParticipants;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 *
 * @author mike
 */
public class PersistenceService {

    public static void save(Class<? extends AbstractController> controllerClass, Object entity) {

        AbstractController controller = getControllerInstance(controllerClass);
        controller.setSelected(entity);
        controller.save(null);
    }

    public static Object loadByObject(Class<? extends AbstractController> controllerClass, Object id) {

        return getControllerInstance(controllerClass).getFacade().find( id );

    }

    public static Object loadByInteger(Class<? extends AbstractController> controllerClass, Object id) {

        return getControllerInstance(controllerClass).getFacade().find( Integer.parseInt(String.valueOf( id )) );

    }

    public static List loadAll(Class<? extends AbstractController> controllerClass) {

        return getControllerInstance(controllerClass).getFacade().findAll();

    }

    public static <T extends AbstractController> T getControllerInstance(Class<T> controllerClass) {
        return FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{"+controllerClass.getSimpleName().substring(0, 1).toLowerCase()+controllerClass.getSimpleName().substring(1)+"}", controllerClass);
    }
}
