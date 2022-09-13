package com.github.algafood.core.security.authorizationserver;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizedClientsController {

	@Autowired
	private OAuth2AuthorizationQueryService oauth2AuthorizationQueryService;

	@GetMapping("/oauth2/authorized-clients")
	public String listAuthorizedClients(Principal principal, Model model) {
		List<RegisteredClient> clients = oauth2AuthorizationQueryService
				.findRegisteredClientsWithConsent(principal.getName());

		model.addAttribute("clients", clients);

		return "pages/authorized-clients";
	}

}
