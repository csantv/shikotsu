set search_path = shikotsu, public;

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
    values ('0000000000', 'Odonto Expert') returning company_id into v_company_id;

    insert into user_account (username, password, company_id, role_id)
    values ('cvaler', v_password, v_company_id, v_doctor_role_id) returning user_account_id into v_user_doctor_id;

    insert into user_account (username, password, company_id, role_id)
    values ('lipenza', v_password, v_company_id, v_admin_role_id) returning user_account_id into v_user_admin_id;

    insert into person (name, last_name, identification_number, user_account_id, company_id)
    values ('Cristhian', 'Valer', 99999999, v_user_doctor_id, v_company_id) returning person_id into v_person_id;

    insert into doctor (person_id, company_id) values (v_person_id, v_company_id) returning doctor_id into v_doctor_id;

    insert into teeth_status (name, company_id) values
    ('APARATO ORTODONTICO FIJO', v_company_id),
    ('APARATO ORTODONTICO REMOVIBLE', v_company_id),
    ('CARIES', v_company_id),
    ('CORONA DEFINITIVA', v_company_id),
    ('CORONA TEMPORAL', v_company_id),
    ('DESGASTE OCLUSAL/INCISAL', v_company_id),
    ('DIASTEMA', v_company_id),
    ('DIENTE AUSENTE', v_company_id),
    ('DIENTE DISCROMICO', v_company_id),
    ('DIENTE ECTOPICO', v_company_id),
    ('DIENTE EN CLAVIJA', v_company_id),
    ('DIENTE EXTRUIDO', v_company_id),
    ('DIENTE INTRUIDO', v_company_id),
    ('EDENTULO TOTAL', v_company_id),
    ('FRACTURA', v_company_id),
    ('GEMINACION/FUSIÓN', v_company_id),
    ('GIROVERSION', v_company_id),
    ('IMPACTACIÓN', v_company_id),
    ('IMPLANTE', v_company_id),
    ('MACRODONCIA', v_company_id),
    ('MICRODONCIA', v_company_id),
    ('MIGRACION', v_company_id),
    ('MOVILIDAD', v_company_id),
    ('PROTESIS FIJA', v_company_id),
    ('PROTESIS REMOVIBLE', v_company_id),
    ('PROTESIS TOTAL', v_company_id),
    ('REMANENTE RADICULAR', v_company_id),
    ('RESTAURACIÓN', v_company_id),
    ('RESTAURACIÓN TEMPORAL', v_company_id),
    ('SEMI-IMPACTACIÓN', v_company_id),
    ('SUPERNUMERARIO', v_company_id),
    ('TRANSPOSICION', v_company_id),
    ('TRATAMIENTO PULPAR', v_company_id);

    -- done initializing
    insert into db_config (initialized) values (true);
end;
$$ language plpgsql;