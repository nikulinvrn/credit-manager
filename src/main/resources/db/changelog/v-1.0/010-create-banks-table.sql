create table banks (
    id bigint primary key Generated Always as Identity not null,
    name character varying(255)
);
--GO

create unique index uk_bankname on banks using btree (name);


