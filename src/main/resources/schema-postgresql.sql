CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists role (
    role_id uuid primary key default uuid_generate_v4(),
    name varchar not null
);

create table if not exists company (
    company_id uuid primary key default uuid_generate_v4(),
    tax_id varchar not null,
    name varchar not null
);

create table if not exists user_account (
    user_account_id uuid primary key default uuid_generate_v4(),
    username varchar not null,
    password varchar not null,
    company_id uuid not null references company
);

create table if not exists user_account_role (
    role_id uuid references role,
    user_account_id uuid references user_account,
    primary key (role_id, user_account_id)
);

create table if not exists person (
    person_id uuid primary key default uuid_generate_v4(),
    name varchar not null,
    last_name varchar not null,
    email varchar,
    birth_date timestamptz,
    user_account_id uuid references user_account,
    company_id uuid not null references company
);

create table if not exists doctor (
    doctor_id uuid primary key default uuid_generate_v4(),
    person_id uuid references person,
    company_id uuid not null references company
);

create table if not exists patient (
    patient_id uuid primary key default uuid_generate_v4(),
    person_id uuid references person,
    company_id uuid not null references company
);

create table if not exists patient_history (
    patient_history_id uuid primary key default uuid_generate_v4(),
    doctor_id uuid not null references doctor,
    patient_id uuid not null references patient,
    company_id uuid not null references company
);

create table if not exists product (
    product_id uuid primary key default uuid_generate_v4(),
    price numeric not null default 0,
    description varchar not null default ''
);

create table if not exists service (
    service_id uuid primary key default uuid_generate_v4(),
    price numeric not null default 0,
    description varchar not null default ''
);

create table if not exists price_log (
    product_id uuid references product,
    service_id uuid references service,
    price numeric not null default 0,
    audit_create timestamptz not null default now()
);

create table if not exists invoice (
    invoice_id uuid primary key default uuid_generate_v4(),
    is_draft bool not null default true,
    client_id uuid not null references person,
    company_id uuid not null references company
);

create table if not exists invoice_line (
    line_id uuid primary key default uuid_generate_v4(),
    invoice_id uuid not null references invoice,
    product_id uuid references product,
    service_id uuid references service,
    quantity numeric not null default 0,
    price numeric not null default 0, -- in cents
    discount numeric not null default 0 -- in cents
);