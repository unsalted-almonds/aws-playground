package com.shilin.aws.awsPlayrgound.other;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class Test {
	
	public static void main(String args[]){
		ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
		
		DIExampleDocumentSaver saver = locator.getService(DIExampleDBDocumentSaver.class);
		
		saver.save();
	}

}
