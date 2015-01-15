create table user (
    id bigint generated by default as identity unique,
    username varchar(255) unique,
    password varchar(255) not null,
    primary key (id)
);

insert into user (id, username, password) values (1, 'aacs', 'aacs');
insert into user (id, username, password) values (2, 'admin', 'admin');

create table article (
    id bigint generated by default as identity unique,
    code varchar(255) not null unique,
    name varchar(255) not null unique,
    price bigint not null,
    icon varchar(255) not null,
    comment varchar(255),
    primary key (id)
);

insert into article (id, code, name, price, icon) values (1, '1234', 'Sör', 200, 'icon/icon.icon');
insert into article (id, code, name, price, icon) values (2, '234', 'Bor', 1500, 'icon/icon.icon');
insert into article (id, code, name, price, icon) values (3, '34', 'Pálinka', 4000, 'icon/icon.icon');

create table customer (
    id bigint generated by default as identity unique,
    code varchar(255) not null unique,
    name varchar(255) not null unique,
    primary key (id)
);

insert into customer (id, code, name) values (1, '0001', 'Ács Ádám');
insert into customer (id, code, name) values (2, '0002', 'Szemők Balázs');

create table bill (
    id bigint generated by default as identity unique,
    customer bigint not null,
    openDate datetime not null,
    closeDate datetime default null,
    currency varchar(255) not null,
    primary key (id),
    FOREIGN KEY (customer) REFERENCES customer(id)
);

insert into bill (id, customer, openDate, closeDate, currency) values (1, 1, '2015-01-15 23:59:59', null, 'FT');
insert into bill (id, customer, openDate, closeDate, currency) values (2, 2, '2015-01-15 23:59:59', null, 'GBP');

create table item (
    id bigint generated by default as identity unique,
    bill bigint not null,
    article bigint not null,
    amount bigint not null,
    outDate datetime default null,
    primary key (id),
    FOREIGN KEY (bill) REFERENCES bill(id),
    FOREIGN KEY (article) REFERENCES article(id)
);

insert into item (id, bill, article, amount, outDate) values (1, 1, 1, 11, null);
insert into item (id, bill, article, amount, outDate) values (2, 1, 2, 11, null);
insert into item (id, bill, article, amount, outDate) values (3, 2, 1, 11, null);
insert into item (id, bill, article, amount, outDate) values (4, 2, 2, 11, null);