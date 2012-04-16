
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
-- Numéro de version : 1.4.0.2
-- Pré-requis : base version 1.4.0.0 ou 1.4.0.1
-- Date de publication : 20/12/2011
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--
--

--

insert into edtscol.contrainte_semaine
select cis_key, fann_key, cis_date, cis_no_semaine, no_individu
from edtscol.ctrl_individu_semaines;

rename ctrl_individu_semaines_seq to contrainte_semaine_seq;


insert into edtscol.contrainte_jour
select cij_key, cis_key, cij_date, cij_no_jour
from edtscol.ctrl_individu_jours;

rename ctrl_individu_jours_seq to contrainte_jour_seq;


insert into edtscol.contrainte_heure
select cih_key, cij_key, cih_heure_debut, cih_heure_fin
from edtscol.ctrl_individu_heures;

rename ctrl_individu_heures_seq to contrainte_heure_seq;

--

insert into edtscol.db_version (db_version, db_date, db_comment)
values ('1.4.0.2', to_date('20/12/2011','dd/mm/yyyy'), '');

--

COMMIT;
