--------------------------------------------------------------------------------
-- Script de mise à jour de la base EDTSCOL de version 1.3.6 vers version 1.3.7
-- A exécuter à partir de GRHUM ou EDTSCOL
--------------------------------------------------------------------------------

CREATE TABLE EDTSCOL.RESA_OBJET_RESERVE
(
  ROR_KEY INTEGER NOT NULL,
  RO_KEY INTEGER NOT NULL,
  C_STRUCTURE VARCHAR2(10) NOT NULL,
  DATE_DEB DATE NOT NULL,
  DATE_FIN DATE
, CONSTRAINT RESA_OBJET_RESERVE_PK PRIMARY KEY
  (
    ROR_KEY
  )
);


CREATE SEQUENCE EDTSCOL.RESA_OBJET_RESERVE_SEQ INCREMENT BY 1 START WITH 1 NOCACHE ;

INSERT INTO EDTSCOL.DB_VERSION (DB_VERSION,DB_DATE,DB_COMMENT) 
values ('1.3.7',TO_DATE('28/11/2008','dd/mm/yyyy'),'gestion des utilisateurs d''objets');

COMMIT;

	