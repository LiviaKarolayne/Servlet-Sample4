create database agendamento;

create table agendamento(
	id int(10) NOT NULL AUTO_INCREMENT,
    primary key(id),
	nome varchar(50) NOT NULL,
    cpf varchar(14) NOT NULL,
    data varchar(10) NOT NULL,
    telefone int(10) NOT NULL,
    endereco varchar(50) NOT NULL
);