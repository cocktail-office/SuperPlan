
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
-- Numéro de version : 1.3.9.2
-- Pré-requis : base version 1.3.9.1
-- Date de publication : 21/01/2011
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--
--

WHENEVER SQLERROR EXIT ROLLBACK SQL.SQLCODE;

--

insert into edtscol.resa_type_location values ('s', 'Réunion de service', 'SUPERPLAN', 'N');
insert into edtscol.resa_type_location values ('p', 'Réunion privée', 'SUPERPLAN', 'N');
update edtscol.resa_type_location set tloc_appli = 'SUPERPLAN' where tloc_appli = 'SALLE';

--

insert into edtscol.db_version (db_version, db_date, db_comment)
values ('1.3.9.2', to_date('21/01/2011','dd/mm/yyyy'), '');

--

COMMIT;
