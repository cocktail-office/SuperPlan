CREATE SEQUENCE LOT_SALLE_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE ;

CREATE OR REPLACE FORCE VIEW "EDTSCOL"."V_OCCUPATION_SALLE" ("DATE_DEB", "DATE_FIN", "LIBELLE", "SAL_PORTE", "LOT_KEY") AS 
select p.date_deb,p.date_fin,sma.map_libelle libelle,s.sal_porte,rls.lot_key
from periodicite p, resa_salles rs, repart_lot_salle rls,grhum.salles s, reservation r,reservation_ap ra,scolarite.scol_maquette_ap sma
where rs.sal_numero = rls.sal_numero
and   s.sal_numero = rls.sal_numero
and   r.resa_key = rs.resa_key
and   p.resa_key = rs.resa_key
and   ra.resa_key = r.resa_key
and   sma.map_key = ra.map_key
and   r.tloc_code in ('CM','TD','TP','TA','ST','EM')
union all
select p.date_deb,p.date_fin,r.resa_commentaire libelle,s.sal_porte,rls.lot_key
from periodicite p, resa_salles rs,repart_lot_salle rls,grhum.salles s, reservation r
where rs.sal_numero = rls.sal_numero
and   s.sal_numero = rls.sal_numero
and   r.resa_key = rs.resa_key
and   p.resa_key = rs.resa_key
and   r.tloc_code in ('r','b','q','f');
 
-- version...
insert into edtscol.db_version (db_comment,db_version,db_date) values ('gestion lots de salles','1.3.5',to_date('01/07/2008','dd/mm/yyyy'));
