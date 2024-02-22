DROP TABLE IF EXISTS commandes;
DROP TABLE IF EXISTS associationCommandePizzas; 

CREATE TABLE commandes(
    id INT PRIMARY KEY,
    nom TEXT,
    date DATE
);



Create table associationCommandePizzas (
       idCommande int,
       idPizza int,

       Constraint pk_associationCommandePizzas Primary Key (idCommande,idPizza),

       Constraint fk_idPizza Foreign Key (idPizza)
       		  References pizzas(id),

       Constraint fk_idCommande Foreign Key (idCommande)
       		  References commandes(id)
);


Insert into Commandes values (1, 'Quentin al Hamitouche', '2024-01-26'),
       	    	   	  (2, 'Guillaume Mirey', '2024-02-11');

Insert into associationCommandePizzas values (1,1), (1,2), (2,1);