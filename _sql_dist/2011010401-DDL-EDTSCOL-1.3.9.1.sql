
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
-- Numéro de version : 1.3.9.1
-- Pré-requis : base version 1.3.8.0 ou 1.3.9.0
-- Date de publication : 04/01/2011
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--
--

WHENEVER SQLERROR EXIT SQL.SQLCODE;

--

CREATE OR REPLACE FORCE VIEW edtscol.v_parcours_ap (fann_key,
                                                    fdip_code,
                                                    fspn_key,
                                                    mpar_key,
                                                    msem_key,
                                                    mec_key,
                                                    map_key,
                                                    mtec_code
                                                   )
AS
   SELECT DISTINCT ru.fann_key, s.fdip_code, p.fspn_key, rs.mpar_key,
                   ru.msem_key, ra.mec_key, ra.map_key, re.mtec_code
              FROM scolarite.scol_maquette_repartition_ue ru,
                   scolarite.scol_maquette_repartition_ec re,
                   scolarite.scol_maquette_repartition_ap ra,
                   scolarite.scol_maquette_repartition_sem rs,
                   scolarite.scol_maquette_parcours p,
                   scolarite.scol_maquette_semestre msem,
                   scolarite.scol_formation_specialisation s
             WHERE rs.mpar_key = p.mpar_key
               AND ru.msem_key = rs.msem_key
               AND re.mue_key = ru.mue_key
               AND ra.mec_key = re.mec_key
               AND s.fspn_key = p.fspn_key
               AND ru.fann_key = rs.fann_key
               AND re.fann_key = ru.fann_key
               AND ra.fann_key = re.fann_key
               AND msem.msem_key = rs.msem_key
               AND MOD (msem.msem_ordre, 2) = ra.mrap_semestre;

--

EXECUTE GRHUM.DROP_OBJECT('EDTSCOL', 'ECHANGER_PERSONNE_EDTSCOL', 'PROCEDURE');

--

declare
  cpt number;
  begin
    select count(*) into cpt from all_tab_columns where owner = 'EDTSCOL' and table_name = 'RESA_OBJET' and column_name = 'RO_ACCES' and nullable = 'N';
    if (cpt = 1) then
         execute immediate 'ALTER TABLE EDTSCOL.RESA_OBJET MODIFY(RO_ACCES NULL)';
    end if;
  end;
/

--

COMMIT;
