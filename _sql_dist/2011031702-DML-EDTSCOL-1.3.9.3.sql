
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
-- Numéro de version : 1.3.9.3
-- Pré-requis : base version 1.3.9.2
-- Date de publication : 17/03/2011
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--
--

WHENEVER SQLERROR EXIT ROLLBACK SQL.SQLCODE;

--

update edtscol.resa_objet set ro_reservable = 'O' where ro_acces is null;
update edtscol.resa_objet set ro_reservable = 'N' where ro_acces is not null;

--

insert into edtscol.db_version (db_version, db_date, db_comment)
values ('1.3.9.3', to_date('17/03/2011','dd/mm/yyyy'), '');

--

COMMIT;
