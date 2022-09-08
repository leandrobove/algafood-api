package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.FormaPagamentoController;
import com.github.algafood.api.dto.FormaPagamentoModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}

	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel = this.createModelWithId(formaPagamento.getId(), formaPagamento);
		modelMapper.map(formaPagamento, formaPagamentoModel);
		
		if(algaSecurity.podeConsultarFormasPagamento()) {
			formaPagamentoModel.add(algaLinksHelper.linkToFormasPagamento("formasPagamento"));
		}
		
		return formaPagamentoModel;
	}
	
	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		CollectionModel<FormaPagamentoModel> formasPagamentoModel = super.toCollectionModel(entities);
		
		if(algaSecurity.podeConsultarFormasPagamento()) {
			formasPagamentoModel.add(algaLinksHelper.linkToFormasPagamento());
		}
		
		return formasPagamentoModel;
	}

}
