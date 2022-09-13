package com.github.algafood.core.security.authorizationserver;

import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public class JdbcOAuth2AuthorizationQueryService implements OAuth2AuthorizationQueryService {

	private final JdbcOperations jdbcOperations;
	private final RowMapper<RegisteredClient> registeredClientRowMapper;
	private final RowMapper<OAuth2Authorization> oauth2AuthorizationRowMapper;

	public JdbcOAuth2AuthorizationQueryService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
		this.jdbcOperations = jdbcOperations;
		this.registeredClientRowMapper = new JdbcRegisteredClientRepository.RegisteredClientRowMapper();
		this.oauth2AuthorizationRowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
	}

	@Override
	public List<RegisteredClient> findRegisteredClientsWithConsent(String principalName) {
		
		String query = "select registered_client.* from oauth2_authorization_consent consent "
				+ "inner join oauth2_registered_client registered_client "
				+ "on(registered_client.id = consent.registered_client_id) where consent.principal_name = ?";
		
		return this.jdbcOperations.query(query, registeredClientRowMapper, principalName);
	}

	@Override
	public List<OAuth2Authorization> findAuthorizations(String principalName, String clientId) {
		String query = "select auth.* from oauth2_authorization auth	"
				+ "inner join oauth2_registered_client c "
				+ "on(c.id = auth.registered_client_id) "
				+ "where auth.principal_name = ? and auth.registered_client_id = ?";
		
		return this.jdbcOperations.query(query, oauth2AuthorizationRowMapper, principalName, clientId);
	}

}
