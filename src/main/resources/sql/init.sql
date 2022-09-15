-- auto-generated definition
create table student
(
    id         bigserial
        constraint student_pk
            primary key,
    name       varchar(255) not null,
    course     integer,
    group_name varchar(255)
);

alter table student
    owner to myuser;

