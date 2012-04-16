
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
-- Numéro de version : 1.3.9.3
-- Pré-requis : base version 1.3.9.2
-- Date de publication : 17/03/2011
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--
--

WHENEVER SQLERROR EXIT SQL.SQLCODE;

--

ALTER TABLE EDTSCOL.RESA_OBJET ADD (RO_RESERVABLE VARCHAR2(1 BYTE));

ALTER TABLE EDTSCOL.RESA_OBJET ADD CONSTRAINT RESA_OBJET_C01 CHECK (RO_RESERVABLE in ('O', 'N'));

--

COMMIT;
