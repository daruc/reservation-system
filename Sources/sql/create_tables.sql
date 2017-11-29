create table users (
	id serial primary key,
	login varchar(100) not null,
	password varchar(100) not null,
	permissions integer not null,
	name varchar(100),
	surname varchar(100)
);

create table groups(
	id serial primary key,
	name varchar(100) not null,
	description varchar(4000)
);

create table user_group(
	id serial primary key,
	user_id integer not null,
	group_id integer not null
);

create table made_reservations(
	id serial primary key,
	reservation_id integer not null,
	label varchar(100),
	user_id integer
);

create table resource_groups(
	id serial primary key,
	name varchar(100) not null,
	description varchar(4000)
);

create table resources(
	id serial primary key,
	resource_group_id integer not null,
	name varchar(100) not null
);

create table reservations(
	id serial primary key,
	name varchar(100) not null,
	description varchar(4000),
	group_id integer not null,
	author_id integer not null,
	resource_id integer not null
);


