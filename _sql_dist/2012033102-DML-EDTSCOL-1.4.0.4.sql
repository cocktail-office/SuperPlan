
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
-- Numéro de version : 1.4.0.4
-- Pré-requis : base version 1.4.0.3
-- Date de publication : 31/03/2012
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--
--

--

insert into edtscol.type_motif_log values (edtscol.type_motif_log_seq.nextval, 'Déplacement de cours à la demande de l''enseignant', 'Modification');
insert into edtscol.type_motif_log values (edtscol.type_motif_log_seq.nextval, 'Déplacement de cours pour cause extérieure', 'Modification');
insert into edtscol.type_motif_log values (edtscol.type_motif_log_seq.nextval, 'Réduction du nombre de groupes', NULL);
insert into edtscol.type_motif_log values (edtscol.type_motif_log_seq.nextval, 'Annulation de cours', NULL);
insert into edtscol.type_motif_log values (edtscol.type_motif_log_seq.nextval, 'Erreur matérielle', NULL);

--

insert into edtscol.db_version (db_version, db_date, db_comment)
values ('1.4.0.4', to_date('31/03/2012','dd/mm/yyyy'), '');

--

COMMIT;
