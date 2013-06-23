/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.EventFunc;

import ac.at.fhkufstein.entity.BmwParticipants;
import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.primefaces.model.SelectableDataModel;  
/**
 *
 * @author
 * wolfgangteves
 */
public class ParticipantDataModel extends ListDataModel<BmwParticipants> implements SelectableDataModel<BmwParticipants>{
	
	public ParticipantDataModel(){
		
	}
	
	 public ParticipantDataModel(List<BmwParticipants> data) {  
        super(data);  
    }  
      
    @Override  
    public BmwParticipants getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<BmwParticipants> cars = (List<BmwParticipants>) getWrappedData();  
          System.out.println(cars.size()+"size of part");
        for(BmwParticipants car : cars) {  
			System.out.println("check if "+car.getId()+" equals "+rowKey);
            if(car.getId()==Integer.parseInt(rowKey))  {
				System.out.println("true");
			
                return car;  }
		
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(BmwParticipants car) {  
        return car.getId();  
    }  
	
}

