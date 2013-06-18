/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.dataimport;

import ac.at.fhkufstein.bean.AbstractController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.Personen;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

/**
 *
 * @author Philipp
 */
@ManagedBean(name = "compareList")
@RequestScoped
public class compareList {

    private BmwUserController bmwUserController;
    private PersonenController personenController;
    private List<Personen> newList = null;

    /**
     * Creates a new instance of compareList
     */
    public compareList() {

        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
        personenController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{personenController}", PersonenController.class);



    }

    public List<BmwUser> getUsers() {
        return bmwUserController.getItems();
    }

    public List<Personen> getPersonen() {

        return personenController.getItems();
    }

    public List<Personen> getSubPersonen() {

        newList = personenController.getItems();

        if (newList != null) {

            for (BmwUser u : bmwUserController.getItems()) {
                String p = u.getPersonenID().getPersonalID();

                for (Personen pl : personenController.getItems()) {
                    if (p.equals(pl.getPersonalID())) {
                        newList.remove(pl);
                        break;
                    }
                }
            }
        }
        return newList;
    }

    public void saveUsers() {


        for (Personen up : newList) {
            BmwUser bmwUser = new BmwUser();
            bmwUser.setPersonenID(up);
            bmwUser.setPwd(getRandomPassword());
            bmwUserController.prepareCreate(null);
            bmwUserController.setSelected(bmwUser);
            bmwUserController.saveNew(null);
        }


    }

    public String getRandomPassword() {
        StringBuilder password = new StringBuilder(8);
        //int next = RandomUtils.nextInt(13) + 8;
        password.append(RandomStringUtils.randomAlphanumeric(8));
        return password.toString();
    }
}
