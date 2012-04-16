--
-- update de la 1.1.0 à la 1.3.0

--
ALTER TABLE EDTSCOL.PREF_USER ADD pas_mail_hors_semaine NUMBER;

-- version...
INSERT INTO EDTSCOL.DB_VERSION VALUES ('1.3.0', TO_DATE('14/11/2007', 'dd/mm/yyyy'), ' ');

--
COMMIT;
