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

/*private User user = new User();
private boolean skip;
private List<User> userAll = new ArrayList<User>();*


private static Logger logger = Logger.getLogger(UserWizard.class.getName());
/*public UserWizard() {
    userAll = new ArrayList<User>();
}*/
/*public List<User> getUserAll() {
    return userAll;
}

public void setUserAll(List<User> userAll) {
    this.userAll = userAll;
}
public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}
*/

/*
    public void setStep(String step) {
        this.step = step;
    }*/

/*public void save(ActionEvent actionEvent) {
    //Persist user
    System.out.println("First name : " + user.getFirstname());
    System.out.println("Last name : " + user.getLastname());
    System.out.println("Age name : " + user.getAge());
    userAll.add(user);
    user = new User();
    FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getFirstname());
    FacesContext.getCurrentInstance().addMessage(null, msg);

}

public boolean isSkip() {
    return skip;
}

public void setSkip(boolean skip) {
    this.skip = skip;
}*/

public String onFlowProcess(FlowEvent event) {
    
        return "confirm";

        //return event.getNewStep();

}

}