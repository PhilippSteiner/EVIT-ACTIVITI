/**
 * @author
 * wolfgang
 * Teves
 * @version
 * 0.6
 */
package ac.at.fhkufstein.EventFunc;

import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwFlightController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.session.BmwFlightFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

@ManagedBean(name = "Flight")
@ViewScoped
public class Flight implements Serializable{

	/*
	 * Provides Flights for one Event
	 * 
	 */
	private BmwFlightController bmwFlightController;
	private BmwEventController bmwEventController;
	private BmwEvent cev;
	private List p; 
	private Integer eventID;
    private String flightNumber;
    private String departureLocation;
	private String arrivalLocation;
    private Date departureTime;
    private Date arrivalTime;

	public Flight() {
		
		//Get Instances
		bmwFlightController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwFlightController}", BmwFlightController.class);
		bmwEventController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEventController}", BmwEventController.class);
		
		
		//Get Current Event ID from URL "eventID"
		FacesContext facesContext = FacesContext.getCurrentInstance();
		this.eventID = (Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));
		cev = bmwEventController.getFacade().find(eventID);

		try {

			EntityManager em = ((BmwFlightFacade) bmwFlightController.getFacade()).getEntityManager();
			p = em.createNamedQuery("BmwFlight.findByEventId")
					.setParameter("eventId", cev)
					.getResultList();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void saveNew(){
		
				BmwFlight fl = bmwFlightController.prepareCreate(null);//Save new Flight
				fl.setEventId(cev);//Correct Event ID
				fl.setFlightNumber(flightNumber);
				fl.setDepartureLocation(departureLocation);
				fl.setArrivalLocation(arrivalLocation);
				fl.setDepartureTime(departureTime);
				fl.setArrivalTime(arrivalTime);
				bmwFlightController.setSelected(fl);
				bmwFlightController.saveNew(null);//save to database
				
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Flug hinzugefügt", "Aktualisieren sie die Seite um die Änderungen zu sehen")); //Send message 
				
	}
	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public List getP() {
		return p;
	}

	public void setP(List p) {
		this.p = p;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}
}
