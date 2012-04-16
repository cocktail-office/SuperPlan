--
-- update de la 1.3.0 à la 1.3.1
--
-- <<< A LANCER DEPUIS GRHUM !!! >>>
--

--
CREATE OR REPLACE FORCE VIEW "GRHUM"."V_TREE_SALLES" ("C_STRUCTURE", "C_STRUCTURE_PERE", "LL_STRUCTURE", "PERS_ID","NIVEAU") 
AS
select distinct local.c_Local ,'0',appellation,1,0
from GRHUM.local,GRHUM.salles 
where local.c_local=salles.c_local 
and salles.sal_reservable='O'
union
select distinct substr(t2.tsal_libelle,1,20) || c_local,c_local,tsal_libelle,0,1
from GRHUM.salles t1,type_salle t2 
where t1.tsal_numero=t2.tsal_numero 
and t1.sal_reservable='O';
 
-- version...
insert into edtscol.db_version (db_comment,db_version,db_date) values ('gestion et recherche de salles','1.3.2',to_date('06/03/2008','dd/mm/yyyy'));
