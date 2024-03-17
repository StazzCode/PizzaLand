# SAE S4.A02.1 : Pizzaland

#### Cancel Paul - Mirey Kellian

## API Pizzas : 

> | URI               |  Opération     | MIME        | Requête         | Réponse                                               |
> |-------------------|----------------|-------------|-----------------|-------------------------------------------------------|
> | /pizzas      |  GET  | <-application/json   |                 | Liste des pizzas (P1)                            |
> | /pizzas/{id} |  GET  | <-application/json   |                 | Une pizza (P1) ou alors 404 si elle n'existe pas    |               
> | /pizzas/{id}/name |  GET  | <-plain/text    |                 | Le nom de la pizza (P1) ou alors 404 si elle n'existe pas|            
> | /pizzas      |  POST   | <-application/json | Pizza (P1)      | Nouvelle pizza (P1) 409 si elle existe déja         |
> | /pizzas/{id} |  DELETE |                    |                 | Supprime la pizza (P1) puis renvoie 204, 404 si elle n'existe pas|
> | /pizzas/{id} |  PATCH  | <-application/json | Pizza (P2)      | Modifications d'attributs d'une pizza (P1), 404 si elle n'existe pas|
> | /pizzas/{id} |  POST   | <-application/json | Ingrédient (I1) | Ajout d'un ingrédient (I1) à une pizza (P1), 404 si elle n'existe pas et 400 si l'id l'ingrédient n'existe pas |
> | /pizzas/{id}/{idIngredient} |  DELETE |     |                 | Supprime un ingrédient (I1) d'une pizza (P1) puis renvoie 204, 404 si la pizza ou l'ingrédient n'existe pas | 
> | /pizzas/{id}/prixFinal |  GET  | <-plain/text |               | Le prix de la pizza (P1) ou alors 404 si elle n'existe pas |   

<br>

## Corps des requêtes : 

### P1, Pizza

#### Une pizza comporte un identifiant, un nom, une pâte et une liste d'ingrédient. Sa représentation JSON (P1) est la suivante : 

```json
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
```

### I1, Ingrédient 

#### A voir dans la documentation ingrédient [Ingrédient](Api-IngredientDoc.md)

<br>

### Lister toutes les pizzas connues : 

> GET /pizzaland-cancel-mirey/pizzas

#### Requête vers le serveur : 

```json
GET /pizzaland-cancel-mirey/pizzas
[
    {
        "id" : "1",
        "nom" : "Pizza Jambon",
        "pate" : "fine",
        "ingredients" : [
            {
                "id": 2,
                "nom": "jambon",
                "prix": 2.99
            }
        ]
    },
    {
        "id" : "2",
        "nom" : "Pizza Jambon Fromage",
        "pate" : "épaisse",
        "ingredients" : [
            {
                "id": 1,
                "nom": "fromage",
                "prix": 1.99
            }, 
            {
                "id": 2,
                "nom": "jambon",
                "prix": 2.99
            }
        ]
    } 
    ...
]
```

#### Codes de status HTTP

> | Status   | Description                              |
> |----------|------------------------------------------|
> | 200 OK   | La requête s'est effectuée correctement  |

<br>


### Récupérer une pizza avec son id :

> GET /pizzaland-cancel-mirey/pizzas/{id}

#### Requête vers le serveur

```json
GET /pizzaland-cancel-mirey/pizzas/5
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
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La pizza n'existe pas                    |

<br>

### Récupérer le nom d'une pizza :

> GET /pizzaland-cancel-mirey/pizzas/{id}/name

#### Requête vers le serveur

```json
GET /pizzaland-cancel-mirey/pizzas/5/name
```

#### Réponse du serveur 

```
Pizza Fromage Ananas
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La pizza n'existe pas                    |

<br>

### Ajouter une pizza : 

> POST /pizzaland-cancel-mirey/pizzas
> {
>   "id": "7",
>   "nom": "chevre",
>   "prix": "69.69"
> }

#### Requête vers le serveur

```json
POST /pizzaland-cancel-mirey/pizzas
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
```

#### Réponse du serveur
```json
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
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 409 CONFLICT  | La pizza existe déja                     |

<br>

### Supprimer une pizza : 

> DELETE /pizzaland-cancel-mirey/pizzas/{id}

#### Requête vers le serveur

```
DELETE /pizzaland-cancel-mirey/pizzas/7
```

> | Status        | Description                  |
> |---------------|------------------------------|
> | 204 NO CONTENT | La pizza a été supprimé     |
> | 404 NOT FOUND  | La pizza n'existe pas       |


<br>

### Modifier les attributs d'une pizza : 

> PATCH /pizzaland-cancel-mirey/pizzas/{id}
>  {
>    "nom" : "Pizza Goatesque",
>    "pate" : "de fruit mdr"
>  }

#### Requête vers le serveur

```json
PATCH /pizzaland-cancel-mirey/pizzas/5
  {
    "nom" : "Pizza Goatesque",
    "pate" : "de fruit mdr"
  }
```

#### Réponse du serveur
```json
{
    "id" : "5",
    "nom" : "Pizza Goatesque",
    "pate" : "de fruit mdr",
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
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La pizza n'existe pas                    |

<br>

### Ajouter un ingrédient à une pizza : 

> POST /pizzaland-cancel-mirey/pizzas/{id}
>  {
>    "id": 6,
>    "nom": "champignons",
>    "prix": 1.5
>  }

#### Requête vers le serveur

```json
POST /pizzaland-cancel-mirey/pizzas/5
{
    "id": 6,
    "nom": "champignons",
    "prix": 1.5
}
```

#### Réponse du serveur
```json
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
        },
        {
        "id": 6,
        "nom": "champignons",
        "prix": 1.5
        }
    ]
}
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La pizza n'existe pas                    |
> | 400 BAD REQUEST | L'ingrédient n'existe pas              |


<br>

### Supprimer un ingrédient d'une pizza : 

> DELETE /pizzaland-cancel-mirey/pizzas/{id}/{idIngredient}

#### Requête vers le serveur

```
DELETE /pizzaland-cancel-mirey/pizzas/5/6
```

> | Status        | Description                  |
> |---------------|------------------------------|
> | 204 NO CONTENT | La pizza a été supprimé     |
> | 404 NOT FOUND  | La pizza ou l'ingrédient n'existe pas |


<br>

### Récupérer le prix d'une pizza :

> GET /pizzaland-cancel-mirey/pizzas/{id}/prixFinal

#### Requête vers le serveur

```json
GET /pizzaland-cancel-mirey/pizzas/5/prixFinal
```

#### Réponse du serveur 

```
14.59
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | La pizza n'existe pas                    |

<br>