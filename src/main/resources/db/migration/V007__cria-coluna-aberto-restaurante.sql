alter table restaurante add column aberto tinyint(1) not null default false;
update restaurante set aberto=false;