**ENTREPRISE** (<ins>id_entreprise</ins>, nom, pays)  
**AVIS** (<ins>_pseudo_</ins>, <ins>_id_jeux_</ins>, note, commentaire)  
**EST AMI(E)** (<ins>_pseudo_</ins>, <ins>_pseudo.1_</ins>)  
**JEUX** (<ins>id_jeux</ins>, titre, date_sortie, prix, age_requis, description_jeux, _id_entreprise_,
_id_entreprise.1_)  
**PARTAGER** (<ins>_pseudo_</ins>, <ins>_id_jeux_</ins>, <ins>_pseudo.1_</ins>)  
**JOUEUR** (<ins>pseudo</ins>, passwd, nom, adress_mail, date_naissance, monnaie)  
**DEBLOQUE** (<ins>_num_succes_</ins>, <ins>_pseudo_</ins>, date)  
**ACHAT** (<ins>_id_jeux_</ins>, <ins>_pseudo_</ins>)
<!--
**GENRE** (<ins>type</ins>)  
-->
**APPARTIENT** (<ins>_id_jeux_</ins>, <ins>_type_</ins>)  
**SUCCES** (<ins>num_succes</ins>, intitule, conditions, _id_jeux_)