CREATE TABLE credit_types (
    id bigint primary key Generated Always as Identity not null,
    credit_limit integer not null,
    interest_rate double precision not null,
    name character varying(255) not null,
    bank_id bigint not null,
    FOREIGN KEY (bank_id)
        REFERENCES banks (id)
        ON DELETE cascade
);




