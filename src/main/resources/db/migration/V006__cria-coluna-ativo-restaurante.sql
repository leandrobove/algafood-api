alter table restaurante add column ativo tinyint(1) not null default true;
update restaurante set ativo = true;