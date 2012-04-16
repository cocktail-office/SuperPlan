---------------------------------------------------------------------------------
-- Script DDL de mise a jour du user EDTSCOL de version 1.3.8 vers version 1.3.9
---------------------------------------------------------------------------------

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

ALTER TABLE EDTSCOL.RESA_OBJET MODIFY(RO_ACCES NULL);

--

COMMIT;
