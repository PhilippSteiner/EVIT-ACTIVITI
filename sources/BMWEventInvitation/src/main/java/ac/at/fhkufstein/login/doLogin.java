/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.login;

import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.Personen;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import ac.at.fhkufstein.session.BmwUserFacade;
import ac.at.fhkufstein.session.PersonenFacade;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author
 * wolfgangteves
 */
@ManagedBean(name = "login")

public class doLogin {
	
 Integer uid;
 Integer role;
 String user;
 String pw;
 BmwUserController bmwUserController;
 BmwUser bmwUser;
 PersonenController personenController;
 Personen personen;
	public doLogin(){
	System.out.println("login class");
}
	public String login(){
		FacesContext context = FacesContext.getCurrentInstance();  
		
			bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
			personenController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{personenController}", PersonenController.class);

			
		//Find User Details by Email (User table)
		EntityManager em= ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
			 List p = em.createNamedQuery("BmwUser.findByEmail")
			 .setParameter("email", user)
			 .getResultList();
			 if(p.size()>0){
				bmwUser = (BmwUser) p.get(0);
			 }
			 
		//Find User Details by Username (User Table)
		EntityManager em2= ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
			 List p2 = em2.createNamedQuery("BmwUser.findByUsername")
			 .setParameter("username", user)
			 .getResultList();
			 if(p2.size()>0){
				bmwUser = (BmwUser) p2.get(0);
			 }
		//Find User Details by Email (Personen Table) JAy.
			 
		EntityManager em3= ((PersonenFacade) personenController.getFacade()).getEntityManager();
			 List p3 = em3.createNamedQuery("Personen.findByEMail1")
			 .setParameter("eMail1", user)
			 .getResultList();
			 if(p3.size()>0){
				personen = (Personen) p3.get(0);
				
		EntityManager em4= ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
			 List p4 = em4.createNamedQuery("BmwUser.findByPersonenID")
			 .setParameter("personenID", personen.getPersonalID().toString())
			 .getResultList();
			 if(p4.size()>0){
				 bmwUser = (BmwUser) p4.get(0);
			 }else{
				 System.out.println("System Error Login");
			 }
			 }
			 if(bmwUser!=null){
				 System.out.println("exists");
			 if(pw.equals(bmwUser.getPwd())){
				 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uid", bmwUser.getUid());
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", bmwUser.getRole());
				System.out.println("Role:"+bmwUser.getRole());
				if(bmwUser.getRole()==null){
					context.addMessage(null, new FacesMessage("Fehler", "Für diesen User ist keine Rolle Definiert"));  
			return "#";
				}else if(bmwUser.getRole()==1){
					//is BMW User
				return "/faces/index.xhtml";
				}else if(bmwUser.getRole()==2){
					//is Journalist
				return "/faces/BMW_Journalist/journalistmenue.xhtml";
				}else if(bmwUser.getRole()==3){
					//is Travel Agency
					
				  context.addMessage(null, new FacesMessage("Fehler", "Es gibt noch keine Oberfläche für Reisebüros"));  
				return "#";
				}else{
					//Unbekannte Rolle
				  context.addMessage(null, new FacesMessage("Fehler", "Diese Rolle ("+bmwUser.getRole().toString()+") ist dem System nicht bekannt"));  
				  return "#";
				}
				
			 }else{
				 
				  context.addMessage(null, new FacesMessage("Fehler", "Passwort nicht Korrekt"));  
			return "#";
			 	 
			 }
			 }else{
				 context.addMessage(null, new FacesMessage("Fehler", "User nicht gefunden"));  
				 return "#";
			 }
		
		
	}
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		System.out.println("logout Set");
		return "/faces/login.xhtml";
		
	}

	public Integer getUid() {
		
		return Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid").toString());
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getRole() {
		
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
	
}
