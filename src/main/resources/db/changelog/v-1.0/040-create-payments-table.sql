CREATE TABLE payments (
    id bigint primary key Generated Always as Identity not null,
    balance_of_debt double precision not null,
    date date not null,
    interest_of_payment double precision not null,
    is_payed boolean not null,
    principal_of_payment double precision not null,
    sum_of_payment double precision not null,
    loan_offer_id bigint not null,
    FOREIGN KEY (loan_offer_id) REFERENCES loan_offers (id)
        ON UPDATE cascade
        ON DELETE cascade
);