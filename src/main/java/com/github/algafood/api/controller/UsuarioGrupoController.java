package com.github.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.assembler.GrupoModelAssembler;
import com.github.algafood.api.dto.GrupoModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.model.Usuario;
import com.github.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private CadastroUsuarioService usuarioService;

	@Autowired
	private GrupoModelAssembler grupoAssembler;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

		CollectionModel<GrupoModel> gruposModel = grupoAssembler.toCollectionModel(usuario.getGrupos()).removeLinks();
		if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
			//associar
			gruposModel.add(algaLinksHelper.linkToUsuarioGrupoAssociacao(usuario.getId(), "associar"));
			
			//desassociar
			gruposModel.forEach((grupoModel) -> {
				grupoModel.add(
						algaLinksHelper.linkToUsuarioGrupoDessociacao(usuarioId, grupoModel.getId(), "desassociar"));
			});
		}

		return gruposModel;
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping(value = "/{grupoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.associarGrupo(usuarioId, grupoId);

		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping(value = "/{grupoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.desassociarGrupo(usuarioId, grupoId);

		return ResponseEntity.noContent().build();
	}
}
