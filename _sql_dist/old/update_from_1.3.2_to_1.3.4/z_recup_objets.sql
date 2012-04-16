--------------------------------------------------------------------------------------------
-- Script à lancer à partir du user EDTSCOL 											  	
-- Permet de récupérer de manière imparfaite les objets saisis dans la base RESERVATION	  	
--------------------------------------------------------------------------------------------

insert into resa_famille_objet(rfo_key,rfo_libelle) 
select resa_famille_objet_seq.nextval,cob_libelle from categ_objet;

insert into resa_type_objet(rto_key,rto_libelle,rfo_key)
select resa_type_objet_seq.nextval,tob_libelle,2 from type_objet;

insert into resa_objet (ro_key,rto_key,ro_libelle1,ro_libelle2,ro_acces)
select resa_objet_seq.nextval,1,obje_libelle1,obje_libelle1,1 from reservation.resa_objet_harp;

declare
  obj_ordre number;
  new_rto_key number;
  cmp numeric;
  cursor c_objets is select * from reservation.resa_objet_harp;
begin
  
  for row_objet in c_objets loop
    
    select resa_objet_seq.nextval into obj_ordre from dual;
  
    select count(*) into cmp from resa_type_objet where rto_libelle = row_objet.type_code;
    
    if cmp=1 then
      select rto_key into new_rto_key from resa_type_objet where rto_libelle = row_objet.type_code;
    else
      new_rto_key := 1;
    end if;
    
    insert into resa_objet (ro_key,rto_key,ro_libelle1,ro_libelle2,ro_acces)
    values (obj_ordre,new_rto_key,row_objet.obje_libelle1,row_objet.obje_libelle1,1);
 
  end loop;

end;
