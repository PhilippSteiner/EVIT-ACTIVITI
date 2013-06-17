package ac.at.fhkufstein.EventFunc;

import ac.at.fhkufstein.entity.EmailTemplates;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "emailTemplatesController")
@ViewScoped
public class EmailTemplatesController extends AbstractController<EmailTemplates> implements Serializable {

    @EJB
    private EmailTemplatesFacade ejbFacade;

    public EmailTemplatesController() {
        super(EmailTemplates.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
