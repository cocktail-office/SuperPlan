----------------------------------------------
-- Script à lancer à partir du user EDTSCOL --
----------------------------------------------


drop table occupant_backup cascade constraints;

drop table periodicite_backup cascade constraints;

drop table resa_examen_backup cascade constraints;

drop table resa_salle_objet_backup cascade constraints;

drop table resa_salles_backup cascade constraints;

drop table reservation_ap_backup cascade constraints;

drop table salle_souhaitee_backup cascade constraints;

drop table reservation_backup cascade constraints;


update pref_user set selection_groupe_multiple = 'N' where selection_groupe_multiple is null;

CREATE TABLE RESA_FAMILLE_OBJET
(
  RFO_KEY INTEGER NOT NULL,
  RFO_LIBELLE VARCHAR2(120) NOT NULL,
  RFO_COMMENTAIRE VARCHAR2(400)
, CONSTRAINT RESA_FAMILLE_OBJET_PK PRIMARY KEY
  (
    RFO_KEY
  )	
);

CREATE TABLE RESA_TYPE_OBJET
(
  RTO_KEY NUMBER NOT NULL,
  RTO_LIBELLE VARCHAR2(120) NOT NULL,
  RTO_COMMENTAIRE VARCHAR2(400),
  RFO_KEY NUMBER NOT NULL
, CONSTRAINT RESA_TYPE_OBJET_PK PRIMARY KEY
  (
    RTO_KEY
  )
);

CREATE TABLE RESA_OBJET
(
  RO_KEY NUMBER NOT NULL,
  RTO_KEY NUMBER NOT NULL,
  RO_LIBELLE1 VARCHAR2(200) NOT NULL,
  RO_LIBELLE2 VARCHAR2(4000),
  RO_ACCES VARCHAR2(4000) NOT NULL,
  SAL_NUMERO NUMBER
, CONSTRAINT TABLE1_PK PRIMARY KEY
  (
    RO_KEY
  )
);


CREATE TABLE "EDTSCOL"."RESERVATION_OBJET" 
   (	"RO_KEY" NUMBER NOT NULL ENABLE, 
	"RESA_KEY" NUMBER NOT NULL ENABLE, 
	"RESA_ETAT" VARCHAR2(1 BYTE) NOT NULL ENABLE, 
	"NO_INDIVIDU_VAL" NUMBER, 
	"RESA_OBJET_KEY" NUMBER NOT NULL ENABLE, 
	"DATE_VALIDATION" DATE, 
	 CONSTRAINT "RESA_OBJET_PK" PRIMARY KEY ("RESA_OBJET_KEY"), 
	 CONSTRAINT "FK_RESA_OBJET" FOREIGN KEY ("RO_KEY")
	  REFERENCES "EDTSCOL"."RESA_OBJET" ("RO_KEY") ENABLE
   );


create view V_TREE_OBJET (NIVEAU,CLE_PARENT,CLE,LIBELLE)
as
select 1,0,rfo_key,rfo_libelle from resa_famille_objet
union
select 2,rfo_key,rto_key,rto_libelle from resa_type_objet
union
select 3,rto_key,ro_key,ro_libelle1 from resa_objet;



CREATE TABLE RESA_OBJET_DEPOSITAIRE
(
  ROD_KEY INTEGER NOT NULL,
  RO_KEY INTEGER NOT NULL,
  C_STRUCTURE VARCHAR2(12) NOT NULL,
  DATE_DEB DATE NOT NULL,
  DATE_FIN DATE
, CONSTRAINT RESA_OBJET_DEPOSITAIRE_PK PRIMARY KEY
  (
    ROD_KEY
  )
);

CREATE SEQUENCE RESA_OBJET_DEPOSITAIRE_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE ;

CREATE SEQUENCE RESA_FAMILLE_OBJET_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE ;

CREATE SEQUENCE RESA_OBJET_DEPOSITAIRE_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE ;

CREATE SEQUENCE RESA_TYPE_OBJET_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE ;

CREATE SEQUENCE RESA_OBJET_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE ;

CREATE SEQUENCE RESERVATION_OBJET_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE ;

-- P = Pré-réservé, R = Reservé, A = Annulé
alter table reservation_objet add constraint chk_resa_etat check (resa_etat in ('P','R','A') );

CREATE TABLE LOT_SALLE
(
  LOT_KEY NUMBER NOT NULL,
  LOT_LIBELLE VARCHAR2(150) NOT NULL,
  NO_INDIVIDU NUMBER NOT NULL
, CONSTRAINT RESA_LOT_SALLE_PK PRIMARY KEY
  (
    LOT_KEY
  )
)
;


CREATE TABLE REPART_LOT_SALLE
(
  LOT_KEY NUMBER NOT NULL,
  SAL_NUMERO NUMBER NOT NULL
, CONSTRAINT REPART_LOT_SALLE_PK PRIMARY KEY
  (
    LOT_KEY,
    SAL_NUMERO
  )
)
;


alter table "RESA_OBJET" add constraint FK_TYPE_OBJET foreign key("RTO_KEY") references "RESA_TYPE_OBJET"("RTO_KEY");

alter table "RESA_TYPE_OBJET" add constraint FK_FAMILLE_OBJET foreign key("RFO_KEY") references "RESA_FAMILLE_OBJET"("RFO_KEY");

alter table "RESERVATION_OBJET" add constraint FK_RESA_OBJET foreign key("RO_KEY") references "RESA_OBJET"("RO_KEY");

alter table "RESA_OBJET_DEPOSITAIRE" add constraint FK_DEPOS_RESA_OBJET foreign key("RO_KEY") references "RESA_OBJET"("RO_KEY");

insert into db_version (db_version,db_date,db_comment) values ('1.3.4',to_date('03/06/2008','dd/mm/yyyy'),'gestion des objets et materiels');





