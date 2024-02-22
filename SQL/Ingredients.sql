DROP TABLE IF EXISTS ingredients;

CREATE TABLE ingredients(id int Primary Key, nom text, prix float);

Insert into ingredients
       values (1, 'fromage', 1.99),
       	      (2, 'ananas', 6.1),
       	      (3, 'jambon', 2.4),
       	      (4, 'lardons', 2),
       	      (5, 'aubergines', 3.8),
       	      (6, 'champignons', 1.5);
