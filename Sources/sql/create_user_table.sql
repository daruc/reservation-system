create table users (
	id serial PRIMARY KEY,
	login varchar(100) not null,
	password varchar(100) not null,
	name varchar(100),
	surname varchar(100)
);