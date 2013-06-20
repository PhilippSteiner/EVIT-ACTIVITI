/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.service;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.AbstractController;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.entity.BmwParticipants;
import javax.faces.context.FacesContext;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author mike
 */
public class PersistenceService {

    public static void save(Class<? extends AbstractController> controllerClass, Object entity) {

        AbstractController controller = getManagedBeanInstance(controllerClass);
        controller.setSelected(entity);
        controller.save(null);
    }

    public static Object loadByObject(Class<? extends AbstractController> controllerClass, Object id) {

        return getManagedBeanInstance(controllerClass).getFacade().find( id );

    }

    public static Object loadByInteger(Class<? extends AbstractController> controllerClass, Object id) {

        return getManagedBeanInstance(controllerClass).getFacade().find( Integer.parseInt(String.valueOf( id )) );

    }

    public static List loadAll(Class<? extends AbstractController> controllerClass) {

        return getManagedBeanInstance(controllerClass).getFacade().findAll();

    }

    public static <T> T getManagedBeanInstance(Class<T> managedBeanClass) {
        return FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{"+managedBeanClass.getAnnotation(ManagedBean.class).name()+"}", managedBeanClass);
    }
}
