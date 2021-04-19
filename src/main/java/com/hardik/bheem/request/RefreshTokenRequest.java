package com.hardik.bheem.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RefreshTokenRequest {

	private final String refreshToken;

}