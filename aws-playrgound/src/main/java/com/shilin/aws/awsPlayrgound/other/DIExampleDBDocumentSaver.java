package com.shilin.aws.awsPlayrgound.other;

import org.jvnet.hk2.annotations.Service;

@Service
public class DIExampleDBDocumentSaver implements DIExampleDocumentSaver {

	@Override
	public void save() {
		System.out.println("Using DB Saver");

	}

}
