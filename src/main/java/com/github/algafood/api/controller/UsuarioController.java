package com.github.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.UsuarioAssembler;
import com.github.algafood.api.assembler.input.UsuarioInputDisassembler;
import com.github.algafood.api.dto.UsuarioDTO;
import com.github.algafood.api.dto.input.SenhaInput;
import com.github.algafood.api.dto.input.UsuarioComSenhaInput;
import com.github.algafood.api.dto.input.UsuarioSemSenhaInput;
import com.github.algafood.domain.model.Usuario;
import com.github.algafood.domain.repository.UsuarioRepository;
import com.github.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService usuarioService;

	@Autowired
	private UsuarioAssembler usuarioAssembler;

	@Autowired
	UsuarioInputDisassembler usuarioInputDisassembler;

	@GetMapping
	public List<UsuarioDTO> listar() {
		return usuarioAssembler.toListDTO(usuarioRepository.findAll());
	}

	@GetMapping(value = "/{usuarioId}")
	public UsuarioDTO buscarPorId(@PathVariable Long usuarioId) {
		return usuarioAssembler.toDTO(usuarioService.buscarOuFalhar(usuarioId));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UsuarioDTO cadastrar(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {

		Usuario usuario = usuarioInputDisassembler.toUsuario(usuarioComSenhaInput);

		return usuarioAssembler.toDTO(usuarioService.salvar(usuario));
	}

	@PutMapping(value = "/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {

		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);

		usuarioInputDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuarioAtual);

		return usuarioAssembler.toDTO(usuarioService.salvar(usuarioAtual));
	}

	@DeleteMapping(value = "/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long usuarioId) {
		usuarioService.deletar(usuarioId);
	}
	
	@PutMapping(value = "/{usuarioId}/senha")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
		usuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}

}
