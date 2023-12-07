CREATE TABLE loan_offers (
    id bigint PRIMARY KEY Generated Always as Identity not null,
    is_accepted boolean not null,
    is_active boolean not null,
    principal_of_credit integer,
    sum_of_credit integer,
    sum_of_interest integer,
    bank_id bigint,
    credit_type_id bigint,
    customer_id UUID,
    FOREIGN KEY (customer_id) REFERENCES customers (id)
        ON DELETE cascade,
    FOREIGN KEY (credit_type_id) REFERENCES credit_types (id)
        ON DELETE cascade,
    FOREIGN KEY (bank_id) REFERENCES banks (id)
        ON DELETE cascade
);

