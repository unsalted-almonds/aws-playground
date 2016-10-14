package com.shilin.aws.awsPlayrgound.lambda;

import com.amazonaws.services.lambda.runtime.Context; 

import org.apache.log4j.Logger;

public class HelloLog2 {
    // Initialize the Log4j logger.
    static final Logger log = Logger.getLogger(HelloLog2.class);

    /*
     * To test your Lambda function use a string ("this is a test") as sample event. The handler code receives the sample event but does nothing with it. It only shows how to write logs.
     * */
    public String myHandler(String name, Context context) {
    	
    	log.debug("log system var init value" + System.getProperty("tier"));
    	
    	System.setProperty("tier", name);
    	
        // System.out: One log statement but with a line break (AWS Lambda writes two events to CloudWatch). 
        System.out.println("log data from stdout \n this is continuation of system.out");
        
       // System.err: One log statement but with a line break (AWS Lambda writes two events to CloudWatch).
        System.err.println("log data from stderr. \n this is a continuation of system.err");

        // Use log4j to log the same thing as above and AWS Lambda will log only one event in CloudWatch.
        log.debug("log data from log4j debug \n this is continuation of log4j debug");

        log.error("log data from log4j err. \n this is a continuation of log4j.err");
        
        // Return will include the log stream name so you can look 
        // up the log later.
        return String.format("Hello %s. log stream = %s and the System VAR is %s", name, "context.getLogStreamName()", System.getProperty("tier"));
    }
    
    public static void main(String args[]){
    	
    	System.out.println(new HelloLog2().myHandler("mytest", null));
    	
    }
}