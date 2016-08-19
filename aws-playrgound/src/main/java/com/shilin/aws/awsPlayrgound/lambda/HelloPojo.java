package com.shilin.aws.awsPlayrgound.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context; 

public class HelloPojo implements RequestHandler<HelloPojoRequest, HelloPojoResponse> {

    public HelloPojoResponse handleRequest(HelloPojoRequest request, Context context) {
        String greetingString = String.format("Hello %s %s.", request.firstName, request.lastName);
        return new HelloPojoResponse(greetingString);
    }

}