create schema sisVacation;
use sisVacation;

create table login(
	id_login int not null auto_increment default 0,
	username varchar(50),
    senha varchar(50),
    primary key (id_login)
);

INSERT INTO login(id_login, username, senha) VALUES( 0, "admin", "admin");

create table usuario(
	id_pessoa int not null,
    nome varchar(50) not null,
    foreign key (id_pessoa) references login(id_login),
    primary key (id_pessoa)
);