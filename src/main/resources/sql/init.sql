create table link
(
    id bigserial
        constraint link_pk
            primary key,
    url varchar(255) not null,
    status varchar,
    created date
);

alter table link owner to myuser;

