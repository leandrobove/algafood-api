package com.github.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.github.algafood.domain.repository.PedidoRepository;
import com.github.algafood.domain.repository.RestauranteRepository;

@Component
public class AlgaSecurity {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Long getUsuarioId() {

		Jwt jwt = (Jwt) this.getAuthentication().getPrincipal();

		return Long.valueOf(jwt.getClaim("usuario_id"));
	}
	
	public boolean isAutenticado() {
		return this.getAuthentication().isAuthenticated();
	}
	
	public boolean hasAuthority(String authorityName) {
		return this.getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}

	public boolean gerenciaRestaurante(Long restauranteId) {
		if (restauranteId == null) {
			return false;
		}
		return restauranteRepository.isResponsavel(this.getUsuarioId(), restauranteId);
	}

	public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
		return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, this.getUsuarioId());
	}
	
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return this.getUsuarioId() != null && usuarioId != null && this.getUsuarioId().equals(usuarioId);
	}
	
	public boolean podeGerenciarPedidos(String codigoPedido) {
		return this.temEscopoEscrita() && 
				(this.hasAuthority("GERENCIAR_PEDIDOS") || this.gerenciaRestauranteDoPedido(codigoPedido));
	}
	
	public boolean temEscopoLeitura() {
		return this.hasAuthority("SCOPE_READ");
	}
	
	public boolean temEscopoEscrita() {
		return this.hasAuthority("SCOPE_WRITE");
	}
	
	public boolean podeConsultarCozinhas() {
		return this.isAutenticado() && this.temEscopoLeitura();
	}
	
	public boolean podeEditarCozinhas() {
		return this.temEscopoEscrita() && this.hasAuthority("EDITAR_COZINHAS");
	}
	
	public boolean podeConsultarRestaurantes() {
		return this.isAutenticado() && this.temEscopoLeitura();
	}
	
	public boolean podeGerenciarCadastroRestaurantes() {
		return this.temEscopoEscrita() && this.hasAuthority("EDITAR_RESTAURANTES");
	}
	
	public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
		return this.temEscopoEscrita() && (this.hasAuthority("EDITAR_RESTAURANTES") || this.gerenciaRestaurante(restauranteId));
	}
	
	public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {	
		return this.temEscopoLeitura() && (this.hasAuthority("CONSULTAR_PEDIDOS") || 
				this.usuarioAutenticadoIgual(clienteId) || this.gerenciaRestaurante(restauranteId));
	}
	
	public boolean podeConsultarFormasPagamento() {
		return this.temEscopoLeitura() && this.isAutenticado();
	}
	
	public boolean podeEditarFormasPagamento() {
		return this.temEscopoEscrita() && this.hasAuthority("EDITAR_FORMAS_PAGAMENTO");
	}
	
	public boolean podeConsultarCidades() {
		return this.temEscopoLeitura() && this.isAutenticado();
	}
	
	public boolean podeEditarCidades() {
		return this.temEscopoEscrita() && this.hasAuthority("EDITAR_CIDADES");
	}
	
	public boolean podeConsultarEstados() {
		return this.temEscopoLeitura() && this.isAutenticado();
	}
	
	public boolean podeEditarEstados() {
		return this.temEscopoEscrita() && this.hasAuthority("EDITAR_ESTADOS");
	}
	
	public boolean podeEditarUsuariosGruposPermissoes() {
		return this.temEscopoEscrita() && this.hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podeConsultarUsuariosGruposPermissoes() {
		return this.temEscopoLeitura() && this.hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podeConsultarEstatisticas() {
		return this.temEscopoLeitura() && this.hasAuthority("GERAR_RELATORIOS");
	}

}
