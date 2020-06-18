use oasisdb;

start transaction;

insert into institution(`institution_id`, `name`) SELECT 0, 'NA' FROM dual WHERE not EXISTS (select institution_id,name from institution where institution.institution_id = 0);

commit;