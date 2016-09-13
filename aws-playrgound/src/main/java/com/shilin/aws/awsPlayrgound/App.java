package com.shilin.aws.awsPlayrgound;

import java.io.IOException;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException{
    	    	
    	String twitterKey = "20nNFS1zfFtYetSSLCFcBrKW9";
    	String twitterSecret = "xJxx52TGm99LU016kylI05FkPJOQJ5Ck7e3O6PQwrmNFgpiEWJ";   	
		String contentType = "application/json";

		
		final OAuth10aService service = new ServiceBuilder().apiKey(twitterKey).apiSecret(twitterSecret)
				.build(TwitterApi.instance());
		
		
		TwitterOAuth20Api twitterApi = TwitterOAuth20Api.instance();
		final OAuth20Service oauth20Service = new ServiceBuilder().apiKey(twitterKey).apiSecret(twitterSecret)
        .build(twitterApi);
		
		OAuth2AccessToken acessToken = twitterApi.getAccessToken(oauth20Service);
		
		System.out.println(acessToken.getAccessToken());
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/users/show.json?screen_name=twitterdev", oauth20Service);
		request.addHeader("Authorization", "Bearer " + acessToken.getAccessToken());
		
		if (contentType != null && !contentType.trim().isEmpty()) {
			request.addHeader("Content-Type", contentType);
		}
		
		request.addPayload("");
		oauth20Service.signRequest(acessToken, request);
		
		Response response = request.send();
		
		System.out.println(response.getBody());
		
    }
}
