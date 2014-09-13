begin transaction;

drop table if exists users;
drop table if exists user_roles;
drop table if exists posts;
drop table if exists comments;
drop table if exists notifications;

CREATE TABLE "users" (
	user_name text primary key,
	full_name text,
	password text
);

CREATE TABLE "user_roles" (
	user_name text not null,
	role_name text not null,
	primary key (user_name, role_name),
	foreign key(user_name) references users(user_name)
);

create table comments (
	"id" integer primary key autoincrement,
	"text" text,
	"user_name" text,
	"post_id" integer,
	"creation_time" date,
	foreign key(user_name) references users(user_name),
	foreign key(post_id) references posts(post_id)
);

create table posts (
	"id" integer primary key autoincrement,
	"user_name" text,
	"creation_time" date,
	"content" text,
	foreign key(user_name) references users(user_name)
);

create table notifications (
	"id" integer primary key autoincrement,
	"user_name" text,
	"message" text,
	"creation_time" date,
	"link" text,
	"isRead" boolean,
	foreign key(user_name) references users(user_name)
);

insert into users (user_name, full_name, password) values ("trung", "Trung Ngo", "1111");
insert into user_roles (user_name, role_name) values ("trung", "user");
insert into user_roles (user_name, role_name) values ("trung", "admin");

insert into users (user_name, full_name, password) values ("mike", "Mike", "1111");
insert into user_roles (user_name, role_name) values ("mike", "user");

insert into posts (user_name, creation_time, content) values ("trung", datetime("now"), "Hey @mike");
insert into posts (user_name, creation_time, content) values ("mike", datetime("now"), "Hey @trung!");

insert into comments (post_id, user_name, creation_time, text) values (0, "mike", datetime("now"), "Comment 1");

insert into notifications (user_name, message, creation_time, link, isRead) values ("trung", "hey there", datetime("now"), "/post/1", 0);

commit;
