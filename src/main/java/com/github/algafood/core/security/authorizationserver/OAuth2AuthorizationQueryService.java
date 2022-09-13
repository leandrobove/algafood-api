package com.github.algafood.core.security.authorizationserver;

import java.util.List;

import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface OAuth2AuthorizationQueryService {

	List<RegisteredClient> findRegisteredClientsWithConsent(String principalName);
	List<OAuth2Authorization> findAuthorizations(String principalName, String clientId);

}
