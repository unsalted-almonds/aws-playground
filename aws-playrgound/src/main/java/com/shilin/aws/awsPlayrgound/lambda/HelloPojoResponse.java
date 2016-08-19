package com.shilin.aws.awsPlayrgound.lambda;


public class HelloPojoResponse {
    String greetings;

    public String getGreetings() {
        return greetings;
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }

    public HelloPojoResponse(String greetings) {
        this.greetings = greetings;
    }

    public HelloPojoResponse() {
    }
}