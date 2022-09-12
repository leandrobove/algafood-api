package com.github.algafood.core.security.authorizationserver;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizationConsentController {

	@Autowired
	private RegisteredClientRepository registeredClientRepository;

	@Autowired
	private OAuth2AuthorizationConsentService auth2AuthorizationConsentService;

	@GetMapping("/oauth2/consent")
	public String concent(Principal principal, Model model,
			@RequestParam(required = true, name = OAuth2ParameterNames.CLIENT_ID) String clientId,
			@RequestParam(required = true, name = OAuth2ParameterNames.SCOPE) String scope,
			@RequestParam(required = true, name = OAuth2ParameterNames.STATE) String state) {

		RegisteredClient client = registeredClientRepository.findByClientId(clientId);

		if (client == null) {
			throw new AccessDeniedException(String.format("Cliente de id %s não foi encontrado", clientId));
		}

		OAuth2AuthorizationConsent consent = auth2AuthorizationConsentService.findById(client.getId(),
				principal.getName());

		String[] scopeArray = StringUtils.delimitedListToStringArray(scope, " ");
		Set<String> scopesParaAprovar = new HashSet<String>(Set.of(scopeArray));

		Set<String> scopesAprovadosAnteriormente;
		if (consent != null) {
			scopesAprovadosAnteriormente = consent.getScopes();
			scopesParaAprovar.removeAll(scopesAprovadosAnteriormente);
		} else {
			scopesAprovadosAnteriormente = Collections.emptySet();
		}

		model.addAttribute("clientId", clientId);
		model.addAttribute("state", state);
		model.addAttribute("principalName", principal.getName());
		model.addAttribute("scopesParaAprovar", scopesParaAprovar);
		model.addAttribute("scopesAprovadosAnteriormente", scopesAprovadosAnteriormente);

		return "pages/approval";
	}
}
