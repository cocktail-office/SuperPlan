alter table EDTSCOL.PREF_USER add (SEND_MAIL_OCCUPANTS VARCHAR2(1));

update edtscol.pref_user set SEND_MAIL_OCCUPANTS = 'O' where SEND_MAIL_OCCUPANTS is null;

alter table EDTSCOL.PREF_USER add (SEND_MAIL_DEPOSITAIRES VARCHAR2(1));

update edtscol.pref_user set SEND_MAIL_DEPOSITAIRES = 'O' where SEND_MAIL_DEPOSITAIRES is null;

alter table edtscol.pref_user add (selection_groupe_multiple varchar2(1));

update edtscol.pref_user set selection_groupe_multiple = 'N' where selection_groupe_multiple is null;

alter  table edtscol.pref_user modify (selection_groupe_multiple not null);

ALTER TABLE edtscol.pref_user MODIFY (SEND_MAIL_OCCUPANTS not null);

ALTER TABLE edtscol.pref_user MODIFY (SEND_MAIL_DEPOSITAIRES not null);
-- version...
insert into edtscol.db_version (db_comment,db_version,db_date) values ('parametrage envoi de mail','1.3.3',to_date('25/03/2008','dd/mm/yyyy'));
