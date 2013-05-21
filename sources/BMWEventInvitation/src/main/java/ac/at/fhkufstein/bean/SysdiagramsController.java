package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.Sysdiagrams;
import ac.at.fhkufstein.session.SysdiagramsFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "sysdiagramsController")
@ViewScoped
public class SysdiagramsController extends AbstractController<Sysdiagrams> implements Serializable {

    @EJB
    private SysdiagramsFacade ejbFacade;

    public SysdiagramsController() {
        super(Sysdiagrams.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
