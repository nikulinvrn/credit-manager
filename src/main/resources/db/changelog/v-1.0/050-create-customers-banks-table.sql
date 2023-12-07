CREATE TABLE customers_banks (
    customer_id UUID not null,
    bank_id bigint not null,
    FOREIGN KEY (bank_id) REFERENCES banks (id)
        ON DELETE cascade,
    FOREIGN KEY (customer_id) REFERENCES customers (id)
        ON DELETE cascade,
    CONSTRAINT pk_customers_banks PRIMARY KEY (bank_id, customer_id)
);

