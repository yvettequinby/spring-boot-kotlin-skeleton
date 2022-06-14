create table hello_user
(
    hello_user_id bigserial constraint hello_user_pk primary key,
    email         varchar not null,
    password      varchar not null
);

create unique index hello_user_email_uindex
    on hello_user (email);