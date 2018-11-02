#Le back de projet TerraStation
Recuperers les parametres sont recupere par raspberry, et les stoke dans la base de donnee. Apres le traitement des donnees,
on va les retourner ver front en forme json
url www.localhost:8080
##TerraiumController
#### /terraium//getAll (recuperer tous les parametres de terraium)
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

#### /terraium//getCurrentParametres (supprime une ligne de parametre au terraium)

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
        "max_temp": {
            "id": 20,
            "temperature": 36.06,
            "createTime": "2018-11-01 19:55:24",
            "updateTime": "2018-11-01 19:55:24"
        },
        "min_temp": {
            "id": 20,
            "temperature": 33,
            "createTime": "2018-11-01 19:55:09",
            "updateTime": "2018-11-01 19:55:09"
        },
        "temperaturesVO": [
            {
                "id": 15,
                "temperature": 33.22,
                "createTime": "2018-11-01 19:54:59",
                "updateTime": "2018-11-01 19:54:59"
            },
            {
                "id": 16,
                "temperature": 35.97,
                "createTime": "2018-11-01 19:55:04",
                "updateTime": "2018-11-01 19:55:04"
            },
            {
                "id": 17,
                "temperature": 33,
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
        "humiditesVO": [
            {
                "id": 15,
                "humidite": 57.8,
                "createTime": "2018-11-01 19:54:59",
                "updateTime": "2018-11-01 19:54:59"
            },
            {
                "id": 16,
                "humidite": 76.67,
                "createTime": "2018-11-01 19:55:04",
                "updateTime": "2018-11-01 19:55:04"
            },
            {
                "id": 17,
                "humidite": 89.59,
                "createTime": "2018-11-01 19:55:09",
                "updateTime": "2018-11-01 19:55:09"
            },
        ],
        "max_hum": {
            "id": 17,
            "humidite": 89.59,
            "createTime": "2018-11-01 19:55:09",
            "updateTime": "2018-11-01 19:55:09"
        },
        "min_hum": {
            "id": 20,
            "humidite": 51.64,
            "createTime": "2018-11-01 19:55:24",
            "updateTime": "2018-11-01 19:55:24"
        }
    }
}
```

