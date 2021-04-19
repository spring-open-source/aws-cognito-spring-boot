package com.hardik.bheem.cognito.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.bheem")
public class CognitoConfiguration {

	public Configuration cognito = new Configuration();

	@Data
	public class Configuration {
		public String url;
		public String domain;
		public String clientId;
		public String clientSecret;
		public String redirectUri;
	}

}
