package com.hardik.bheem.cognito;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardik.bheem.cognito.configuration.CognitoConfiguration;
import com.hardik.bheem.dto.TokenResponse;
import com.hardik.bheem.request.RefreshTokenRequest;
import com.hardik.bheem.request.TokenRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(CognitoConfiguration.class)
public class TokenService {

	private final CognitoConfiguration cognitoConfiguration;

	private final String contentType = "application/x-www-form-urlencoded";

	public TokenResponse fetchToken(TokenRequest tokenRequest) throws UnsupportedEncodingException {
		var configuration = cognitoConfiguration.getCognito();
		var client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
		var queryParamString = "?grant_type=authorization_code&code=%s&redirect_uri=%s"
				.formatted(tokenRequest.getCode(), configuration.getRedirectUri());
		var httpRequest = HttpRequest
				.newBuilder(URI.create(configuration.getDomain() + configuration.getUrl() + queryParamString))
				.header("Content-Type", contentType)
				.header("Authorization", "Basic " + Base64.getEncoder().encodeToString("%s:%s"
						.formatted(configuration.getClientId(), configuration.getClientSecret()).getBytes("utf-8")))
				.POST(BodyPublishers.noBody()).build();
		try {
			var response = client.send(httpRequest, BodyHandlers.ofString());
			return new ObjectMapper().readValue(response.body(), TokenResponse.class);
		} catch (final IOException e) {
			throw new RuntimeException("Unable to fetch token from code", e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Unable to fetch token from code", e);
		}
	}

	public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws UnsupportedEncodingException {
		var configuration = cognitoConfiguration.getCognito();
		var client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
		var queryParamString = "?grant_type=refresh_token&refresh_token=%s&redirect_uri=%s"
				.formatted(refreshTokenRequest.getRefreshToken(), configuration.getRedirectUri());
		var httpRequest = HttpRequest
				.newBuilder(URI.create(configuration.getDomain() + configuration.getUrl() + queryParamString))
				.header("Content-Type", contentType)
				.header("Authorization", "Basic " + Base64.getEncoder().encodeToString("%s:%s"
						.formatted(configuration.getClientId(), configuration.getClientSecret()).getBytes("utf-8")))
				.POST(BodyPublishers.noBody()).build();
		try {
			var response = client.send(httpRequest, BodyHandlers.ofString());
			return new ObjectMapper().readValue(response.body(), TokenResponse.class);
		} catch (final IOException e) {
			throw new RuntimeException("Unable to fetch token from code", e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Unable to fetch token from code", e);
		}
	}

}
