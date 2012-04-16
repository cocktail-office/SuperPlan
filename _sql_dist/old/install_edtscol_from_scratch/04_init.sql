-- edtscol
-- install from scratch
-- initialisations apr�s l'import des users edtscol et reservation
-- EXECUTER DEPUIS GRHUM !!!!

--
BEGIN
dbms_utility.compile_schema('EDTSCOL');
END;

BEGIN
dbms_utility.compile_schema('RESERVATION');
END;

--
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('.', 'Libre', 'EDT', 'O');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('I', 'Travail IATOSS', 'EDT', 'O');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('A', 'Absent', 'EDT', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('E', 'Examen', 'SALLE', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('f', 'Formation', 'SALLE', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('t', 'Travaux', 'SALLE', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('n', 'Entretien', 'SALLE', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('r', 'Reunion', 'SALLE', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('o', 'Option de resa', 'SALLE', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('h', 'Oral', 'SALLE', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('q', 'Indisponible', 'SALLE', 'N');
INSERT INTO EDTSCOL.RESA_TYPE_LOCATION ( TLOC_CODE, TLOC_LIBELLE, TLOC_APPLI,
TLOC_ECRASABLE ) VALUES ('b', 'Blocage', 'SALLE', 'N');

--
INSERT INTO EDTSCOL.TYPE_OBJET_SALLE ( TYPE_CODE, RESA_EDT ) VALUES ( 
'VIDEO PROJECTEUR', 0);
INSERT INTO EDTSCOL.TYPE_OBJET_SALLE ( TYPE_CODE, RESA_EDT ) VALUES ( 
'VEHICULES', 1);
INSERT INTO EDTSCOL.TYPE_OBJET_SALLE ( TYPE_CODE, RESA_EDT ) VALUES ( 
'TABLETTE VIDEO', 1);
INSERT INTO EDTSCOL.TYPE_OBJET_SALLE ( TYPE_CODE, RESA_EDT ) VALUES ( 
'GENERATEUR DIAPOS', 1);
INSERT INTO EDTSCOL.TYPE_OBJET_SALLE ( TYPE_CODE, RESA_EDT ) VALUES ( 
'APPAREILS CCA', 1);
INSERT INTO EDTSCOL.TYPE_OBJET_SALLE ( TYPE_CODE, RESA_EDT ) VALUES ( 
'MATERIEL VIDEO', 0);

--
insert into edtscol.type_ressource values(1,'PERSONNE');
insert into edtscol.type_ressource values(2,'SALLE');
insert into edtscol.type_ressource values(3,'ENSEIGNEMENT');
insert into edtscol.type_ressource values(4,'OBJET');

--
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
1, 'Banalisee');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
2, 'Bureau');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
3, 'Travaux Pratiques');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
4, 'Amphitheatre');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
5, 'Langues Vivantes');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
6, 'Laboratoire');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
7, 'Preparation');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
8, 'Salle de reunion');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
9, 'Local Technique');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
10, 'Local de Franchise');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
11, 'Archives');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
12, 'Stockage');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
13, 'Circulation');
INSERT INTO EDTSCOL.TYPE_SALLE ( TSAL_NUMERO, TSAL_LIBELLE ) VALUES ( 
14, 'Non Affectee');

--
INSERT INTO RESERVATION.RESA_OBJET_DROITS ( C_DROITS, LIBELLE ) VALUES ( 
1, 'PUBLIC');
INSERT INTO RESERVATION.RESA_OBJET_DROITS ( C_DROITS, LIBELLE ) VALUES ( 
2, 'PRIVE');
INSERT INTO RESERVATION.RESA_OBJET_DROITS ( C_DROITS, LIBELLE ) VALUES ( 
3, 'MASQUE');

-- version...
INSERT INTO EDTSCOL.DB_VERSION VALUES ('1.3.1', TO_DATE('31/01/2008', 'dd/mm/yyyy'), ' ');

COMMIT;
