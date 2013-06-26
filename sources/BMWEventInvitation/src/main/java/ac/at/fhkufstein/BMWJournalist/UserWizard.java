package ac.at.fhkufstein.BMWJournalist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@SessionScoped
public class UserWizard {

    public String onFlowProcess(FlowEvent event) {

        return "confirm";

        //return event.getNewStep();

    }
}