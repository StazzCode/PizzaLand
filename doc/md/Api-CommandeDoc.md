# SAE S4.A02.1 : Pizzaland

#### Cancel Paul - Mirey Kellian

## API Commandes : 

> | URI               |  Opération     | MIME        | Requête         | Réponse                                               |
> |-------------------|----------------|-------------|-----------------|-------------------------------------------------------|
> | /commandes      |  GET  | <-application/json   |                 | Liste des commandes (C1)                                |
> | /commandes/{id} |  GET  | <-application/json   |                 | Une commande (C1) ou alors 404 si elle n'existe pas     |               
> | /commandes/{id}/name |  GET  | <-plain/text    |                 | Le nom de la commande (C1) ou alors 404 si elle n'existe pas|            
> | /commandes      |  POST   | <-application/json | Commande (C1)   | Nouvelle commande (C1) 409 si elle existe déja          |
> | /commandes/{id} |  DELETE |                    |                 | Supprime la commande (C1) puis renvoie 204, 404 si elle n'existe pas|
> | /commandes/{id} |  PATCH  | <-application/json | Commande (C1)   | Modifications d'attributs d'une commande (C1), 404 si elle n'existe pas|
> | /commandes/{id} |  POST   | <-application/json | Pizza (I1) | Ajout d'une pizza (I1) à une commande (C1), 404 si elle n'existe pas et 400 si la pizza n'existe pas |
> | /commandes/{id}/{idPizza} |  DELETE |     |                 | Supprime une pizza (I1) d'une commande (C1) puis renvoie 204, 404 si la commande ou la pizza n'existe pas | 
> | /commandes/{id}/prixFinal |  GET  | <-plain/text |               | Le prix de la commande (C1) ou alors 404 si elle n'existe pas |   

<br>

## Corps des requêtes : 

### C1, Commande

#### Une commande comporte un identifiant, un nom, et une liste de pizzas. Sa représentation JSON (C1) est la suivante : 

```json
{
    "id" : "3",
    "nom" : "Paul Cancel",
    "date" : 2206223600000,
    "pizzas": [
      {
        "id": 1,
        "nom": "pizza jambon fromage",
        "pate": "fine",
        "ingredients": [
          {
            "id": 1,
            "nom": "fromage",
            "prix": 1.99
          },
          {
            "id": 3,
            "nom": "jambon",
            "prix": 2.4
          }
        ]
      }
    ]
}
```

### P1, Pizza 

#### A voir dans la documentation pizza [Pizza](Api-PizzaDoc.md)

<br>

### Lister toutes les commandes connues : 

> GET /pizzaland-cancel-mirey/commandes

#### Requête vers le serveur : 

```json
GET /pizzaland-cancel-mirey/commandes
[
    {
        "id" : "1",
        "nom" : "Paul Cancel",
        "date" : 2206223600000,
        "pizzas": [
        {
            "id": 1,
            "nom": "pizza jambon fromage",
            "pate": "fine",
            "ingredients": [
            {
                "id": 1,
                "nom": "fromage",
                "prix": 1.99
            },
            {
                "id": 3,
                "nom": "jambon",
                "prix": 2.4
            }
            ]
        }
        ]
    }, 
        {
        "id" : "2",
        "nom" : "Kellian Mirey",
        "date" : 2206223600000,
        "pizzas": [
        {
            "id": 1,
            "nom": "pizza jambon fromage",
            "pate": "fine",
            "ingredients": [
            {
                "id": 1,
                "nom": "fromage",
                "prix": 1.99
            },
            {
                "id": 3,
                "nom": "jambon",
                "prix": 2.4
            }
            ]
        }, 
        {
            "id" : "5",
            "nom" : "Pizza Fromage Ananas",
            "pate" : "epaisse",
            "ingredients" : [
                {
                    "id": 1,
                    "nom": "fromage",
                    "prix": 1.99
                },
                {
                    "id": 2,
                    "nom": "ananas",
                    "prix": 6.1
                }
            ]
        }
        ]
    }

]
```

#### Codes de status HTTP

> | Status   | Description                              |
> |----------|------------------------------------------|
> | 200 OK   | La requête s'est effectuée correctement  |

<br>


### Récupérer une commande avec son id :

> GET /pizzaland-cancel-mirey/commandes/{id}

#### Requête vers le serveur

```json
GET /pizzaland-cancel-mirey/commandes/5
{
    "id" : "1",
    "nom" : "Paul Cancel",
    "date" : 2206223600000,
    "pizzas": [
    {
        "id": 1,
        "nom": "pizza jambon fromage",
        "pate": "fine",
        "ingredients": [
        {
            "id": 1,
            "nom": "fromage",
            "prix": 1.99
        },
        {
            "id": 3,
            "nom": "jambon",
            "prix": 2.4
        }
        ]
    }
    ]
}
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La commande n'existe pas                 |

<br>

### Récupérer le nom d'une commande :

> GET /pizzaland-cancel-mirey/commandes/{id}/name

#### Requête vers le serveur

```json
GET /pizzaland-cancel-mirey/commandes/1/name
```

#### Réponse du serveur 

```
Paul Cancel
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La commande n'existe pas                 |

<br>

### Ajouter une commande : 

> POST /pizzaland-cancel-mirey/commandes
>  {
>    "id" : "3",
>    "nom" : "Paul Cancel",
>    "date" : 2206223600000,
>    "pizzas": [
>      {
>        "id": 1,
>        "nom": "pizza jambon fromage",
>        "pate": "fine",
>        "ingredients": [
>          {
>            "id": 1,
>            "nom": "fromage",
>            "prix": 1.99
>          },
>          {
>            "id": 3,
>            "nom": "jambon",
>            "prix": 2.4
>          }
>        ]
>      }
>    ]
>  }

#### Requête vers le serveur

```json
POST /pizzaland-cancel-mirey/commandes
{
    "id" : "3",
    "nom" : "Paul Cancel",
    "date" : 2206223600000,
    "pizzas": [
        {
        "id": 1,
        "nom": "pizza jambon fromage",
        "pate": "fine",
        "ingredients": [
            {
            "id": 1,
            "nom": "fromage",
            "prix": 1.99
            },
            {
            "id": 3,
            "nom": "jambon",
            "prix": 2.4
            }
        ]
        }
    ]
}
```

#### Réponse du serveur
```json
{
    "id" : "3",
    "nom" : "Paul Cancel",
    "date" : 2206223600000,
    "pizzas": [
        {
        "id": 1,
        "nom": "pizza jambon fromage",
        "pate": "fine",
        "ingredients": [
            {
            "id": 1,
            "nom": "fromage",
            "prix": 1.99
            },
            {
            "id": 3,
            "nom": "jambon",
            "prix": 2.4
            }
        ]
        }
    ]
}
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 409 CONFLICT  | La commande existe déja                  |

<br>

### Supprimer une commande : 

> DELETE /pizzaland-cancel-mirey/commandes/{id}

#### Requête vers le serveur

```
DELETE /pizzaland-cancel-mirey/commandes/2
```

> | Status        | Description                  |
> |---------------|------------------------------|
> | 204 NO CONTENT | La commande a été supprimé     |
> | 404 NOT FOUND  | La commande n'existe pas       |


<br>

### Modifier les attributs d'une commande : 

> PATCH /pizzaland-cancel-mirey/commandes/{id}
>  {
>    "nom":"Mickey"
>  }

#### Requête vers le serveur

```json
PATCH /pizzaland-cancel-mirey/commandes/3
  {
    "nom":"Mickey"
  }
```

#### Réponse du serveur
```json
{
    "id" : "3",
    "nom" : "Mickey",
    "date" : 2206223600000,
    "pizzas": [
        {
        "id": 1,
        "nom": "pizza jambon fromage",
        "pate": "fine",
        "ingredients": [
            {
            "id": 1,
            "nom": "fromage",
            "prix": 1.99
            },
            {
            "id": 3,
            "nom": "jambon",
            "prix": 2.4
            }
        ]
        }
    ]
}
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La commande n'existe pas                 |

<br>

### Ajouter une commande à une commande : 

> POST /pizzaland-cancel-mirey/commandes/{id}
> {
>     "id": 2,
>     "nom": "pizza jambon fromage",
>     "pate": "fine",
>     "ingredients": [
>     {
>         "id": 1,
>         "nom": "fromage",
>         "prix": 1.99
>     },
>     {
>         "id": 3,
>         "nom": "jambon",
>         "prix": 2.4
>     }
>     ]
> }

#### Requête vers le serveur

```json
POST /pizzaland-cancel-mirey/commandes/3
{
    "id": 2,
    "nom": "pizza jambon fromage",
    "pate": "fine",
    "ingredients": [
    {
        "id": 1,
        "nom": "fromage",
        "prix": 1.99
    },
    {
        "id": 3,
        "nom": "jambon",
        "prix": 2.4
    }
    ]
}
```

#### Réponse du serveur
```json
{
    "id" : "3",
    "nom" : "Mickey",
    "date" : 2206223600000,
    "pizzas": [
        {
        "id": 1,
        "nom": "pizza jambon fromage",
        "pate": "fine",
        "ingredients": [
            {
                "id": 1,
                "nom": "fromage",
                "prix": 1.99
            },
            {
                "id": 3,
                "nom": "jambon",
                "prix": 2.4
            }
        ]
        },
        {
            "id": 2,
            "nom": "pizza jambon fromage",
            "pate": "fine",
            "ingredients": [
            {
                "id": 1,
                "nom": "fromage",
                "prix": 1.99
            },
            {
                "id": 3,
                "nom": "jambon",
                "prix": 2.4
            }
            ]
        }
    ]
}
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La commande n'existe pas                    |
> | 400 BAD REQUEST | La pizza n'existe pas              |


<br>

### Supprimer une pizza d'une commande : 

> DELETE /pizzaland-cancel-mirey/commandes/{id}/{idPizza}

#### Requête vers le serveur

```
DELETE /pizzaland-cancel-mirey/pizzas/3/1
```

> | Status        | Description                  |
> |---------------|------------------------------|
> | 204 NO CONTENT | La pizza a été supprimé     |
> | 404 NOT FOUND  | La commande ou la pizza n'existe pas |


<br>

### Récupérer le prix d'une commande :

> GET /pizzaland-cancel-mirey/commandes/{id}/prixFinal

#### Requête vers le serveur

```json
GET /pizzaland-cancel-mirey/commandes/3/prixFinal
```

#### Réponse du serveur 

```
35.29
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La commande n'existe pas                 |

<br>