-- auto-generated definition
create table link
(
    id      bigserial
        constraint link_pk
            primary key,
    url     varchar(255) not null,
    status  varchar,
    created timestamp default now()
);

alter table link
    owner to myuser;
