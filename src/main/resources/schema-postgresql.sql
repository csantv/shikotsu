create extension if not exists "uuid-ossp";

drop schema if exists shikotsu cascade;
create schema if not exists shikotsu;
set search_path = shikotsu, public;

create table if not exists role (
    role_id uuid primary key default uuid_generate_v4(),
    name varchar not null unique
);

create table if not exists company (
    company_id uuid primary key default uuid_generate_v4(),
    tax_id varchar not null unique,
    name varchar not null
);

create table if not exists user_account (
    user_account_id uuid primary key default uuid_generate_v4(),
    username varchar not null, -- has to be unique inside companies, validated on service layer
    password varchar not null,
    is_active bool not null default true,
    company_id uuid not null references company,
    role_id uuid not null references role
);

create table if not exists person (
    person_id uuid primary key default uuid_generate_v4(),
    name varchar not null,
    last_name varchar not null,
    email varchar,
    birth_date timestamptz,
    identification_number numeric not null,
    phone_number numeric,
    is_active bool not null default true,
    user_account_id uuid references user_account,
    company_id uuid not null references company
);

create table if not exists doctor (
    doctor_id uuid primary key default uuid_generate_v4(),
    person_id uuid not null references person,
    company_id uuid not null references company
);

create table if not exists patient (
    patient_id uuid primary key default uuid_generate_v4(),
    person_id uuid references person,
    company_id uuid not null references company,
    is_active bool not null default true
);

create table if not exists dental_chart (
    dental_chart_id uuid primary key default uuid_generate_v4(),
    patient_id uuid references patient,
    data jsonb,
    company_id uuid not null references company
);

create table if not exists invoice (
    invoice_id uuid primary key default uuid_generate_v4(),
    status smallint not null default 0,
    client_id uuid not null references person,
    company_id uuid not null references company,
    total numeric not null default 0,
    lines jsonb
);

create table if not exists patient_preliminary_history (
    patient_preliminary_history_id uuid primary key default uuid_generate_v4(),
    history_number bigint generated always as identity,
    doctor_id uuid not null references doctor,
    patient_id uuid references patient,
    company_id uuid not null references company,
    dental_chart_id uuid references dental_chart,
    invoice_id uuid references invoice,
    audit_create timestamptz not null default now(),
    audit_update timestamptz not null default now()
);

create table if not exists patient_history (
    patient_history_id uuid primary key default uuid_generate_v4(),
    doctor_id uuid not null references doctor,
    patient_id uuid not null references patient,
    company_id uuid not null references company,
    dental_chart_id uuid not null references dental_chart,
    patient_preliminary_history_id uuid references patient_preliminary_history
);

create table if not exists product (
    product_id uuid primary key default uuid_generate_v4(),
    price numeric not null default 0,
    name varchar not null default '',
    company_id uuid not null references company
);

create table if not exists service (
    service_id uuid primary key default uuid_generate_v4(),
    price numeric not null default 0,
    max_discount numeric check ( max_discount >= 0 ),
    title varchar not null default '',
    description varchar not null default '',
    company_id uuid not null references company,
    teeth_status_id uuid references teeth_status
);

create table if not exists price_log (
    product_id uuid references product,
    service_id uuid references service,
    price numeric not null default 0,
    audit_create timestamptz not null default now(),
    company_id uuid not null references company
);

create table if not exists invoice (
    invoice_id uuid primary key default uuid_generate_v4(),
    status smallint not null default 0,
    client_id uuid references person,
    company_id uuid not null references company,
    total numeric not null default 0,
    lines jsonb
);

create table if not exists patient_invoice (
    patient_invoice_id uuid primary key default uuid_generate_v4(),
    invoice_id uuid references invoice,
    patient_id uuid references patient,
    company_id uuid not null references company
);

create table if not exists teeth_status (
    teeth_status_id uuid primary key default uuid_generate_v4(),
    name varchar not null default '',
    is_active bool not null default true,
    company_id uuid not null references company
);

create table if not exists payment (
    payment_id uuid primary key default uuid_generate_v4(),
    pay_amount numeric not null default 0,
    date_paid timestamptz not null default now(),
    invoice_id uuid references invoice,
    company_id uuid not null references company
);

create table if not exists document (
    document_id uuid primary key default uuid_generate_v4(),
    patient_id uuid references patient,
    filename varchar,
    hash varchar,
    download_url varchar,
    media_type varchar,
    audit_create timestamptz
);

create table if not exists db_config (
    initialized bool not null default false
);