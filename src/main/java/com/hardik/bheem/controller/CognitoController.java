package com.hardik.bheem.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.bheem.cognito.TokenService;
import com.hardik.bheem.dto.TokenResponse;
import com.hardik.bheem.request.RefreshTokenRequest;
import com.hardik.bheem.request.TokenRequest;
import com.hardik.bheem.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CognitoController {

	private final TokenService tokenService;

	private final UserService userService;

	@GetMapping("/v1/get-token/{code}")
	public TokenResponse fetchTokenHandler(@PathVariable(required = true, name = "code") final String code)
			throws UnsupportedEncodingException {
		var tokenResponse = tokenService.fetchToken(new TokenRequest(code));
		userService.handleSignIn(tokenResponse.getIdToken());
		return tokenResponse;
	}

	@PutMapping("/v1/refresh-token")
	public TokenResponse refreshTokenHandler(
			@RequestBody(required = true) final RefreshTokenRequest refreshTokenRequest)
			throws UnsupportedEncodingException {
		return tokenService.refreshToken(refreshTokenRequest);
	}

}
