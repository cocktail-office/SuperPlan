
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
-- Numéro de version : 1.4.0.4
-- Pré-requis : base version 1.4.0.3
-- Date de publication : 31/03/2012
-- Auteur(s) : Patrice DE MEYER
-- Licence : CeCILL version 2
--
-- A lancer sous : EDTSCOL
--

--

delete from edtscol.resa_type_location where tloc_code in ('q', 'n', 't', 'h', 'o', 'f');

update edtscol.resa_type_location set tloc_libelle = 'Réunion' where tloc_code = 'r';

CREATE OR REPLACE FORCE VIEW edtscol.type_location (tloc_code,
                                                    tloc_libelle,
                                                    tloc_ecrasable,
                                                    tloc_appli)
AS
   SELECT rtl.tloc_code, rtl.tloc_libelle, rtl.tloc_ecrasable, rtl.tloc_appli
     FROM edtscol.resa_type_location rtl
   UNION
   SELECT hc.mhco_code, hc.mhco_abreviation, 'N', 'SCOL'
     FROM scolarite.scol_maquette_horaire_code hc;

--

create table edtscol.log_reservation (
  LOG_KEY                       NUMBER NOT NULL,
  LOG_NO_INDIVIDU               NUMBER NOT NULL,
  LOG_DATE                      DATE NOT NULL,
  LOG_ACTION                    VARCHAR2(20) NOT NULL,
  LOG_MOTIF                     VARCHAR2(200),
  LOG_RESA_KEY              	NUMBER NOT NULL,
  LOG_OLD_RESA_COMMENTAIRE      VARCHAR2(255),
  LOG_OLD_NO_INDIVIDU_CLIENT    NUMBER NOT NULL,
  LOG_OLD_D_CREATION            DATE NOT NULL,
  LOG_OLD_D_MODIFICATION        DATE NOT NULL,
  LOG_OLD_TLOC_CODE             VARCHAR2(5) NOT NULL,
  CONSTRAINT LOG_PK PRIMARY KEY (
    LOG_KEY
  )    
);

ALTER TABLE edtscol.log_reservation ADD (
  CONSTRAINT LOG_REF_INDIVIDU 
 FOREIGN KEY (LOG_NO_INDIVIDU) 
 REFERENCES grhum.individu_ulr (NO_INDIVIDU)
 DEFERRABLE INITIALLY DEFERRED);

create sequence edtscol.log_reservation_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

create table edtscol.log_reservation_ap (
  LOG_AP_KEY        NUMBER NOT NULL,
  LOG_KEY           NUMBER NOT NULL,
  LOG_OLD_RESA_KEY  NUMBER NOT NULL,
  LOG_OLD_MAP_KEY   NUMBER NOT NULL,
  LOG_OLD_HCOMP_REC NUMBER DEFAULT 0 NOT NULL,
  LOG_OLD_GGRP_KEY  NUMBER,
  CONSTRAINT LOG_AP_PK PRIMARY KEY (
    LOG_AP_KEY
  )    
);

ALTER TABLE edtscol.log_reservation_ap ADD (
  CONSTRAINT LOG_AP_REF_LOG 
 FOREIGN KEY (LOG_KEY) 
 REFERENCES edtscol.log_reservation (LOG_KEY)
 DEFERRABLE INITIALLY DEFERRED);

create sequence EDTSCOL.LOG_RESERVATION_AP_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

create table edtscol.log_reservation_examen (
  LOG_EXAMEN_KEY    NUMBER NOT NULL,
  LOG_KEY           NUMBER NOT NULL,
  LOG_OLD_RESA_KEY  NUMBER NOT NULL,
  LOG_OLD_EENT_KEY  NUMBER NOT NULL,
  LOG_OLD_GGRP_KEY  NUMBER,
  CONSTRAINT LOG_EXAMEN_PK PRIMARY KEY (
    LOG_EXAMEN_KEY
  )    
);

ALTER TABLE edtscol.log_reservation_examen ADD (
  CONSTRAINT LOG_AP_REF_LOG 
 FOREIGN KEY (LOG_KEY) 
 REFERENCES edtscol.log_reservation (LOG_KEY)
 DEFERRABLE INITIALLY DEFERRED);

create sequence EDTSCOL.LOG_RESERVATION_EXAMEN_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

create table edtscol.log_reservation_objet (
  LOG_OBJET_KEY             NUMBER NOT NULL,
  LOG_KEY                   NUMBER NOT NULL,
  LOG_OLD_RO_KEY            NUMBER NOT NULL,
  LOG_OLD_RESA_KEY          NUMBER NOT NULL,
  LOG_OLD_RESA_ETAT         VARCHAR2(1) NOT NULL,
  LOG_OLD_NO_INDIVIDU_VAL   NUMBER,
  LOG_OLD_DATE_VALIDATION   DATE,
  LOG_OLD_MOTIF_ANNULATION  VARCHAR2(500),
  CONSTRAINT LOG_OBJET_PK PRIMARY KEY (
    LOG_OBJET_KEY
  )    
);

ALTER TABLE edtscol.log_reservation_objet ADD (
  CONSTRAINT LOG_OBJET_REF_LOG 
 FOREIGN KEY (LOG_KEY) 
 REFERENCES edtscol.log_reservation (LOG_KEY)
 DEFERRABLE INITIALLY DEFERRED);

create sequence EDTSCOL.LOG_RESERVATION_OBJET_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

create table edtscol.log_reservation_semestre (
  LOG_SEMESTRE_KEY  NUMBER NOT NULL,
  LOG_KEY           NUMBER NOT NULL,
  LOG_OLD_RESA_KEY  NUMBER NOT NULL,
  LOG_OLD_MSEM_KEY  NUMBER NOT NULL,
  LOG_OLD_GGRP_KEY  NUMBER,
  LOG_OLD_HCOMP_REC NUMBER NOT NULL,
  CONSTRAINT LOG_SEMESTRE_PK PRIMARY KEY (
    LOG_SEMESTRE_KEY
  )    
);

ALTER TABLE edtscol.log_reservation_semestre ADD (
  CONSTRAINT LOG_SEMESTRE_REF_LOG 
 FOREIGN KEY (LOG_KEY) 
 REFERENCES edtscol.log_reservation (LOG_KEY)
 DEFERRABLE INITIALLY DEFERRED);

create sequence EDTSCOL.LOG_RESERVATION_SEMESTRE_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

create table edtscol.log_reservation_salle (
  LOG_SALLE_KEY         NUMBER NOT NULL,
  LOG_KEY               NUMBER NOT NULL,
  LOG_OLD_RESA_KEY      NUMBER NOT NULL,
  LOG_OLD_SAL_NUMERO    NUMBER NOT NULL,
  LOG_OLD_RESA_SAL_ETAT VARCHAR2(1) DEFAULT 'N',
  LOG_OLD_RESA_SAL_DATE DATE,
  CONSTRAINT LOG_SALLE_PK PRIMARY KEY (
    LOG_SALLE_KEY
  )    
);

ALTER TABLE edtscol.log_reservation_salle ADD (
  CONSTRAINT LOG_SALLE_REF_LOG 
 FOREIGN KEY (LOG_KEY) 
 REFERENCES edtscol.log_reservation (LOG_KEY)
 DEFERRABLE INITIALLY DEFERRED);

create sequence EDTSCOL.LOG_RESERVATION_SALLE_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

create table edtscol.log_reservation_occupant (
  LOG_OCCUPANT_KEY      NUMBER NOT NULL,
  LOG_KEY               NUMBER NOT NULL,
  LOG_OLD_RESA_KEY      NUMBER NOT NULL,
  LOG_OLD_NO_INDIVIDU   NUMBER NOT NULL,
  LOG_OLD_HCOMP_REC     NUMBER DEFAULT 0 NOT NULL ,
  CONSTRAINT LOG_OCCUPANT_PK PRIMARY KEY (
    LOG_OCCUPANT_KEY
  )    
);

ALTER TABLE edtscol.log_reservation_occupant ADD (
  CONSTRAINT LOG_OCCUPANT_REF_LOG 
 FOREIGN KEY (LOG_KEY) 
 REFERENCES edtscol.log_reservation (LOG_KEY)
 DEFERRABLE INITIALLY DEFERRED);

create sequence EDTSCOL.LOG_RESERVATION_OCCUPANT_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

create table edtscol.log_reservation_period (
  LOG_PERIODICITE_KEY   NUMBER NOT NULL,
  LOG_KEY               NUMBER NOT NULL,
  LOG_OLD_RESA_KEY      NUMBER NOT NULL,
  LOG_OLD_DATE_DEB      DATE NOT NULL,
  LOG_OLD_DATE_FIN      DATE NOT NULL,
  LOG_OLD_HCOMP         NUMBER DEFAULT 0 NOT NULL,
  CONSTRAINT LOG_PERIODICITE_PK PRIMARY KEY (
    LOG_PERIODICITE_KEY
  )    
);

ALTER TABLE edtscol.log_reservation_period ADD (
  CONSTRAINT LOG_PERIODICITE_REF_LOG 
 FOREIGN KEY (LOG_KEY) 
 REFERENCES edtscol.log_reservation (LOG_KEY)
 DEFERRABLE INITIALLY DEFERRED);

create sequence EDTSCOL.LOG_RESERVATION_PERIOD_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

create table edtscol.type_motif_log (
  TML_KEY       NUMBER NOT NULL,
  TML_MOTIF     VARCHAR2(100) NOT NULL,
  TML_ACTION    VARCHAR2(20),
  CONSTRAINT TML_PK PRIMARY KEY (
    TML_KEY
  )    
);

COMMENT ON COLUMN edtscol.type_motif_log.tml_action IS 'A quelle action est réservé ce motif ? Valeurs possibles: ''Modification'', ''Suppression'' ou NULL (utilisé pour n''importe quel motif)';

create sequence edtscol.type_motif_log_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE;

--

COMMIT;
