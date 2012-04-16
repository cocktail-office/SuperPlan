
SET DEFINE OFF;

--
-- 
-- __________________________________________________________
--  /!\ ATTENTION /!\ fichier encodé en UTF-8 sans BOM
-- (il peut contenir des é è ç à î ê ô ...)
-- __________________________________________________________
--
--

--
--
-- Fichier : 2
-- Type : DML
-- Schéma : EDTSCOL
-- Numéro de version : 1.3.9.1
-- Pré-requis : base version 1.3.8.0 ou 1.3.9.0
-- Date de publication : 04/01/2011
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--
--

WHENEVER SQLERROR EXIT ROLLBACK SQL.SQLCODE;

--

UPDATE EDTSCOL.RESA_OBJET SET RO_ACCES = null WHERE RTO_KEY NOT IN (
  SELECT RTO_KEY FROM EDTSCOL.RESA_TYPE_OBJET WHERE RTO_LIBELLE IN (
    SELECT TYPE_OBJET FROM RESERVATION.TYPE_OBJET_KIOSQUE
  )
);

--

insert into edtscol.db_version (db_version, db_date, db_comment)
values ('1.3.9.1', to_date('04/01/2011','dd/mm/yyyy'), '');

--

COMMIT;
