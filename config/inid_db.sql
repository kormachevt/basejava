create table resume
(
	uuid varchar(36) not null
		constraint resume_pk
			primary key,
	full_name text not null
);

alter table resume owner to postgres;

create table contact
(
    id          serial      not null
        constraint contact_pk
            primary key,
    type        text        not null,
    value       text        not null,
    resume_uuid varchar(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on update restrict on delete cascade
);

alter table contact
    owner to postgres;

create unique index contact_unique_uuid_type_index
    on contact (resume_uuid, type);

create table section
(
    id          serial not null
        constraint section_pk
            primary key,
    value       text   not null,
    type        text   not null,
    resume_uuid varchar(36)
        constraint section_resume_uuid_fk
            references resume
            on update restrict on delete cascade
);

alter table section
    owner to postgres;

create unique index section_id_uindex
    on section (id);

create unique index section_resume_uuid_type_uindex
    on section (resume_uuid, type);
