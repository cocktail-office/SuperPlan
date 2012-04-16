---------------------------------------------------------------------------------
-- Script DML de mise a jour du user EDTSCOL de version 1.3.8 vers version 1.3.9
---------------------------------------------------------------------------------

UPDATE EDTSCOL.RESA_OBJET SET RO_ACCES = null WHERE RTO_KEY IN (
  SELECT RTO_KEY FROM EDTSCOL.RESA_TYPE_OBJET WHERE RTO_LIBELLE IN (
    SELECT TYPE_OBJET FROM RESERVATION.TYPE_OBJET_KIOSQUE
  )
);

--

insert into edtscol.db_version (db_version, db_date, db_comment)
values ('1.3.9', to_date('22/11/2010','dd/mm/yyyy'), '');

COMMIT;
