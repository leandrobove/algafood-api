package com.github.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MeuPrimeiroController {
	
	@GetMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		return "Olá Mundo!";
	}

}
