/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.login;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author
 * wolfgangteves
 */
@ManagedBean(name = "loginData")
public class login implements PhaseListener {
		Integer uid;
		FacesContext facesContext;
		HttpSession session;

	public void afterPhase(PhaseEvent event) {

		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		facesContext = event.getFacesContext();
		session = (HttpSession) facesContext.getExternalContext().getSession(false);
		//String currentPage = facesContext.getViewRoot().getViewId(); //Holt sich die Aktuelle Seite
		//System.out.println("currentP"+currentPage);
		String currentPage = ((HttpServletRequest) ec.getRequest()).getRequestURI();
		
		System.out.println(currentPage);
		boolean isLoginPage = (currentPage.lastIndexOf("/login.xhtml") > -1);

		System.out.println(isLoginPage + currentPage);
		
		System.out.println("session" + session);
		System.out.println(isLoginPage+"Login Page");
		
		if(!isLoginPage){
		System.out.println("no Login Page");	
		if (session == null) {
			System.out.println("Session is null");
			try {
				
				ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");

			} catch (IOException ex) {
				Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
			}

		} else {
			System.out.println("Session not null");

			try {
				System.out.println("try to access uid");
				if(session.getAttribute("uid")==null){
					ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
				}
					
				
			} catch (Exception x) {
				try {
					
					ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");

				} catch (IOException ex) {
					Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}}

		

	}

	public void beforePhase(PhaseEvent event) {
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	
}