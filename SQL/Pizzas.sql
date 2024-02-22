Drop table if exists pizzas;
Drop table if exists associationPizzaIngrédients; 

Create table pizzas (
       id int Primary Key,
       nom text,
       pate text
);

Create table associationPizzaIngrédients (
       idPizza int,
       idIngre int,

       Constraint pk_associationPizzaIngrédients Primary Key (idPizza,idIngre),

       Constraint fk_idPizza Foreign Key (idPizza)
       		  References pizzas(id),

       Constraint fk_idIngre Foreign Key (idIngre)
       		  References ingredients(id)
);


Insert into pizzas values (1, 'pizza jambon fromage', 'fine'),
       	    	   	  (2, 'pizza fromage', 'fine');

Insert into associationpizzaingrédients values (1,1), (1,3), (2,1);