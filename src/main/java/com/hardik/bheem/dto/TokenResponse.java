package com.hardik.bheem.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenResponse implements Serializable {

	private static final long serialVersionUID = 2404372093396416188L;

	@JsonProperty("id_token")
	private String idToken;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private Long expiresIn;

}