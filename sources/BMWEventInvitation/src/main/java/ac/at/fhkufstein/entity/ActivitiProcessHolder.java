/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.entity;

/**
 *
 * @author mike
 */
public interface ActivitiProcessHolder {

    public Integer getId();

    public void setId(Integer id);

    public Integer getProcessId();

    public void setProcessId(Integer processId);
    
    public void setProgress(Integer progress);
    
    public Integer getProgress();

}
