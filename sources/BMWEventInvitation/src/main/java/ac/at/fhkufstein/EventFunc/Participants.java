/**
 * @author wolfgang Teves
 * @version 0.6
 */
package ac.at.fhkufstein.EventFunc;

import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;


@ManagedBean(name = "Participants")
@ViewScoped

public class Participants implements Serializable{
/*
  * This Bean Provides Functionality for adding Participants
  */

	//private BmwUserController bmwUserController; 
	private BmwEventController bmwEventController;// Controllers
	private BmwParticipantsController bmwParticipantsController;
	private Integer eventID; //Current Event ID
	private List<BmwUser> u = new ArrayList<BmwUser>();//List of Users
	private BmwEvent current;//Current Event
	private BmwUser[] selected; //Selected Participants
	private BmwParticipants[] selectedp; //Selected Participants


//	public EntityManager em;
	/**
	 * Creates
	 * a
	 * new
	 * instance
	 * of
	 * bmwEventFunc
	 */
	public Participants() {


		try {



			//bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
			//Instanciate Controllers
			PersonenController personenController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{personenController}", PersonenController.class);
			bmwEventController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEventController}", BmwEventController.class);
			bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);
			
			//Get eventID from URL
			FacesContext facesContext = FacesContext.getCurrentInstance();
			this.eventID = (Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));

			//Get Current Event for eventID
			current = bmwEventController.getFacade().find(eventID);

			
			//Load participants for Event
			EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();
			List p = em.createNamedQuery("BmwParticipants.findByEventId")
					.setParameter("id", current)
					.getResultList();
			
			
			//Set Size for Arrays
			selected = new BmwUser[p.size()];
			selectedp = new BmwParticipants[p.size()];
			
			//Iterate alle Participants
			Iterator<BmwParticipants> it = p.iterator();
			int ii = 0;

			while (it.hasNext()) {
				BmwParticipants x = it.next();
				selected[ii] = x.getUserId();//Write in Vars
				selectedp[ii] = x;
				ii++;
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public Integer getEventID() {
		return eventID;
	}

	public BmwUser[] getSelected() {
		return selected;
	}

	public void setSelected(BmwUser[] selected) {
		this.selected = selected;
	}

	public void saveSelected() {//Saves the participants
		//Get all Participants for Event
		EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();
		List p = em.createNamedQuery("BmwParticipants.findByEventId")
				.setParameter("id", current)
				.getResultList();

		//System.out.println("Size:" + p.size());
		//System.out.println("Anzahl Selected" + selected.length);
		int i = 0;
		while (i < selected.length) {
			BmwUser b = selected[i];

			Iterator<BmwParticipants> it = p.iterator();

			while (it.hasNext()) {
				//check if element is already in database, if yes, delete from array "selected"
				if (b.getUid() == it.next().getUserId().getUid()) {//Delete all Participants which are already in database from selected array, we dont want to invite them again. Delete of an invitation is currently not possible
					selected[i] = null;
				}
			}
			i++;
		}

		int ii = 0;
		while (ii < selected.length) { //save to database

			if (selected[ii] != null) {//Only those which are left in the array
				BmwUser b = selected[ii];

				BmwParticipants pc = bmwParticipantsController.prepareCreate(null);
				pc.setUserId(b);
				pc.setEventId(current);
				pc.setPState("eingeladen");//Set state to eingeladen. This is the initial State
				System.out.println("Added:" + b.getPersonenID().getNameVollstaendig());
				bmwParticipantsController.setSelected(pc);
				bmwParticipantsController.saveNew(null);//save to database
			}
			ii++;
		}

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Successful", "Teilnehmer gespeichert")); //Send message 
	
		
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}
	public BmwEvent getCurrent() {
		return current;
	}

	public void setCurrent(BmwEvent current) {
		this.current = current;
	
	}

	

	public List<BmwUser> getU() {
		return u;
	}

	public void setU(List<BmwUser> u) {
		this.u = u;
	}
	
	
	public BmwParticipants[] getSelectedp() {
		return selectedp;
	}

	public void setSelectedp(BmwParticipants[] selectedp) {
		this.selectedp = selectedp;
	}
}
