package ac.at.fhkufstein.EventFunc;

import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwFlightController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.session.BmwFlightFacade;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wolfgangteves
 */
@ManagedBean(name = "Flight")
@ViewScoped
//@PersistenceContext
public class Flight {
	private BmwFlightController bmwFlightController;
	private BmwEventController bmwEventController;
	private BmwEvent cev;
	private List p;

	public List getP() {
		return p;
	}

	public void setP(List p) {
		this.p = p;
	}
	
private Integer eventID;

	

	public Flight() {
		bmwFlightController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwFlightController}", BmwFlightController.class);
		bmwEventController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEventController}", BmwEventController.class);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		this.eventID = (Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));
		cev = bmwEventController.getFacade().find(eventID);
	


		try {
			
		         EntityManager em= ((BmwFlightFacade) bmwFlightController.getFacade()).getEntityManager();
			 p = em.createNamedQuery("BmwFlight.findByEventId")
			 .setParameter("eventId", cev)
			 .getResultList();
			 System.out.println("fuckin size"+p.size());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
}
