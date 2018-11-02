#Le back de projet TerraStation
Recuperers les parametres sont recupere par raspberry, et les stoke dans la base de donnee. Apres le traitement des donnees,
on va les retourner ver front en forme json
url www.localhost:8080
##TerraiumController
#### /terraium/getAll (recuperer tous les parametres de terraium)
``` json
{
    "code": 0,
    "msg": "reussi",
    "data": [
        {
            "id": 1,
            "temperature": 31.12,
            "humidite": 88.09,
            "createTime": "2018-11-01 19:53:49",
            "updateTime": "2018-11-01 19:53:49"
        },
        {
            "id": 2,
            "temperature": 35.61,
            "humidite": 78.35,
            "createTime": "2018-11-01 19:53:54",
            "updateTime": "2018-11-01 19:53:54"
        }]
  }
``` 
#### /terraium/add (ajouter une ligne de parametre au terraium)
``` json
{
    "code": 0,
    "msg": "reussi",
    "data": {
        "id": 21,
        "temperature": 28,
        "humidite": 70,
        "createTime": "2018-11-02 16:26:25",
        "updateTime": "2018-11-02 16:26:25"
    }
}

``` 
#### /terraium/update/{id} (modifier une ligne de parametre au terraium)
``` json
{
    "code": 0,
    "msg": "reussi",
    "data": {
        "id": 21,
        "temperature": 28,
        "humidite": 69,
        "createTime": "2018-11-02 16:26:26",
        "updateTime": "2018-11-02 16:27:17"
    }
}
```

#### /terraium/delete/{id} (supprime une ligne de parametre au terraium)
```json
{
    "code": 0,
    "msg": "reussi",
    "data": "vous avez reussi de supprimer : 22"
}
```

#### /terraium/getCurrentParametres (recuperer les lignes des parametres recents）

``` json

{
    "code": 0,
    "msg": "reussi",
    "data": [
        {
            "id": 20,
            "temperature": 36.06,
            "humidite": 51.64,
            "createTime": "2018-11-01 19:55:24",
            "updateTime": "2018-11-01 19:55:24"
        },
        {
            "id": 19,
            "temperature": 35.5,
            "humidite": 88.81,
            "createTime": "2018-11-01 19:55:19",
            "updateTime": "2018-11-01 19:55:19"
        }]
 }
 ```

 
#### /terraium/temperature/getCurrentTemperaturesVO(recuperer les temperatures recentes, maximal et minimal )


 ``` json
{
    "code": 0,
    "msg": "reussi",
    "data": {
        "max": {
            "id": 20,
            "type": "Temperature",
            "valeur": 36.06,
            "symbol": "%",
            "createTime": "2018-11-01 19:55:24",
            "updateTime": "2018-11-01 19:55:24"
        },
        "min": {
            "id": 17,
            "type": "Temperature",
            "valeur": 33,
            "symbol": "%",
            "createTime": "2018-11-01 19:55:09",
            "updateTime": "2018-11-01 19:55:09"
        },
        "terraiumsVO": [
            {
                "id": 15,
                "type": "Temperature",
                "valeur": 33.22,
                "symbol": "%",
                "createTime": "2018-11-01 19:54:59",
                "updateTime": "2018-11-01 19:54:59"
            },
            {
                "id": 16,
                "type": "Temperature",
                "valeur": 35.97,
                "symbol": "%",
                "createTime": "2018-11-01 19:55:04",
                "updateTime": "2018-11-01 19:55:04"
            },
            {
                "id": 17,
                "type": "Temperature",
                "valeur": 33,
                "symbol": "%",
                "createTime": "2018-11-01 19:55:09",
                "updateTime": "2018-11-01 19:55:09"
            }
        ]
    }
}
```


#### /terraium/humidite/getCurrentHumiditesVO(recuperer les humidites recentes, maximal et minimal )

``` json
{
    "code": 0,
    "msg": "reussi",
    "data": {
        "max": {
            "id": 17,
            "type": "Humidite",
            "valeur": 89.59,
            "symbol": "%",
            "createTime": "2018-11-01 19:55:09",
            "updateTime": "2018-11-01 19:55:09"
        },
        "min": {
            "id": 20,
            "type": "Humidite",
            "valeur": 51.64,
            "symbol": "%",
            "createTime": "2018-11-01 19:55:24",
            "updateTime": "2018-11-01 19:55:24"
        },
        "terraiumsVO": [
            {
                "id": 15,
                "type": "Humidite",
                "valeur": 57.8,
                "symbol": "%",
                "createTime": "2018-11-01 19:54:59",
                "updateTime": "2018-11-01 19:54:59"
            },
            {
                "id": 16,
                "type": "Humidite",
                "valeur": 76.67,
                "symbol": "%",
                "createTime": "2018-11-01 19:55:04",
                "updateTime": "2018-11-01 19:55:04"
            },
            {
                "id": 17,
                "type": "Humidite",
                "valeur": 89.59,
                "symbol": "%",
                "createTime": "2018-11-01 19:55:09",
                "updateTime": "2018-11-01 19:55:09"
            }
           
        ]
    }
}
```

