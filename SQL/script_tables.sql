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
)
