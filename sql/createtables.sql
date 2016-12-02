use wechat;

show tables;

create table contact(
	id int NOT NULL auto_increment,
	name varchar(80),
    primary key (id));

create table team(
	id int NOT NULL auto_increment,
    name varchar(80),
    primary key (id));
    
create table team_contact(
	id int NOT NULL auto_increment,
	teamId int not null,
    contactId int not null,
    primary key (id)
);  

create table contact_contact(
	id int NOT NULL auto_increment,
	teamId int not null,
    contactId int not null,
    primary key (id)
);      