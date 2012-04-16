--------------------------------------------------------------------------------
-- Script de mise à jour de la base EDTSCOL de version 1.3.6 vers version 1.3.7
-- A exécuter à partir de EDTSCOL
-- nouveauté pdm : une clé unique pour les 3 tables d'objets...
--------------------------------------------------------------------------------

drop sequence EDTSCOL.RESA_FAMILLE_OBJET_SEQ;

drop sequence EDTSCOL.RESA_TYPE_OBJET_SEQ;

drop sequence EDTSCOL.RESA_OBJET_SEQ;


CREATE SEQUENCE EDTSCOL.RESA_ARBRE_OBJETS_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE ;

CREATE SYNONYM EDTSCOL.RESA_FAMILLE_OBJET_SEQ FOR EDTSCOL.RESA_ARBRE_OBJETS_SEQ;

CREATE SYNONYM EDTSCOL.RESA_TYPE_OBJET_SEQ FOR EDTSCOL.RESA_ARBRE_OBJETS_SEQ;

CREATE SYNONYM EDTSCOL.RESA_OBJET_SEQ FOR EDTSCOL.RESA_ARBRE_OBJETS_SEQ;


ALTER TABLE EDTSCOL.RESA_TYPE_OBJET DISABLE CONSTRAINT FK_FAMILLE_OBJET;

ALTER TABLE EDTSCOL.RESA_OBJET DISABLE CONSTRAINT FK_TYPE_OBJET;

ALTER TABLE EDTSCOL.RESERVATION_OBJET DISABLE CONSTRAINT FK_RESA_OBJET;

ALTER TABLE EDTSCOL.RESA_OBJET_DEPOSITAIRE DISABLE CONSTRAINT FK_DEPOS_RESA_OBJET;

ALTER TABLE EDTSCOL.RESA_FAMILLE_OBJET DISABLE CONSTRAINT RESA_FAMILLE_OBJET_PK;

ALTER TABLE EDTSCOL.RESA_TYPE_OBJET DISABLE CONSTRAINT RESA_TYPE_OBJET_PK;

ALTER TABLE EDTSCOL.RESA_OBJET DISABLE CONSTRAINT TABLE1_PK;


declare
  my_rfo_key number;
  my_rto_key number;
  my_ro_key number;
  cursor c_familles_objets is select * from edtscol.resa_famille_objet;
  cursor c_types_objets is select * from edtscol.resa_type_objet;
  cursor c_objets is select * from edtscol.resa_objet;
  cursor c_resas is select * from edtscol.reservation_objet;
  cursor c_depos is select * from edtscol.resa_objet_depositaire;
begin

  update edtscol.resa_famille_objet set rfo_key = rfo_key + 100000;
  update edtscol.resa_type_objet set rfo_key = rfo_key + 100000;
  
  for row_famille_objet in c_familles_objets loop

    select resa_famille_objet_seq.nextval into my_rfo_key from dual;

    update edtscol.resa_famille_objet set rfo_key = my_rfo_key where rfo_key = row_famille_objet.rfo_key;
    update edtscol.resa_type_objet set rfo_key = my_rfo_key where rfo_key = row_famille_objet.rfo_key;
    
  end loop;

  update edtscol.resa_type_objet set rto_key = rto_key + 100000;
  update edtscol.resa_objet set rto_key = rto_key + 100000;

  for row_type_objet in c_types_objets loop

    select resa_type_objet_seq.nextval into my_rto_key from dual;

    update edtscol.resa_type_objet set rto_key = my_rto_key where rto_key = row_type_objet.rto_key;
    update edtscol.resa_objet set rto_key = my_rto_key where rto_key = row_type_objet.rto_key;
    
  end loop;

  update edtscol.resa_objet set ro_key = ro_key + 100000;
  update edtscol.reservation_objet set ro_key = ro_key + 100000;
  update edtscol.resa_objet_depositaire set ro_key = ro_key + 100000;

  for row_objet in c_objets loop

    select resa_objet_seq.nextval into my_ro_key from dual;

    update edtscol.resa_objet set ro_key = my_ro_key where ro_key = row_objet.ro_key;
    update edtscol.reservation_objet set ro_key = my_ro_key where ro_key = row_objet.ro_key;
    update edtscol.resa_objet_depositaire set ro_key = my_ro_key where ro_key = row_objet.ro_key;
    
  end loop;

end;


ALTER TABLE EDTSCOL.RESA_FAMILLE_OBJET ENABLE CONSTRAINT RESA_FAMILLE_OBJET_PK;

ALTER TABLE EDTSCOL.RESA_TYPE_OBJET ENABLE CONSTRAINT RESA_TYPE_OBJET_PK;

ALTER TABLE EDTSCOL.RESA_OBJET ENABLE CONSTRAINT TABLE1_PK;

ALTER TABLE EDTSCOL.RESA_TYPE_OBJET ENABLE CONSTRAINT FK_FAMILLE_OBJET;

ALTER TABLE EDTSCOL.RESA_OBJET ENABLE CONSTRAINT FK_TYPE_OBJET;

ALTER TABLE EDTSCOL.RESERVATION_OBJET ENABLE CONSTRAINT FK_RESA_OBJET;

ALTER TABLE EDTSCOL.RESA_OBJET_DEPOSITAIRE ENABLE CONSTRAINT FK_DEPOS_RESA_OBJET;


insert into edtscol.db_version (db_comment,db_version,db_date) values ('gestion lots de salles','1.3.7',to_date('15/10/2008','dd/mm/yyyy'));

COMMIT;
