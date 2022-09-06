set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from restaurante_usuario_responsavel;
delete from pedido;
delete from item_pedido;
delete from foto_produto;
delete from oauth2_registered_client;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into forma_pagamento (id, descricao, data_atualizacao) values (1, 'Cartão de crédito', utc_timestamp);
insert into forma_pagamento (id, descricao, data_atualizacao) values (2, 'Cartão de débito', utc_timestamp);
insert into forma_pagamento (id, descricao, data_atualizacao) values (3, 'Dinheiro', utc_timestamp);

insert into permissao (id, nome, descricao) values (1, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (id, nome, descricao) values (3, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (id, nome, descricao) values (5, 'CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários');
insert into permissao (id, nome, descricao) values (6, 'EDITAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários');
insert into permissao (id, nome, descricao) values (7, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (id, nome, descricao) values (8, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (id, nome, descricao) values (9, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (id, nome, descricao) values (10, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 0, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into grupo (id, nome) values (1, 'Gerente');
insert into grupo (id, nome) values (2, 'Vendedor');
insert into grupo (id, nome) values (3, 'Secretária');
insert into grupo (id, nome) values (4, 'Cadastrador');

insert into usuario(id, nome, email, senha, data_cadastro) values(1, 'João da Silva', 'joao.ger@yopmail.com', '$2a$12$mSmgnhsxKkpvTVEHlPM06ub4fYpxzyuraajI0SHOE9YJtIP0wgu4S', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values(2, 'Maria Joaquina', 'maria.vnd@yopmail.com', '$2a$12$mSmgnhsxKkpvTVEHlPM06ub4fYpxzyuraajI0SHOE9YJtIP0wgu4S', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values(3, 'José Souza', 'jose.aux@yopmail.com', '$2a$12$mSmgnhsxKkpvTVEHlPM06ub4fYpxzyuraajI0SHOE9YJtIP0wgu4S', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values(4, 'Sebastião Martins', 'sebastiao.cad@yopmail.com', '$2a$12$mSmgnhsxKkpvTVEHlPM06ub4fYpxzyuraajI0SHOE9YJtIP0wgu4S', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values(5, 'Manoel Lima', 'manoel.loja@yopmail.com', '$2a$12$mSmgnhsxKkpvTVEHlPM06ub4fYpxzyuraajI0SHOE9YJtIP0wgu4S', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values(6, 'Débora Mendonça', 'debora.cliente@yopmail.com', '$2a$12$mSmgnhsxKkpvTVEHlPM06ub4fYpxzyuraajI0SHOE9YJtIP0wgu4S', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values(7, 'Carlos Lima', 'carlos.cliente@yopmail.com', '$2a$12$mSmgnhsxKkpvTVEHlPM06ub4fYpxzyuraajI0SHOE9YJtIP0wgu4S', utc_timestamp);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total) values (1, 'c7c917ce-4016-11ec-a47b-706979a681a3', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil','CRIADO', utc_timestamp, 298.90, 10, 308.90);
insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total) values (2, 'c7c91ffc-4016-11ec-a47b-706979a681a3', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'CRIADO', utc_timestamp, 79, 0, 79);
insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total) values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil', 'ENTREGUE', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);
insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total) values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro', 'ENTREGUE', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);
insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total) values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins', 'ENTREGUE', '2019-11-03 02:00:30', '2019-11-02 21:01:21', '2019-11-02 21:20:10', 87.2, 10, 97.2);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) values (1, 1, 1, 1, 78.9, 78.9, null);
insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');
insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) values (3, 2, 6, 1, 79, 79, 'Ao ponto');
insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) values (4, 3, 2, 1, 110, 110, null);
insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) values (5, 4, 3, 2, 87.2, 174.4, null);
insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) values (6, 5, 3, 1, 87.2, 87.2, null);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 5), (3, 5), (1, 2);

# Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

# Adiciona permissoes no grupo do vendedor
insert into grupo_permissao (grupo_id, permissao_id)
select 2, id from permissao where nome like 'CONSULTAR_%';

insert into grupo_permissao (grupo_id, permissao_id)
select 2, id from permissao where nome = 'EDITAR_RESTAURANTES';

# Adiciona permissoes no grupo do auxiliar
insert into grupo_permissao (grupo_id, permissao_id)
select 3, id from permissao where nome like 'CONSULTAR_%';

# Adiciona permissoes no grupo cadastrador
insert into grupo_permissao (grupo_id, permissao_id)
select 4, id from permissao where nome like '%_RESTAURANTES';

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

insert into oauth2_registered_client(id,client_id,client_id_issued_at,client_secret,client_secret_expires_at,client_name,client_authentication_methods,authorization_grant_types,redirect_uris,scopes,client_settings,token_settings) 
values ('1','algafood-backend','2022-09-06 14:54:59','$2a$10$NfBs1HoeEgEGuhimqyylEu93IBH2sJ9ZcKhFrMZHadp.3oa3Xv5rq',NULL,'1','client_secret_basic','client_credentials','','READ','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":false}','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",1800.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]}');

insert into oauth2_registered_client(id,client_id,client_id_issued_at,client_secret,client_secret_expires_at,client_name,client_authentication_methods,authorization_grant_types,redirect_uris,scopes,client_settings,token_settings) 
values ('2','algafood-web','2022-09-06 14:54:59','$2a$10$DjMEnXUgZQE2bgEdB/BI2.vFxs1LDv5TRS7JoGlex1ky4XoHvEEiC',NULL,'2','client_secret_basic','refresh_token,authorization_code','http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized','READ,WRITE','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":false,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",900.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",86400.000000000]}');

