package com.shilin.aws.awsPlayrgound.other;

import javax.inject.Inject;

public class DocumentStorage {
	
	final DIExampleDocumentSaver documentSaver;
	
	@Inject
	public DocumentStorage(final DIExampleDocumentSaver saver){
		documentSaver = saver;
	}
	
	public void save(){
		documentSaver.save();
	}
	

}
