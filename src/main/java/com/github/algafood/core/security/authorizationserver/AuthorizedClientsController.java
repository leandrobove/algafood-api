package com.github.algafood.core.security.authorizationserver;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizedClientsController {

	@Autowired
	private OAuth2AuthorizationQueryService oauth2AuthorizationQueryService;

	@Autowired
	private RegisteredClientRepository registeredClientRepository;

	@Autowired
	private OAuth2AuthorizationConsentService oauth2AuthorizationConsentService;
	
	@Autowired
	private OAuth2AuthorizationService authorizationService;

	@GetMapping("/oauth2/authorized-clients")
	public String listAuthorizedClients(Principal principal, Model model) {
		List<RegisteredClient> clients = oauth2AuthorizationQueryService
				.findRegisteredClientsWithConsent(principal.getName());

		model.addAttribute("clients", clients);

		return "pages/authorized-clients";
	}

	@PostMapping("/oauth2/authorized-clients/revoke")
	public String revokeAuthorizedClient(Principal principal, Model model,
			@RequestParam(required = true, name = OAuth2ParameterNames.CLIENT_ID) String clientId) {

		RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);

		if (registeredClient == null) {
			throw new AccessDeniedException(String.format("Cliente %s não encontrado.", clientId));
		}

		OAuth2AuthorizationConsent consent = oauth2AuthorizationConsentService.findById(registeredClient.getId(),
				principal.getName());

		List<OAuth2Authorization> authorizations = oauth2AuthorizationQueryService
				.findAuthorizations(principal.getName(), registeredClient.getId());

		if (consent != null) {
			this.oauth2AuthorizationConsentService.remove(consent);
		}
		
		for (OAuth2Authorization authorization : authorizations) {
			this.authorizationService.remove(authorization);
		}
		
		return "redirect:/oauth2/authorized-clients";
	}

}
