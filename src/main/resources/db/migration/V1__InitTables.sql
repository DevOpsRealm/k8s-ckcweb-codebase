create table menu (
    id  bigserial not null,
    created_at timestamp,
    description varchar(255),
    primary_menu boolean,
    updated_at timestamp,
    url varchar(255),
    uuid varchar(255),
    parent_id int8,
    primary key (id)
);

create table post (
   id  bigserial not null,
    content text,
    date_path date,
    excerpt varchar(255),
    keywords varchar(255),
    override_defaults boolean,
    posted_at timestamp,
    slug varchar(255),
    static_page boolean,
    title varchar(255),
    updated_at timestamp,
    uuid varchar(255),
    primary key (id)
);

create table users (
    id  bigserial not null,
    designation varchar(255),
    full_name varchar(255),
    password varchar(255),
    roles varchar(255),
    username varchar(255),
    primary key (id)
);
