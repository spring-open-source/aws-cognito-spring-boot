package com.hardik.bheem.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Jacksonized
public class TokenRequest {

	private final String code;

}
