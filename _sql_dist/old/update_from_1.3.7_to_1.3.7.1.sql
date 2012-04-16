--	------------------------------------------------------------------------------
-- Script de mise à jour de la base EDTSCOL de version 1.3.7 vers version 1.3.7.1
-- A exécuter à partir de GRHUM ou EDTSCOL
-- -------------------------------------------------------------------------------

alter table edtscol.pref_scol add fann_key number;

update edtscol.pref_scol set fann_key = 2009 where fann_key is null;

alter table edtscol.pref_scol modify fann_key not null;

insert into db_version (db_version,db_date,db_comment) values ('1.3.7.1',to_date('09/09/2009','dd/mm/yyyy'),'Préférence diplôme par année universitaire');

commit;
