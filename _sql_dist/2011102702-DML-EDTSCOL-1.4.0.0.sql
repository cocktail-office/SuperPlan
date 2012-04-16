
SET DEFINE OFF;

-- 
-- __________________________________________________________
--  /!\ ATTENTION /!\ fichier encodé en UTF-8 sans BOM
-- (il peut contenir des é è ç à î ê ô ...)
-- __________________________________________________________
--
--

--
-- Fichier : 2
-- Type : DML
-- Schéma : EDTSCOL
-- Numéro de version : 1.4.0.0
-- Pré-requis : base version 1.3.9.3 ou 1.3.9.4
-- Date de publication : 27/10/2011
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL ou GRHUM
--
--

insert into edtscol.db_version (db_version, db_date, db_comment)
values ('1.4.0.0', to_date('27/10/2011','dd/mm/yyyy'), '');

--

COMMIT;
