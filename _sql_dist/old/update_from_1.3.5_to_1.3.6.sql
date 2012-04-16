--------------------------------------------------------------------------------
- Script de mise à jour de la base EDTSCOL de version 1.3.5 vers version 1.3.6
- A exécuter à partir de GRHUM ou EDTSCOL
--------------------------------------------------------------------------------

ALTER TABLE "EDTSCOL"."PREF_USER" add(USE_TOOLTIP_PLANNING VARCHAR2(1));

UPDATE EDTSCOL.PREF_USER SET USE_TOOLTIP_PLANNING = 'O' WHERE USE_TOOLTIP_PLANNING IS NULL;

ALTER TABLE EDTSCOL.PREF_USER MODIFY (USE_TOOLTIP_PLANNING NOT NULL);

INSERT INTO EDTSCOL.DB_VERSION (DB_VERSION,DB_DATE,DB_COMMENT) 
values ('1.3.6',TO_DATE('28/11/2008','dd/mm/yyyy'),'préference tooltip sur le planning');

COMMIT;