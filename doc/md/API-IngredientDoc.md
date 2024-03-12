# SAE S4.A02.1 : Pizzaland

#### Cancel Paul - Mirey Kellian

## API Ingrédients : 


> | URI               |  Opération     | MIME        | Requête         | Réponse                                               |
> |-------------------|----------------|-------------|-----------------|-------------------------------------------------------|
> | /ingredients      |  GET  | <-application/json   |                 | Liste des ingrédients (I1)                            |
> | /ingredients/{id} |  GET  | <-application/json   |                 | Un ingrédient (I1) ou alors 404 si il n'existe pas    |               
> | /ingredients/{id}/name |  GET  | <-plain/text    |                 | Le nom de l'ingrédient ou alors 404 si il n'existe pas|            
> | /ingredients      |  POST      | <-plain/text    | Ingrédient (I1) | Nouvel ingrédient (I1) 409 si il existe déja          |
> | /ingredients/{id} |  DELETE |                    |                 | Supprime l'ingrédient (I1) puis renvoie 204, 404 si il n'existe pas|

<br>

## Corps des requêtes : 

### I1, Ingrédient

#### Un ingrédient comporte un identifiant, un nom et un prix. Sa représentation JSON (I1) est la suivante : 

```json
{
  "id": "7",
  "nom": "chevre",
  "prix": "69.69"
}
```

<br>

## Exemples

### Lister tous les ingrédients connus : 

> GET /pizzaland-cancel-mirey/ingredients

#### Requête vers le serveur : 

```json
GET /pizzaland-cancel-mirey/ingredients
[
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
    "id": 3,
    "nom": "jambon",
    "prix": 2.4
  }
]
```

#### Codes de status HTTP

> | Status   | Description                              |
> |----------|------------------------------------------|
> | 200 OK   | La requête s'est effectuée correctement  |

<br>

### Récupérer un ingrédient avec son id :

> GET /pizzaland-cancel-mirey/ingredients/{id}

#### Requête vers le serveur

```json
GET /pizzaland-cancel-mirey/ingredients/4
{
  "id": 4,
  "nom": "lardons",
  "prix": 2
}
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | L'ingrédient n'existe pas                |

<br>

### Récupérer le nom d'un ingrédient :

> GET /pizzaland-cancel-mirey/ingredients/{id}/name

#### Requête vers le serveur

```json
GET /pizzaland-cancel-mirey/ingredients/4/name
lardons
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 404 NOT FOUND | L'ingrédient n'existe pas                |

<br>

### Ajouter un ingrédient : 

> POST /pizzaland-cancel-mirey/ingredients
> {
>   "id": "7",
>   "nom": "chevre",
>   "prix": "69.69"
> }

#### Requête vers le serveur

```json
POST /pizzaland-cancel-mirey/ingredients
{
  "id": "7",
  "nom": "chevre",
  "prix": "69.69"
}
```

#### Réponse du serveur
```json
{
  "id": "7",
  "nom": "chevre",
  "prix": "69.69"
}
```

#### Codes de status HTTP

> | Status        | Description                              |
> |---------------|------------------------------------------|
> | 200 OK        | La requête s'est effectuée correctement  |
> | 409 CONFLICT  | L'ingrédient existe déja                 |

<br>

### Supprimer un ingrédient : 

> DELETE /pizzaland-cancel-mirey/ingredients/{id}

#### Requête vers le serveur

DELETE /pizzaland-cancel-mirey/ingredients/7
```

> | Status        | Description                  |
> |---------------|------------------------------|
> | 204 NO CONTENT | L'ingrédient a été supprimé |
> | 404 NOT FOUND  | L'ingrédient n'existe pas   |