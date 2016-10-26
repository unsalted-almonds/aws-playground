package com.shilin.aws.awsPlayrgound.other;

import org.jvnet.hk2.annotations.Service;

@Service
public class DIExampleSLDocumentSaver implements DIExampleDocumentSaver{

	@Override
	public void save() {
		System.out.println("Using SL Saver");
		
	}

}
