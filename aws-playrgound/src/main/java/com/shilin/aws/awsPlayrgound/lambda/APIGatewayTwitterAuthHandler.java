package com.shilin.aws.awsPlayrgound.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.shilin.others.example.TwitterOAuth20Api;

/**
 * Authorize user by using Twitter token 
 * @author Shilin_Gan
 *
 */
public class APIGatewayTwitterAuthHandler implements RequestHandler<TokenAuthorizerContext, AuthPolicy> {

	@Override
	public AuthPolicy handleRequest(TokenAuthorizerContext input, Context context) {

		String token = input.getAuthorizationToken();
		String principalId = "GAR Video Streaming";

		String methodArn = input.getMethodArn();
		String[] arnPartials = methodArn.split(":");
		String region = arnPartials[3];
		String awsAccountId = arnPartials[4];
		String[] apiGatewayArnPartials = arnPartials[5].split("/");
		String restApiId = apiGatewayArnPartials[0];
		String stage = apiGatewayArnPartials[1];
		String httpMethod = apiGatewayArnPartials[2];
		String resource = ""; // root resource

		if (apiGatewayArnPartials.length == 4) {
			resource = apiGatewayArnPartials[3];
		}

		AuthPolicy res = null;
		String twitterKey = "20nNFS1zfFtYetSSLCFcBrKW9";
		String twitterSecret = "xJxx52TGm99LU016kylI05FkPJOQJ5Ck7e3O6PQwrmNFgpiEWJ";
		String contentType = "application/json";

		TwitterOAuth20Api twitterApi = TwitterOAuth20Api.instance();
		final OAuth20Service oauth20Service = new ServiceBuilder().apiKey(twitterKey).apiSecret(twitterSecret)
				.build(twitterApi);

		//OAuth2AccessToken acessToken;
		try {
//			acessToken = twitterApi.getAccessToken(oauth20Service);
//			System.out.println(acessToken.getAccessToken());
			OAuthRequest request = new OAuthRequest(Verb.GET,
					"https://api.twitter.com/1.1/users/show.json?screen_name=twitterdev", oauth20Service);
			request.addHeader("Authorization", "Bearer " + token);

			if (contentType != null && !contentType.trim().isEmpty()) {
				request.addHeader("Content-Type", contentType);
			}

			request.addPayload("");
			oauth20Service.signRequest(new OAuth2AccessToken(token), request);

			Response response = request.send();

			if (response.getCode() != 200)
				res = new AuthPolicy(principalId,
						AuthPolicy.PolicyDocument.getDenyAllPolicy(region, awsAccountId, restApiId, stage));
			else
				res = new AuthPolicy(principalId,
						AuthPolicy.PolicyDocument.getAllowAllPolicy(region, awsAccountId, restApiId, stage));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

}
