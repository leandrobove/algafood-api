package com.github.algafood.api.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import springfox.documentation.annotations.ApiIgnore;

@Hidden
@ApiIgnore
@RestController
public class HostCheckController {

	@GetMapping("/hostcheck")
	public String checkHost() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress() +
				" - " + InetAddress.getLocalHost().getHostName();
	}
}
