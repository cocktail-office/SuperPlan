
SET DEFINE OFF;

-- 
-- __________________________________________________________
--  /!\ ATTENTION /!\ fichier encodé en UTF-8 sans BOM
-- (il peut contenir des é è ç à î ê ô ...)
-- __________________________________________________________
--
--

--
-- Fichier : 1
-- Type : DDL
-- Schéma : EDTSCOL
-- Numéro de version : 1.4.0.3
-- Pré-requis : base version 1.4.0.2
-- Date de publication : 16/01/2012
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--

--

ALTER TABLE EDTSCOL.PREF_USER
 ADD (DEFAULT_TYPE_RESA VARCHAR2(20 BYTE));
 
UPDATE EDTSCOL.PREF_USER SET DEFAULT_TYPE_RESA = 'Enseignement';

ALTER TABLE EDTSCOL.PREF_USER
MODIFY(DEFAULT_TYPE_RESA NOT NULL);


--

COMMIT;
