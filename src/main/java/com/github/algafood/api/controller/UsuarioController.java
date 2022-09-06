package com.github.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.github.algafood.api.assembler.UsuarioModelAssembler;
import com.github.algafood.api.assembler.input.UsuarioInputDisassembler;
import com.github.algafood.api.dto.UsuarioModel;
import com.github.algafood.api.dto.input.SenhaInput;
import com.github.algafood.api.dto.input.UsuarioComSenhaInput;
import com.github.algafood.api.dto.input.UsuarioSemSenhaInput;
import com.github.algafood.core.security.CheckSecurity;
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
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	UsuarioInputDisassembler usuarioInputDisassembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<UsuarioModel> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		return usuarioModelAssembler.toCollectionModel(usuarios);
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping(value = "/{usuarioId}")
	public UsuarioModel buscarPorId(@PathVariable Long usuarioId) {
		return usuarioModelAssembler.toModel(usuarioService.buscarOuFalhar(usuarioId));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UsuarioModel cadastrar(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {

		Usuario usuario = usuarioInputDisassembler.toUsuario(usuarioComSenhaInput);

		return usuarioModelAssembler.toModel(usuarioService.salvar(usuario));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
	@PutMapping(value = "/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {

		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);

		usuarioInputDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuarioAtual);

		return usuarioModelAssembler.toModel(usuarioService.salvar(usuarioAtual));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping(value = "/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long usuarioId) {
		usuarioService.deletar(usuarioId);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@PutMapping(value = "/{usuarioId}/senha")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
		usuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}

}
