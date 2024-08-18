package com.openclassrooms.medilabo_report.configuration;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class BearerTokenRelayInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof JwtAuthenticationToken) {
			String tokenValue = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
			request.getHeaders().setBearerAuth(tokenValue);
		}

		return execution.execute(request, body);
	}
}
