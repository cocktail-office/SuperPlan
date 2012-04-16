--
-- update de la 1.3.0 à la 1.3.1
--
-- <<< A LANCER DEPUIS GRHUM !!! >>>
--

--
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_ANNEE_CIVILE';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_ANNEE_DEBUT';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_ANNEE_FIN';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_DISPLAY_BATIMENT';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_JOURS_FERIES';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_MEN_CODE';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_PANEL_PERIODICITE';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_PERSONNE_REUNION';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_PRINT_AND_EXPORT';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_PUBLICATION';
DELETE FROM GRHUM.GRHUM_PARAMETRES WHERE PARAM_KEY = 'EDT_TIME_ZONE';

--
drop table edtscol.type_ressource;

CREATE TABLE edtscol.TYPE_RESSOURCE
(
  TYPE_ORDRE INTEGER NOT NULL,
  TYPE_CODE VARCHAR2(20) NOT NULL
, CONSTRAINT TYPE_RESSOURCE_PK PRIMARY KEY
  (
    TYPE_ORDRE
  )
);

insert into edtscol.type_ressource values(1,'PERSONNE');
insert into edtscol.type_ressource values(2,'SALLE');
insert into edtscol.type_ressource values(3,'ENSEIGNEMENT');
insert into edtscol.type_ressource values(4,'OBJET');

--
update edtscol.pref_user set default_planning = 1 where default_planning is null;

ALTER TABLE edtscol.PREF_USER 
MODIFY ("DEFAULT_PLANNING" NOT NULL);

alter table edtscol.pref_user drop column use_treeview;
alter table edtscol.pref_user add color_ec varchar2(1);
update edtscol.pref_user set color_ec = 'N' where color_ec is null;
alter table edtscol.pref_user modify color_ec not null;

--
CREATE TABLE edtscol.RESA_COULEUR_EC
(
  MEC_KEY INTEGER NOT NULL,
  FANN_KEY INTEGER NOT NULL,
  COLOR_CODE VARCHAR2(10) NOT NULL
, CONSTRAINT RESA_COULEUR_EC_PK PRIMARY KEY
  (
    MEC_KEY,
    FANN_KEY
  )
);

--
create view edtscol.V_MAQUETTE_AP
as
select ma.fann_key,ma.map_groupe_prevu,ma.map_key,ma.map_libelle,ma.map_seuil,ma.map_valeur,ma.mhco_code,ma.mhmo_code,ce.color_code
from scolarite.scol_maquette_repartition_ap mra,scolarite.scol_maquette_ap ma,edtscol.resa_couleur_ec ce
where mra.map_key = ma.map_key
and ce.mec_key(+) = mra.mec_key
and ce.fann_key(+) = mra.fann_key;

-- version...
INSERT INTO EDTSCOL.DB_VERSION VALUES ('1.3.1', TO_DATE('31/01/2008', 'dd/mm/yyyy'), ' ');

--
COMMIT;
