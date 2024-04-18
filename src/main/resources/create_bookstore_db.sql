CREATE ROLE dev_book_store_admin WITH LOGIN CREATEDB ENCRYPTED PASSWORD 'fakedPassword';

create sequence seq_action_id;

alter sequence seq_action_id owner to dev_book_store_admin;

create domain tbanknotesamt as integer;

alter domain tbanknotesamt owner to dev_book_store_admin;

create domain tbinary as bytea;

alter domain tbinary owner to dev_book_store_admin;

create domain tboolean as boolean;

alter domain tboolean owner to dev_book_store_admin;

create domain tchessgamescore as numeric(3, 1);

alter domain tchessgamescore owner to dev_book_store_admin;

create domain tcoinsamt as integer;

alter domain tcoinsamt owner to dev_book_store_admin;

create domain tcurrencyiso as varchar(3);

alter domain tcurrencyiso owner to dev_book_store_admin;

create domain tcurrencystr10 as varchar(10);

alter domain tcurrencystr10 owner to dev_book_store_admin;

create domain tdate as date;

alter domain tdate owner to dev_book_store_admin;

create domain tdatetime as timestamp;

alter domain tdatetime owner to dev_book_store_admin;

create domain tgpscoordinates as point;

alter domain tgpscoordinates owner to dev_book_store_admin;

create domain tidbigcode as bigint;

alter domain tidbigcode owner to dev_book_store_admin;

create domain tidcode as integer;

comment on type tidcode is 'Код сущности';

alter domain tidcode owner to dev_book_store_admin;

create domain tiduser as integer;

alter domain tiduser owner to dev_book_store_admin;

create domain timage as bytea;

alter domain timage owner to dev_book_store_admin;

create domain tintcounter as integer;

alter domain tintcounter owner to dev_book_store_admin;

create domain tinteger as integer;

alter domain tinteger owner to dev_book_store_admin;

create domain titemtype as smallint;

alter domain titemtype owner to dev_book_store_admin;

create domain tmoney as numeric(22, 4);

alter domain tmoney owner to dev_book_store_admin;

create domain tpercrate as numeric(12, 6);

alter domain tpercrate owner to dev_book_store_admin;

create domain tpercrateext as numeric(16, 8);

alter domain tpercrateext owner to dev_book_store_admin;

create domain treal as real;

alter domain treal owner to dev_book_store_admin;

create domain tstr10 as varchar(10);

alter domain tstr10 owner to dev_book_store_admin;

create domain tstr100 as varchar(100);

alter domain tstr100 owner to dev_book_store_admin;

create domain tstr10000 as varchar(10000);

alter domain tstr10000 owner to dev_book_store_admin;

create domain tstr128 as varchar(128);

alter domain tstr128 owner to dev_book_store_admin;

create domain tstr2 as varchar(2);

alter domain tstr2 owner to dev_book_store_admin;

create domain tstr20 as varchar(20);

alter domain tstr20 owner to dev_book_store_admin;

create domain tstr200 as varchar(200);

alter domain tstr200 owner to dev_book_store_admin;

create domain tstr2000 as varchar(2000);

alter domain tstr2000 owner to dev_book_store_admin;

create domain tstr3 as varchar(3);

alter domain tstr3 owner to dev_book_store_admin;

create domain tstr30 as varchar(30);

alter domain tstr30 owner to dev_book_store_admin;

create domain tstr40 as varchar(40);

alter domain tstr40 owner to dev_book_store_admin;

create domain tstr400 as varchar(400);

alter domain tstr400 owner to dev_book_store_admin;

create domain tstr50 as varchar(50);

alter domain tstr50 owner to dev_book_store_admin;

create domain tstr80 as varchar(80);

alter domain tstr80 owner to dev_book_store_admin;

create domain tsumext as numeric(24, 4);

alter domain tsumext owner to dev_book_store_admin;

create domain ttext as text;

alter domain ttext owner to dev_book_store_admin;

create domain ttime as time;

alter domain ttime owner to dev_book_store_admin;

create domain tidbytecode as numeric(1, 0);

alter domain tidbytecode owner to dev_book_store_admin;

create domain tstr500 as varchar(500);

alter domain tstr500 owner to dev_book_store_admin;

create domain tstr1000 as varchar(1000);

alter domain tstr1000 owner to dev_book_store_admin;

CREATE TABLE statuses_ref
(
    status_id bigint PRIMARY KEY NOT NULL ,
    status_code varchar(100) NOT NULL,
    constraint code_unique UNIQUE (status_code),
    status_name varchar(120) NOT NULL

);

CREATE TABLE core_entities
(
    core_entity_id bigint PRIMARY KEY NOT NULL ,
    status_id bigint REFERENCES statuses_ref(status_id),
    create_date timestamp NOT NULL ,
    delete_date timestamp
);

CREATE TABLE roles_ref
(
    role_id bigint PRIMARY KEY NOT NULL ,
    role_code varchar(100) NOT NULL,
    constraint code1_unique UNIQUE (role_code),
    role_name varchar(100) NOT NULL
);

CREATE TABLE users
(
    user_id bigint primary key REFERENCES core_entities(core_entity_id) NOT NULL ,
    login varchar(20) NOT NULL ,
    constraint login_unique unique(login),
    password varchar(200) NOT NULL ,
    username varchar(24) NOT NULL ,
    user_age smallint ,
    user_role_id bigint REFERENCES roles_ref(role_id) NOT NULL
);

CREATE TABLE carts
(
    cart_id bigint PRIMARY KEY REFERENCES core_entities(core_entity_id) NOT NULL ,
    user_id bigint REFERENCES users(user_id) NOT NULL ,
    total_price money NOT NULL,
    cart_code varchar(100) NOT NULL,
    constraint code2_unique UNIQUE (cart_code)
);

CREATE TABLE genre_ref
(
    genre_id bigint PRIMARY KEY NOT NULL ,
    genre_code varchar(100) NOT NULL ,
    constraint code3_unique UNIQUE (genre_code),
    genre_name varchar(120) NOT NULL
);

CREATE TABLE books
(
    book_id bigint PRIMARY KEY  REFERENCES core_entities(core_entity_id) NOT NULL ,
    genre_id bigint REFERENCES  genre_ref(genre_id) NOT NULL,
    book_publisher varchar(120) NOT NULL ,
    book_price money NOT NULL ,
    book_description varchar(10000),
    book_pages int,
    book_quantity int,
    book_name varchar(120) NOT NULL ,
    book_code varchar(100) NOT NULL,
    constraint code4_unique UNIQUE (book_code)

);
CREATE TABLE cart_item
(
    cart_item_id bigint PRIMARY KEY REFERENCES core_entities(core_entity_id) NOT NULL ,
    book_id bigint REFERENCES books(book_id) NOT NULL ,
    cart_id bigint REFERENCES carts(cart_id) NOT NULL ,
    cart_item_code varchar(100) NOT NULL,
    constraint code5_unique UNIQUE (cart_item_code)
);

CREATE TABLE orders
(
    order_id bigint PRIMARY KEY REFERENCES core_entities(core_entity_id) NOT NULL ,
    user_id bigint REFERENCES users(user_id) NOT NULL ,
    address varchar(120) NOT NULL ,
    order_price money NOT NULL ,
    order_date timestamp NOT NULL ,
    order_code varchar(100) NOT NULL,
    constraint code6_unique UNIQUE (order_code)
);

CREATE TABLE order_items
(
    order_items_id bigint PRIMARY KEY REFERENCES core_entities(core_entity_id) NOT NULL ,
    book_id bigint REFERENCES books(book_id) NOT NULL ,
    order_id bigint REFERENCES orders(order_id) NOT NULL ,
    order_item_code varchar(100) NOT NULL ,
    constraint code7_unique UNIQUE (order_item_code),
    order_item_price money NOT NULL ,
    order_items_amount int NOT NULL ,
    order_items_total_price money NOT NULL
);
-- можешь добавить уникальность на коды
-- в остальном вроде все ок, начинай писать код, там будет видно
