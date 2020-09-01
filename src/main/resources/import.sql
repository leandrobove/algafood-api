insert into cozinha(id, nome) values(1, 'Tailandesa');
insert into cozinha(id, nome) values(2, 'Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 15, 2);

insert into estado (id,nome) values(1, 'São Paulo');
insert into estado (id,nome) values(2, 'Rio de Janeiro');

insert into cidade(nome, estado_id) values ('Bragança Paulista', 1);
insert into cidade(nome, estado_id) values ('Araraquara', 1);
insert into cidade(nome, estado_id) values ('Atibaia', 1);
insert into cidade(nome, estado_id) values ('Niterói', 2);
insert into cidade(nome, estado_id) values ('Macaé', 2);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');