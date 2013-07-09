/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.admin;

import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.Personen;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwUserFacade;
import ac.at.fhkufstein.session.PersonenFacade;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Philipp
 */
@ManagedBean(name = "reisebuero")
@ViewScoped
public class Reisebuero implements Serializable {

    private BmwUserController bmwUserController;
    private PersonenController personenController;
    private List<BmwUser> roleUser;
    private BmwUser selected;
    private Personen person;
    private Boolean loginSent = false;
    private String username;
    private Integer role;
    private String frequentflyer;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String companyName;
    private String postalCode;
    private String city;
    private String street;
    private Integer uid;
    private String pwd;
    private Integer rating;
    private String seatingPriority;
    private Personen personenID;

    /**
     * Creates a new instance of Reisebuero
     */
    public Reisebuero() {

        bmwUserController = PersistenceService.getManagedBeanInstance(BmwUserController.class);
        personenController = PersistenceService.getManagedBeanInstance(PersonenController.class);
        EntityManager emp = ((PersonenFacade) personenController.getFacade()).getEntityManager();
        person = personenController.getFacade().find("9ff20e6c-dec0-4548-82db-005825Aaf1c8");

        update();

    }
    
    public void update(){
    
        EntityManager emu = ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
        roleUser = emu.createNamedQuery("BmwUser.findByRole")
                .setParameter("role", 3)
                .getResultList();
    }

    public void prepareCreate() {
        selected = bmwUserController.prepareCreate(null);
    }

    public void saveNew() {


        selected = bmwUserController.prepareCreate(null);
        selected.setPersonenID(person);
        selected.setRole(3);
        selected.setUsername(username);
        selected.setCity(city);
        selected.setCompanyName(companyName);
        selected.setEmail(email);
        selected.setFirstName(firstName);
        selected.setLastName(lastName);
        selected.setLoginSent(loginSent);
        selected.setPhone(phone);
        selected.setPostalCode(postalCode);
        selected.setPwd(ResetPassword.getRandomPassword());


        bmwUserController.setSelected(selected);
        bmwUserController.saveNew(null);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Reisebüro hinzugefügt", "Aktualisieren sie die Seite um die Änderungen zu sehen")); //Send message 
        update();
    }

    public void save() {

        bmwUserController.setSelected(selected);
        bmwUserController.saveNew(null);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Reisebüro geändert", "Erfolgreich geändert!")); //Send message 

    }
    
    public void delete(){
        bmwUserController.setSelected(selected);
        System.out.println(selected);
        bmwUserController.delete(null);
        update();
    }

    public Boolean getLoginSent() {
        return loginSent;
    }

    public void setLoginSent(Boolean loginSent) {
        this.loginSent = loginSent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getFrequentflyer() {
        return frequentflyer;
    }

    public void setFrequentflyer(String frequentflyer) {
        this.frequentflyer = frequentflyer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getSeatingPriority() {
        return seatingPriority;
    }

    public void setSeatingPriority(String seatingPriority) {
        this.seatingPriority = seatingPriority;
    }

    public Personen getPersonenID() {
        return personenID;
    }

    public void setPersonenID(Personen personenID) {
        this.personenID = personenID;
    }

    public BmwUser getSelected() {
        return selected;
    }

    public void setSelected(BmwUser selected) {
        this.selected = selected;
    }

    public List<BmwUser> getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(List<BmwUser> roleUser) {
        this.roleUser = roleUser;
    }
}
