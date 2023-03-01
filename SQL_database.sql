IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'mycompany2')
  BEGIN
    CREATE DATABASE mycompany2;

    END
	go
use mycompany2;

drop table users_roles;
drop table users;
drop table roles;
drop table address;


create table address (
id 			int		 		NOT NULL constraint pk1_id primary key identity(1,1),
country 	varchar(255)	NOT NULL,
state		varchar(255)	NOT NULL,
city		varchar(255)	NOT NULL,
street		varchar(255),
house_no	varchar(20)		NOT NULL,
flat_no		varchar(20)
);

create table roles(
role_id 	int 			NOT NULL constraint pk2_id primary key identity(1,1),
name		varchar(255)	NOT NULL
);

create table users (
user_id 	int				NOT NULL constraint pk3_id primary key identity(1,1),
name		varchar(255)	NOT NULL,
surname 	varchar(255)	NOT NULL,
login	 	varchar(255)	NOT NULL,
password 	varchar(255)	NOT NULL,
enabled 	bit				NOT NULL,
address_id	int				NOT NULL
);

ALTER TABLE users
ADD FOREIGN KEY (address_id) REFERENCES address(id);

create table users_roles(
id			int				NOT NULL constraint pk4_id primary key identity(1,1),
role_id		int				NOT NULL,
user_id 	int				NOT NULL
);

ALTER TABLE users_roles
ADD FOREIGN KEY (role_id) REFERENCES roles(role_id);
ALTER TABLE users_roles
ADD FOREIGN KEY (user_id) REFERENCES users(user_id);





insert into address(country, state, city, street, house_no, flat_no)
values ('Polska', 'ma³opolskie', 'Kraków', '3 maja', '12', '4'),
('Polska', 'mazowieckie', 'Warszawa', 'Centralna', '2', '3');

insert into roles(name)
values ('ADMIN'), ('USER');

insert into users(name, surname, login, password, enabled, address_id)
values ('Arkadiusz', 'Kowalski', 'arek99', '$2a$12$Zv1gWFXyvw8z6toQqr0Hsujy3HlsJgisz1G5qC0LEbMP9Kc7v90TC', 1, 1),
('Maria', 'Nowak', 'maria99', '$2a$12$6IEzu9f14MLb6a4brDFXGejQ9ZrfTsTDZwV/MrCV9e4sTy4.ZDV9W', 1, 2);

insert into users_roles(role_id, user_id)
values (1,1), (2,2);
