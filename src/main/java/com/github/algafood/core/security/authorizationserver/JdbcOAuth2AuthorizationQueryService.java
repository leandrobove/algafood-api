package com.github.algafood.core.security.authorizationserver;

import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public class JdbcOAuth2AuthorizationQueryService implements OAuth2AuthorizationQueryService {

	private final JdbcOperations jdbcOperations;
	private final RowMapper<RegisteredClient> registeredClientRowMapper;

	public JdbcOAuth2AuthorizationQueryService(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
		this.registeredClientRowMapper = new JdbcRegisteredClientRepository.RegisteredClientRowMapper();
	}

	@Override
	public List<RegisteredClient> findRegisteredClientsWithConsent(String principalName) {
		
		String query = "select registered_client.* from oauth2_authorization_consent consent "
				+ "inner join oauth2_registered_client registered_client "
				+ "on(registered_client.id = consent.registered_client_id) where consent.principal_name = ?";
		
		return this.jdbcOperations.query(query, registeredClientRowMapper, principalName);
	}

}
