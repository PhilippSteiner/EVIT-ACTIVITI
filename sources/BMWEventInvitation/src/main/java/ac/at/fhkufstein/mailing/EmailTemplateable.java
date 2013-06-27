/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

/**
 *
 * @author mike
 */
public interface EmailTemplateable {

    public Integer getId();
    public String getSubject();
    public String getType();
    public String getEmailContent();
}
