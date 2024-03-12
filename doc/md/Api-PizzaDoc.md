# SAE S4.A02.1 : Pizzaland

#### Cancel Paul - Mirey Kellian

## API Pizzas : 

> | URI               |  Opération     | MIME        | Requête         | Réponse                                               |
> |-------------------|----------------|-------------|-----------------|-------------------------------------------------------|
> | /pizzas      |  GET  | <-application/json   |                 | Liste des ingrédients (P1)                            |
> | /pizzas/{id} |  GET  | <-application/json   |                 | Une pizza (P1) ou alors 404 si il n'existe pas    |               
> | /pizzas/{id}/name |  GET  | <-plain/text    |                 | Le nom de la pizza ou alors 404 si il n'existe pas|            
> | /pizzas      |  POST      | <-plain/text    | Pizza (P1) | Nouvelle pizza (P1) 409 si il existe déja          |
> | /pizzas/{id} |  DELETE |                    |                 | Supprime la pizza (P1) puis renvoie 204, 404 si il n'existe pas|
> | /pizzas/{id} |  PATCH  |                    |                 | Supprime la pizza (P1) puis renvoie 204, 404 si il n'existe pas|