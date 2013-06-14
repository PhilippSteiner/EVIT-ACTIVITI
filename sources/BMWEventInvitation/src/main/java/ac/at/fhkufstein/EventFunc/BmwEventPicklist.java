/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.EventFunc;

import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.Personen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author
 * wolfgangteves
 */
@ManagedBean(name = "bmwEventPicklist")
@ViewScoped
//@PersistenceContext
public class BmwEventPicklist {

	private BmwUserController bmwUserController; // +setter
	private DualListModel<String> cities;
	private Integer eventID;
	private BmwEventController bmwEventController;
	private BmwParticipantsController bmwParticipantsController;
	private List<BmwUser> u = new ArrayList<BmwUser>();
	private BmwEvent current;
	private BmwUser[] selected;

//	public EntityManager em;
	/**
	 * Creates
	 * a
	 * new
	 * instance
	 * of
	 * bmwEventFunc
	 */
	public BmwEventPicklist() {


		try {

			bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
			PersonenController personenController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{personenController}", PersonenController.class);
			bmwEventController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEventController}", BmwEventController.class);
			bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

			FacesContext facesContext = FacesContext.getCurrentInstance();
			this.eventID = (Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));

			current = bmwEventController.getFacade().find(eventID);
			personenController.init();
			//Hier muss noch was eingebaut werden wenn keine user existieren? Sonst crasht das programm

			//Personen p = personenController.
			//System.out.println(p.getNachname());

			//BmwParticipants participants= bmwParticipantsController.getFacade().find(this);
		/*
			 EntityManager em= ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();
			 List participants = em.createNamedQuery("BmwParticipants.findByEventId")
			 .setParameter("id", eventID)
			 .getResultList();
			 System.out.println("hello im here"+participants);
			 */
			Collection<BmwParticipants> p = current.getBmwParticipantsCollection();
			System.out.println("Size:" + p.size());
			
			selected = new BmwUser[p.size()];
			System.out.println("test");
			Iterator<BmwParticipants> it = p.iterator();
			int ii=0;
			
			while (it.hasNext()) {
				selected[ii]=it.next().getUserId();
			
				//u.add((BmwUser) it.next().getUserId());
				//System.out.println("Name"+it.next().getUserId().getPersonenID().getNameVollstaendig());
				ii++;
			}
			//u.toArray();

			//System.out.println("that" + selected.toString());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public DualListModel<String> getCities() {
		return cities;
	}

	public void setCities(DualListModel<String> cities) {
		this.cities = cities;

		System.out.println("set called" + cities);
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

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public void onTransfer(TransferEvent event) {
		System.out.println("transfer called");
		StringBuilder builder = new StringBuilder();
		/*for(Object item : event.getItems()) {
		 builder.append(((Player) item).getName()).append("<br />");
		 }
		 */

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public BmwEvent getCurrent() {
		return current;
	}

	public void setCurrent(BmwEvent current) {
		this.current = current;
		//bmwEventController.setSelected(current);
		//bmwEventController.save(null);
		System.out.println("yeah");
	}

	public void saveCurrent() {

		bmwEventController.setSelected(current);
		bmwEventController.save(null);
		
		//bmwUserController.setSelected();
		System.out.println("yeah");

	}

	public List<BmwUser> getU() {
		return u;
	}

	public void setU(List<BmwUser> u) {
		this.u = u;
	}
}
