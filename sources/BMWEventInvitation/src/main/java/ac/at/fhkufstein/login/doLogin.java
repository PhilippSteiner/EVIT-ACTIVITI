/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.login;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.activiti.Services;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.Personen;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwUserFacade;
import ac.at.fhkufstein.session.PersonenFacade;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import org.activiti.engine.runtime.Execution;

/**
 *
 * @author wolfgangteves
 */
@ManagedBean(name = "login")
@SessionScoped
public class doLogin implements Serializable {

    Integer uid;
    Integer role;
    String user;
    String pw;
    String name;
    BmwUserController bmwUserController;
    BmwUser bmwUser;
    PersonenController personenController;
    Personen personen;

    public String login() {

        for (Execution exec : Services.getRuntimeService().createExecutionQuery().processInstanceId("9901").list()) {
            System.out.println("-----------  current Activity: " + exec.getActivityId());
        }
        FacesContext context = FacesContext.getCurrentInstance();
        //instanciate controllers
        bmwUserController = PersistenceService.getManagedBeanInstance(BmwUserController.class);
        personenController = PersistenceService.getManagedBeanInstance(PersonenController.class);


        //Find User Details by Email (User table)
        EntityManager em = ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
        List p = em.createNamedQuery("BmwUser.findByEmail")
                .setParameter("email", user)
                .getResultList();
        if (p.size() > 0) {
            //if result this is the searched one!
            bmwUser = (BmwUser) p.get(0);
        }

        //Find User Details by Username (User Table)
        EntityManager em2 = ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
        List p2 = em2.createNamedQuery("BmwUser.findByUsername")
                .setParameter("username", user)
                .getResultList();
        if (p2.size() > 0) {
            //if result this is the searched one!
            bmwUser = (BmwUser) p2.get(0);
        }
        //Find User Details by Email (Personen Table) JAy.

        EntityManager em3 = ((PersonenFacade) personenController.getFacade()).getEntityManager();
        List p3 = em3.createNamedQuery("Personen.findByEMail1")
                .setParameter("eMail1", user)
                .getResultList();
        if (p3.size() > 0) {
            //if result this is the searched one!
            personen = (Personen) p3.get(0);
            //Get the BMW User for the person
            EntityManager em4 = ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
            List p4 = em4.createNamedQuery("BmwUser.findByPersonenID")
                    .setParameter("personenID", personen.getPersonalID().toString())
                    .getResultList();
            if (p4.size() > 0) {
                //check if bmw user is found
                bmwUser = (BmwUser) p4.get(0);
            } else {
                System.out.println("System Error Login");
            }
        }

        if (bmwUser != null) {
            name = bmwUser.getUsername();
            //check if one user is found
            if (pw.equals(bmwUser.getPwd())) {//check if password is correct
                //Set uid and role
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uid", bmwUser.getUid());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", bmwUser.getRole());

                System.out.println("Role:" + bmwUser.getRole()); //output role
                if (bmwUser.getRole() == null) { //Return error if no role is defined
                    context.addMessage(null, new FacesMessage("Fehler", "Für diesen User ist keine Rolle Definiert"));
                    return "#";
                } else if (bmwUser.getRole() == 1) {
                    //is BMW User -> Redirect to BMW User
                    return "index.xhtml";
                } else if (bmwUser.getRole() == 2) {
                    //is Journalist -> Redirect to Journalist interface
                    return "/faces/BMW_Journalist/journalistmenue.xhtml";
                } else if (bmwUser.getRole() == 3) {
                    //is Travel Agency
                    //Todo: Redirect to Travel Agency Interface
                    return "/faces/Travelagency/index.xhtml";

                } else {
                    //Unbekannte Rolle
                    context.addMessage(null, new FacesMessage("Fehler", "Diese Rolle (" + bmwUser.getRole().toString() + ") ist dem System nicht bekannt"));
                    return "#";
                }

            } else {
                //Password not correct
                context.addMessage(null, new FacesMessage("Fehler", "Passwort nicht Korrekt"));
                return "#";

            }
        } else {
            //User not found
            context.addMessage(null, new FacesMessage("Fehler", "User nicht gefunden"));
            return "#";
        }


    }

    public String logout() {

        System.out.println("logout called: trying to get session...");

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("old_admin") != null) {

            //Its a admin going back

            Integer ol = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("old_admin").toString());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uid", ol);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", 1);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("old_admin", null);

            System.out.println("Its a admin going back");
            return "index.xhtml";
        } else {
            //Its a regular user logging out
            //Delete Session
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            //redirect to login page
            return "login.xhtml";
        }

    }

    public String loginAsJournalist(Integer jid) {
        try {
            System.out.println("login as journalist called");

            loginAsUser(jid, 2);

            System.out.println("Logging in as Journalist... JID:" + jid);

            return "/faces/BMW_Journalist/journalistmenue.xhtml";
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "login.xhtml";
    }
    
    public String loginAsReisebuero(Integer rid) {
        try {
            System.out.println("login as reisebuero called");

            loginAsUser(rid, 3);

            System.out.println("Logging in as Reisebuero... RID:" + rid);

            return "/faces/Travelagency/index.xhtml";
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "login.xhtml";
    }

    public void loginAsUser(Integer desiredUid, Integer desiredRole) throws Exception {

        Integer role = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role").toString());
        if (role != 1) {//Some Security
            throw new Exception("The user is not an Administrator, so no privileges for login as user " + desiredUid);
        }
        Integer ol_admin = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid").toString());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("old_admin", ol_admin);
        System.out.println("Old Admin Saved in old_admin:" + ol_admin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uid", desiredUid);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", desiredRole);

    }

    public void checkBmwSession() {
        Integer role = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role").toString());
        if (role != 1) {
            System.out.println("invalid access to Bmw");
            //Delete Session
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            try {//redirect to login page
                FacesContext.getCurrentInstance().getExternalContext().dispatch("login.xhtml");
            } catch (Exception e) {
                System.out.println("Redirect to login page failed");
            }
        }
    }

    public void checkJournalistSession() {
        Integer role = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role").toString());
        if (role != 2) {
            System.out.println("invalid access to Journalist");
            //Delete Session
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            try {//redirect to login page
                FacesContext.getCurrentInstance().getExternalContext().dispatch("login.xhtml");
            } catch (Exception e) {
                System.out.println("Redirect to login page failed");
            }
        }
    }

    public void checkTravelagencySession() {
        Integer role = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role").toString());
        if (role != 3) {
            System.out.println("invalid access to Travelagency");
            //Delete Session
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            try {//redirect to login page
                FacesContext.getCurrentInstance().getExternalContext().dispatch("login.xhtml");
            } catch (Exception e) {
                System.out.println("Redirect to login page failed");
            }
        }
    }

    public Integer getUid() {
        //Get uid from session
        return Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid").toString());
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRole() {
        //get role from session
        return Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role").toString());
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
