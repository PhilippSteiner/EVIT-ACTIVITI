/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.EventFunc;

import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.Personen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
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
		
	
		try{
		
			bmwUserController=FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
		 PersonenController personenController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{personenController}", PersonenController.class);
        personenController.init();
		//Hier muss noch was eingebaut werden wenn keine user existieren? Sonst crasht das programm
		
		//Personen p = personenController.
		//System.out.println(p.getNachname());

		

		//Cities
		List<String> citiesSource = new ArrayList<String>();
		List<String> citiesTarget = new ArrayList<String>();
		
		
		//Personen person = personenController.getFacade().find(sp.getPersonalID());
		
		int i=0;
        while(i<bmwUserController.getItems().size()){
			
			Personen sp = bmwUserController.getItems().get(i).getPersonenID();
			Personen person = personenController.getFacade().find(sp.getPersonalID());
			citiesSource.add(person.getNameVollstaendig());
			i++;
		}
		
		cities = new DualListModel<String>(citiesSource, citiesTarget);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public DualListModel<String> getCities() {
		return cities;	
}
	public void setCities(DualListModel<String> cities) {
		this.cities = cities;
		
		System.out.println("set called"+cities); 
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
}

