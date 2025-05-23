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

create table if not exists statuses_ref
(
    status_id   bigint       not null
        primary key,
    status_code varchar(100) not null
        constraint code_unique
            unique,
    status_name varchar(120) not null
);

alter table statuses_ref
    owner to dev_book_store_admin;

create table if not exists core_entities
(
    core_entity_id bigint    not null
        primary key,
    status_id      bigint
        references statuses_ref,
    create_date    timestamp not null,
    delete_date    timestamp
);

alter table core_entities
    owner to dev_book_store_admin;

create table if not exists roles_ref
(
    role_id   bigint       not null
        primary key,
    role_code varchar(100) not null
        constraint code1_unique
            unique,
    role_name varchar(100) not null
);

alter table roles_ref
    owner to dev_book_store_admin;

create table if not exists users
(
    user_id      bigint       not null
        primary key
        references core_entities,
    login        varchar(20)  not null
        constraint login_unique
            unique,
    password     varchar(200) not null,
    user_age     smallint,
    user_role_id bigint       not null
        references roles_ref
);

alter table users
    owner to dev_book_store_admin;

create table if not exists carts
(
    cart_id    bigint       not null
        primary key
        references core_entities,
    user_id    bigint       not null
        references users,
    cart_code  varchar(100) not null
        constraint code2_unique
            unique,
    cart_price numeric(22, 2)
);

alter table carts
    owner to dev_book_store_admin;

create table if not exists genre_ref
(
    genre_id   bigint       not null
        primary key,
    genre_code varchar(100) not null
        constraint code3_unique
            unique,
    genre_name varchar(120) not null
);

alter table genre_ref
    owner to dev_book_store_admin;

create table if not exists books
(
    book_id          bigint         not null
        primary key
        references core_entities,
    genre_id         bigint         not null
        references genre_ref,
    book_publisher   varchar(120)   not null,
    book_price       numeric(22, 2) not null,
    book_description varchar(10000),
    book_pages       integer,
    book_quantity    integer,
    book_name        varchar(120)   not null,
    book_code        varchar(100)   not null
        constraint code4_unique
            unique,
    image            bytea
);

alter table books
    owner to dev_book_store_admin;

create table if not exists cart_items
(
    cart_item_id       bigint       not null
        constraint cart_item_pkey
            primary key
        constraint cart_item_cart_item_id_fkey
            references core_entities,
    book_id            bigint       not null
        constraint cart_item_book_id_fkey
            references books,
    cart_id            bigint       not null
        constraint cart_item_cart_id_fkey
            references carts,
    cart_item_code     varchar(100) not null
        constraint code5_unique
            unique,
    cart_item_quantity integer
);

alter table cart_items
    owner to dev_book_store_admin;

create table if not exists orders
(
    order_id    bigint         not null
        primary key
        references core_entities,
    user_id     bigint         not null
        references users,
    address     varchar(120)   not null,
    order_price numeric(22, 2) not null,
    order_date  timestamp      not null,
    order_code  varchar(100)   not null
        constraint code6_unique
            unique
);

alter table orders
    owner to dev_book_store_admin;

create table if not exists order_items
(
    order_items_id    bigint         not null
        primary key
        references core_entities,
    book_id           bigint         not null
        references books,
    order_id          bigint         not null
        references orders,
    order_item_code   varchar(100)   not null
        constraint code7_unique
            unique,
    order_item_price  numeric(22, 2) not null,
    order_item_amount integer        not null
);

alter table order_items
    owner to dev_book_store_admin;

create or replace function update_cart_price() returns trigger
    language plpgsql
as
$$
BEGIN

    UPDATE carts
    SET cart_price = (
        SELECT COALESCE(SUM(ci.cart_item_quantity * b.book_price), 0)
        FROM cart_items ci
                 JOIN books b ON ci.book_id = b.book_id
                 JOIN core_entities ce ON ce.core_entity_id = ci.cart_item_id
        WHERE ci.cart_id = NEW.cart_id
          AND ce.status_id != 5
    )
    WHERE cart_id = NEW.cart_id;

    RETURN NEW;
END;
$$;

alter function update_cart_price() owner to postgres;

create trigger cart_price_after_insert
    after insert
    on cart_items
    for each row
execute procedure update_cart_price();

create trigger cart_price_after_update
    after update
    on cart_items
    for each row
execute procedure update_cart_price();

create trigger cart_price_after_delete
    after delete
    on cart_items
    for each row
execute procedure update_cart_price();



