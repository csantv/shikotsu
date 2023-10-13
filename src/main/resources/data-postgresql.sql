do $$
declare
    initialized bool := false;
    v_user_doctor_id uuid;
    v_user_admin_id uuid;
    v_company_id uuid;
    v_doctor_role_id uuid;
    v_patient_role_id uuid;
    v_admin_role_id uuid;
    v_password varchar := '$argon2id$v=19$m=16384,t=2,p=1$ciUMzi3mQfiBqr4XT5ccFA$t1TZKpxvZi5ZaNbXz+0plBP5RobH0LLnnEPYGE8v58k'; -- 12345
    v_person_id uuid;
    v_doctor_id uuid;
begin
    select db.initialized into initialized from db_config db limit 1;

    if initialized then
        raise notice 'Database already initialized';
        return;
    end if;

    insert into role (name) values ('DOCTOR') returning role_id into v_doctor_role_id;
    insert into role (name) values ('PATIENT') returning role_id into v_patient_role_id;
    insert into role (name) values ('ADMIN') returning role_id into v_admin_role_id;

    insert into company (tax_id, name)
    values ('0000000000', 'Dental Expert') returning company_id into v_company_id;

    insert into user_account (username, password, company_id)
    values ('cvaler', v_password, v_company_id) returning user_account_id into v_user_doctor_id;

    insert into user_account (username, password, company_id)
    values ('lipenza', v_password, v_company_id) returning user_account_id into v_user_admin_id;

    insert into user_account_role (role_id, user_account_id)
    values (v_doctor_role_id, v_user_doctor_id), (v_admin_role_id, v_user_admin_id);

    insert into person (name, last_name, user_account_id, company_id)
    values ('Cristhian', 'Valer', v_user_doctor_id, v_company_id) returning person_id into v_person_id;

    insert into doctor (person_id, company_id) values (v_person_id, v_company_id) returning doctor_id into v_doctor_id;

    -- done initializing
    insert into db_config (initialized) values (true);
end;
$$ language plpgsql;