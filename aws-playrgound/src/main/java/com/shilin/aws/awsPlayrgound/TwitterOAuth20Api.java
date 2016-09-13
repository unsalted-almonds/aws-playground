package com.shilin.aws.awsPlayrgound;

import java.io.IOException;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * Twitter OAuth 2.0 API this is only for getting the access token which is the
 * only OAuth 2.0 API Twitter is exposing at this point
 * 
 * @author Shilin_Gan
 *
 */
public class TwitterOAuth20Api extends DefaultApi20 {

	private static class InstanceHolder {
		private static final TwitterOAuth20Api INSTANCE = new TwitterOAuth20Api();
	}

	public static TwitterOAuth20Api instance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	public Verb getAccessTokenVerb() {
		return Verb.POST;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return getEnvURL();
	}

	@Override
	public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
		return OAuth2AccessTokenJsonExtractor.instance();
	}

	private String getEnvURL() {
		return "https://api.twitter.com/oauth2/token";

	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return getEnvURL();
	}

	/*
	 * Need to hard code "client_credentials" as grant type here
	 */
	public OAuth2AccessToken getAccessToken(OAuth20Service service) throws OAuthException, IOException {

		final OAuthConfig config = service.getConfig();
		final OAuthRequest request = new OAuthRequest(this.getAccessTokenVerb(), this.getAccessTokenEndpoint(),
				service);
		request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
		request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
		// request.addBodyParameter(OAuthConstants.CODE, "empty");
		// request.addParameter(OAuthConstants.REDIRECT_URI,
		// config.getCallback());
		final String scope = config.getScope();
		if (scope != null) {
			request.addParameter(OAuthConstants.SCOPE, scope);
		}
		request.addBodyParameter(OAuthConstants.GRANT_TYPE, "client_credentials");

		return this.getAccessTokenExtractor().extract(request.send());
	}
}
