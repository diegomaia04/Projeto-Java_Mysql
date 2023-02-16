/**
Projeto para Assistência técnica de VideoGames
  @author Bruno Henrique e Diego Maia 
    @version 1.0
*/


create database dbgames;
use dbgames;
drop database dbgames;

create table login(
   idfor int primary key auto_increment,
 foreign key(idfor) references usuarios(idfor)
);

drop table login;

insert into login(login,senha,perfil)
values('admin',md5('admin'),'admin');

insert into login(login,senha,perfil)
values('Bruno',md5('1234'),'Usuário');

insert into login(login,senha,perfil)
values('Diego',md5('1234'),'Usuário');

select * from login;

select * from usuarios where perfil = 'admin';

create table usuarios (
idfor int primary key auto_increment,
nome varchar(50) not null,
cpf varchar(16) not null unique,
endereco varchar (100) not null,
numero varchar (6) not null,
complemento varchar(100),
bairro varchar(50) not null,
cidade varchar(50) not null,
cep varchar(10),
uf char(2) not null,
contato varchar(30)not null,
email varchar (250),
login varchar(15) not null unique,
senha varchar(250) not null,
perfil varchar(20) not null
);

drop table usuarios;

insert into usuarios(nome,cpf,endereco,numero,complemento,bairro,cidade,cep,uf,contato,email,login,senha,perfil)
values('Colono', '125879512','Rua Francisco Gouveia','26','Perto da Igreja','Mooca','São Paulo','08017431','SP','953336542','maiadiego290@gmail.com','Colono',md5('1234'),'usuário'); 

insert into usuarios(nome,cpf,endereco,numero,complemento,bairro,cidade,cep,uf,contato,email,login,senha,perfil)
values('Yayah', '35905128812','Rua Baltazar da Silva','48','Perto da Praça','Itaquera','São Paulo','132456789','SP','985962472','yayah@email.com','Yayah',md5('admin'),'admin'); 

insert into usuarios(nome,cpf,endereco,numero,complemento,bairro,cidade,cep,uf,contato,email,login,senha,perfil)
values('Colono', '12546879555312','Rua Francisco Gouveia','467','Perto da Igreja','Mooca','São Paulo','08017431','SP','953336542','maiadiego290@gmail.com','Colono',md5('1234'),'Usuário'); 

insert into usuarios(nome,cpf,endereco,numero,complemento,bairro,cidade,cep,uf,contato,email,login,senha,perfil)
values('Admin','12579555312','Rua Francisco Gouveia','467','Perto da Igreja','Mooca','São Paulo','08017431','SP','953336542','maiadiego290@gmail.com','Admin',md5('admin'),'admin'); 

delete from usuarios where idFor = 3;

select * from usuarios;


create table clientes(
id int primary key auto_increment,
nome varchar(50) not null,
cpf varchar(16) not null unique,
endereco varchar (30) not null,
numero varchar (6) not null,
complemento varchar(250),
datacad timestamp default current_timestamp,
bairro varchar(50) not null,
cidade varchar(50) not null,
cep varchar(10),
uf char(2) not null,
contato varchar(30) not null,
email varchar (250) not null,
obs varchar (250) not null
); 

insert into clientes (nome,cpf,endereco,numero,complemento,bairro,cidade,cep,uf,contato,email,obs)
values('Hayashii','87409127','Mendes junior','718','ap85','bras','sao paulo','03013011','SP','Hayashii','hayashii@hotmail.com','qualquer coisa');

update clientes set nome = 'Colono Hammer',contato = '4901729041' , obs = 'Coé'  where id = 2;


drop table clientes;

select * from clientes;


create table os(
idfor int primary key auto_increment,
tecnico varchar (250) not null,
modelo varchar (250) not null,
serie varchar (250) not null, 
defeito varchar (250) not null,
datacad timestamp default current_timestamp,
solucao varchar (250),
pendencia varchar(20) not null,
total decimal(10,2),
garantia date,
id int not null,
nomee varchar (50) not null,
forma varchar(250),
razao varchar(250),
foreign key(id) references clientes(id)
); 

drop table os;

insert into os (tecnico,modelo,serie,defeito,solucao,pendencia,total,garantia,id,nomee,forma)
values ('roberta','ps4','hf832820','não esta ligando',' arruamamos o fio de força', 'pendente','300','20231202','1','Hayashii','cheque'); 

insert into os (tecnico,modelo,serie,defeito,solucao,pendencia,total,id,nomee)
values ('robesrta','pss4','hf832820','não esta ligando',' arruamamos o fio de força', 'pendente','300','1','Hayashii'); 

update os set pendencia = 'resolvido' where idfor = 1;
 
select * from os;

-- relatório 1 (clientes) --
select * from clientes order by nome; 


-- relatório 2 (Pendentes) --

select 
os.idfor as OS,date_format(os.datacad,'%d/%m/%Y') as data,
os.modelo,os.solucao as Solução,os.pendencia as status,os.total,
clientes.nome as Clientes,clientes.contato as Telefone
from os inner join clientes on os.id = clientes.id
where pendencia = 'pendente' ;

-- relatorios 3 (Entregues)--
select 
os.idfor as OS,date_format(os.datacad,'%d/%m/%Y') as data,
os.modelo,os.solucao as Solução,os.pendencia as status,os.total,
clientes.nome as Clientes,clientes.contato as Telefone
from os inner join clientes on os.id = clientes.id
where pendencia = 'resolvido' ;
