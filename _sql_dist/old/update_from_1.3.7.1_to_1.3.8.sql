--	------------------------------------------------------------------------------
-- Script de mise à jour de la base EDTSCOL de version 1.3.7.1 vers version 1.3.8
-- A exécuter à partir de GRHUM uniquement.
--------------------------------------------------------------------------------

CREATE TABLE EDTSCOL.RESERVATION_SEMESTRE
(
  RESA_SEM_KEY NUMBER NOT NULL,
  RESA_KEY NUMBER NOT NULL,
  MSEM_KEY NUMBER NOT NULL,
  GGRP_KEY NUMBER,
  HCOMP_REC NUMBER NOT NULL
, CONSTRAINT RESERVATION_SEMESTRE_PK PRIMARY KEY
  (
    RESA_SEM_KEY
  )
  ENABLE
);

CREATE SEQUENCE EDTSCOL.RESERVATION_SEMESTRE_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;


grant references on scolarite.scol_maquette_semestre to edtscol;

alter table edtscol.reservation_semestre
add CONSTRAINT fk_msem
  FOREIGN KEY (msem_key)
  REFERENCES scolarite.scol_maquette_semestre(msem_key);


alter table edtscol.resa_type_location modify tloc_code varchar2(3);

insert into edtscol.resa_type_location (tloc_code,tloc_libelle,tloc_appli,tloc_ecrasable)
values ('SEM','Promo etudiants','SUPERPLAN','N');


CREATE OR REPLACE FORCE VIEW "EDTSCOL"."V_PARCOURS_AP" ("FANN_KEY", "FDIP_CODE", "FSPN_KEY", "MPAR_KEY", "MSEM_KEY", "MEC_KEY", "MAP_KEY") AS 
SELECT distinct ru.fann_key,s.fdip_code,p.fspn_key,rs.mpar_key,ru.msem_key,ra.mec_key,ra.map_key 
FROM scolarite.scol_maquette_repartition_ue ru, scolarite.scol_maquette_repartition_ec re, 
	 scolarite.scol_maquette_repartition_ap ra,scolarite.scol_maquette_repartition_sem rs, 
	 scolarite.scol_maquette_parcours p , 
	 scolarite.scol_maquette_semestre msem,scolarite.scol_formation_specialisation s 
WHERE 
	 rs.mpar_key = p.mpar_key AND 
	 ru.msem_key = rs.msem_key AND 
     re.mue_key = ru.mue_key AND 
	 ra.mec_key = re.mec_key AND 
	 s.fspn_key = p.fspn_key AND 
	 ru.fann_key = rs.fann_key AND 
	 re.fann_key = ru.fann_key AND 
	 ra.fann_key = re.fann_key AND 
	 msem.msem_key = rs.msem_key AND 
	 mod(msem.msem_ordre,2) = ra.mrap_semestre;



CREATE TABLE EDTSCOL.REPART_LOT_INDIVIDU
(
  LOT_KEY NUMBER NOT NULL,
  NO_INDIVIDU NUMBER NOT NULL
, CONSTRAINT REPART_LOT_INDIVIDU_PK PRIMARY KEY
  (
    LOT_KEY,
    NO_INDIVIDU
  )
  ENABLE
)
;



INSERT INTO EDTSCOL.DB_VERSION (DB_VERSION,DB_DATE,DB_COMMENT) 
values ('1.3.8',TO_DATE('16/12/2009','dd/mm/yyyy'),'reservation de parcours-semestres et lots vers individus');

commit;
