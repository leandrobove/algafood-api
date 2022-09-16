package com.github.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.assembler.PermissaoModelAssembler;
import com.github.algafood.api.dto.PermissaoModel;
import com.github.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.model.Grupo;
import com.github.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private CadastroGrupoService grupoService;

	@Autowired
	private PermissaoModelAssembler permissaoAssembler;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	@Override
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);

		CollectionModel<PermissaoModel> permissoesModel = permissaoAssembler.toCollectionModel(grupo.getPermissoes())
				.removeLinks();
		
		permissoesModel.add(algaLinksHelper.linkToGrupoPermissoes(grupoId));
		
		if(algaSecurity.podeEditarUsuariosGruposPermissoes()) {
			//associar
			permissoesModel.add(algaLinksHelper.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
	
			//desassociar
			permissoesModel.getContent().forEach((permissaoModel) -> {
				permissaoModel.add(algaLinksHelper.linkToGrupoPermissaoDesassociacao(grupoId, permissaoModel.getId(), "desassociar"));
			});
		}
		
		return permissoesModel;
	}

	@Override
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping(value = "/{permissaoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desassociarPermissao(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}

	@Override
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping(value = "/{permissaoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.associarPermissao(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}

}
