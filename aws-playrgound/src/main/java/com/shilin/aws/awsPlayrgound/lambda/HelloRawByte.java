package com.shilin.aws.awsPlayrgound.lambda;

import java.io.InputStream;
import java.io.OutputStream;
import com.amazonaws.services.lambda.runtime.Context; 

public class HelloRawByte {
    public static void myHandler(InputStream inputStream, OutputStream outputStream, Context context) {
        
        int letter;
        try {       
            while((letter = inputStream.read()) != -1)
            {
                outputStream.write(Character.toUpperCase(letter));
            }
            Thread.sleep(3000); // Intentional delay for testing the getRemainingTimeInMillis() result.
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       
        // For fun, let us get function info using the context object.
        System.out.println("Function name: " + context.getFunctionName());
        System.out.println("Max mem allocated: " + context.getMemoryLimitInMB());
        System.out.println("Time remaining in milliseconds: " + context.getRemainingTimeInMillis());
        System.out.println("CloudWatch log stream name: " + context.getLogStreamName());
        System.out.println("CloudWatch log group name: " + context.getLogGroupName());
    }
}
