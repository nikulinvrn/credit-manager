create table customers (
          id UUID primary key not null,
          firstname character varying(255) not null,
          lastname character varying(255) not null,
          surname character varying(255),
          number bigint not null,
          series bigint not null,
          email character varying(255) not null,
          is_active boolean not null default true
);


