
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
-- Fichier : 1
-- Type : DDL
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

WHENEVER SQLERROR EXIT SQL.SQLCODE;

--

ALTER TABLE EDTSCOL.PREF_USER DROP COLUMN DEFAULT_PLANNING;

ALTER TABLE EDTSCOL.PREF_USER ADD (DEFAULT_VISIBILITE VARCHAR2(10 BYTE));

--

COMMIT;
