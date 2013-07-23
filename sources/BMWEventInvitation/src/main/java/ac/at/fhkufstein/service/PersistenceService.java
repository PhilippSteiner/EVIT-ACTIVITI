/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.service;

import ac.at.fhkufstein.bean.AbstractController;
import ac.at.fhkufstein.session.AbstractFacade;
import java.lang.reflect.InvocationTargetException;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author mike
 */
public class PersistenceService {

    public final static String PROJECTNAME = "BMWEventInvitation";
    public final static String JNDI_LOOKUP = "java:global/" + PROJECTNAME + "/";

    public static void save(Class<? extends AbstractController> controllerClass, Object entity) {

        AbstractController controller = getManagedBeanInstance(controllerClass);
        controller.setSelected(entity);
        controller.save(null);
    }

    public static Object loadByObject(Class<? extends AbstractController> controllerClass, Object id) {
        return getManagedBeanInstance(controllerClass).getFacade().find(id);

    }

    public static Object loadByInteger(Class<? extends AbstractController> controllerClass, Object id) {
        AbstractFacade facade = getManagedBeanInstance(controllerClass).getFacade();
        System.err.println("facade of controller "+controllerClass+" is "+facade);
        return facade.find(Integer.parseInt(String.valueOf(id)));

    }

    public static List loadAll(Class<? extends AbstractController> controllerClass) {
        return getManagedBeanInstance(controllerClass).getFacade().findAll();
    }

    public static <T> T getManagedBeanInstance(Class<T> managedBeanClass) {
        if (FacesContext.getCurrentInstance() == null) {
            System.out.println("FacesContext null, so requesting plain class "+managedBeanClass);
            return getPlainClass(managedBeanClass);
        }
        System.out.println("FacesContext instance exists, so get class "+managedBeanClass);
        return FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{" + managedBeanClass.getAnnotation(ManagedBean.class).name() + "}", managedBeanClass);
    }

    private static <T> T getPlainClass(Class<T> desiredClass) {
        T plainClass = null;
        try {
            
            System.out.println("getting plain class "+desiredClass);
            if (desiredClass.getSuperclass().equals(AbstractController.class)) {
                System.out.println("getting plain class "+desiredClass+"(instance of abstractcontroller)");
                plainClass = desiredClass.getConstructor(Boolean.class).newInstance(true);
            } else {
                plainClass = desiredClass.newInstance();
            }
            
            System.out.println("got plain class "+plainClass);
        } catch (InstantiationException ex) {
            Logger.getLogger(PersistenceService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PersistenceService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PersistenceService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PersistenceService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PersistenceService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PersistenceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return plainClass;
    }

    public static <T extends AbstractFacade> T getFacadeJndiLookup(Class<T> facadeClass) {
        T ejbFacade = null;
        try {
            System.out.println("starting jndi_lookup...");
            InitialContext context = new InitialContext();
            ejbFacade = (T) context.lookup(JNDI_LOOKUP + facadeClass.getSimpleName());
            System.out.println("jndi_lookup: "+ejbFacade);
        } catch (NamingException ex) {
            Logger.getLogger(PersistenceService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ejbFacade;
    }
}
