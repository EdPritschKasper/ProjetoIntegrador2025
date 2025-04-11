create table tb_funcionario (
	id serial primary key,
	nome varchar(50) not null
)

create table tb_cliente (
	id serial primary key,
	nome varchar(50) not null,
	email varchar(50) not null unique,
	senha varchar(50) not null
)

create table tb_cardapio (
	id serial primary key,
	data date not null
)

create table tb_ingrediente (
	id serial primary key,
	descricao varchar(50) not null
)

create table tb_pedido (
	id serial primary key,
	marmita varchar(10),
	status varchar(10),
	hora_inicio time not null,
	hora_fim time,
	cardapio_id bigint not null,
	funcionario_id bigint,
	cliente_id bigint,
	constraint fk_cardapio_id foreign key (cardapio_id) references tb_cardapio (id),
	constraint fk_funcionario_id foreign key (funcionario_id) references tb_funcionario (id),
	constraint fk_cliente_id foreign key (cliente_id) references tb_cliente (id)
)

create table tb_cardapio_ingrediente (
	cardapio_id bigint not null,
	ingrediente_id bigint not null,
	constraint fk_cardapio_id foreign key (cardapio_id) references tb_cardapio (id),
	constraint fk_ingrediente_id foreign key (ingrediente_id) references tb_ingrediente (id)
)

create table tb_pedido_ingrediente (
	pedido_id bigint not null,
	ingrediente_id bigint not null,
	constraint fk_pedido_id foreign key (pedido_id) references tb_pedido (id),
	constraint fk_ingrediente_id foreign key (ingrediente_id) references tb_ingrediente (id)
)

-- INSERT INTO
insert into tb_funcionario (nome) values
('Didi Mocó'),
('Zacarias');

insert into tb_cliente (nome, email, senha) values
('Dado Dolabela', 'dado@gmail.com', 'iloveyou'),
('Luan Santana', 'luan@gmail.com', 'meteorodapaixao');

insert into tb_cardapio (data) values
('30-03-2025'),
('31-03-2025'),
('01-04-2025');

insert into tb_ingrediente (descricao) values
('arroz'),
('feijão'),
('frango'),
('bife'),
('alface'),
('tomate'),
('batata frita');

insert into tb_pedido (marmita, status, hora_inicio, hora_fim, cardapio_id, funcionario_id, cliente_id) values
(null, 'retirado', '12:20:05','12:40:05', 1, 1, 1),
('pequena', 'retirado', '12:20:05','12:40:05', 2, 2, 2),
('grande', 'preparando', '12:20:05','12:40:05', 3, 1, 2);

insert into tb_cardapio_ingrediente (cardapio_id, ingrediente_id) values
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 4), (2, 5), (2, 6), (2, 7),
(3, 1), (3, 2), (3, 5), (3, 7);

insert into tb_pedido_ingrediente (pedido_id, ingrediente_id) values
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 4), (2, 5), (2, 6), (2, 7),
(3, 1), (3, 2), (3, 5), (3, 7);


