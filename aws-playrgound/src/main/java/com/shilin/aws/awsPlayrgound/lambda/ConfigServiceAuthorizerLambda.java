package com.shilin.aws.awsPlayrgound.lambda;

import static com.shilin.aws.awsPlayrgound.lambda.AuthPolicy.HttpMethod;
import java.io.IOException;
import com.amazonaws.services.lambda.runtime.Context;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Authorize configuration service request using JWT token
 *
 * @author Shilin_Gan
 */
public class ConfigServiceAuthorizerLambda {

    final static String qaUrl = "https://garqa.gallup.com/gar/rest/data/aol/learnmodulessearch";
    final static String prodUrl = "https://garqa.gallup.com/gar/rest/data/aol/learnmodulessearch";
    final static String principalId = "GAR Configuration Service";

    public static void main(String args[]) throws IOException {

        String methodArn = "arn:aws:execute-api:us-west-2:025840044765:g8nt6ujup9/*/POST/mydemoresource";
        String token = "f6576049-e269-452a-8469-0b54827531fb-proxy";

        System.out.println(new ConfigServiceAuthorizerLambda()
                .handleRequestQA(new TokenAuthorizerContext("unknown type", token, methodArn), null).getPolicyDocument());
    }

    public AuthPolicy handleRequestQA(TokenAuthorizerContext input, Context context)
            throws IOException {
        return sendRequest(input, context, qaUrl);
    }

    public AuthPolicy handleRequestPROD(TokenAuthorizerContext input, Context context)
            throws IOException {
        return sendRequest(input, context, prodUrl);
    }

    private AuthPolicy sendRequest(TokenAuthorizerContext input, Context context, String endpoint)
            throws IOException {

        // parse API Gateway request parameters
        String token = input.getAuthorizationToken();
        String methodArn = input.getMethodArn();
        String[] arnPartials = methodArn.split(":");
        String region = arnPartials[3];
        String awsAccountId = arnPartials[4];
        String[] apiGatewayArnPartials = arnPartials[5].split("/");
        String restApiId = apiGatewayArnPartials[0];
        String stage = apiGatewayArnPartials[1];
        String httpMethod = apiGatewayArnPartials[2];
        String resource = ""; // root resource

        if (apiGatewayArnPartials.length == 4)
            resource = apiGatewayArnPartials[3];

        // construct http request for token validation
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(endpoint);

        postRequest.setHeader("Content-Type", "application/json");
        postRequest.setHeader("X-Client-Id", "1");
        postRequest.setHeader("X-Requested-With", "XMLHttpRequest");
        postRequest.setHeader("X-Session-Id", token);
        postRequest.setHeader("X-Widget-Code-Name", "AOL");

        postRequest.setEntity(new StringEntity(
                "{\"action\":{\"eType\":\"DATA_DIM\",\"eCode\":\"LIST_MODULES\",\"eData\":\"MODULE_STATUS:AVAILABLE\"},\"dimData\":{\"pageNum\":1,\"pageCount\":6}}"));

        return (client.execute(postRequest).getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                ? new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowOnePolicy(region, awsAccountId, restApiId, stage, HttpMethod.valueOf(httpMethod), resource))
                : new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getDenyOnePolicy(region, awsAccountId, restApiId, stage, HttpMethod.valueOf(httpMethod), resource));

    }
}
