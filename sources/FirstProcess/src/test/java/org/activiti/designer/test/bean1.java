package org.activiti.designer.test;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class bean1 implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		
		for(int i=0; i<10; i++) {
			System.out.println("Bean1 - Durchgang "+i);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
