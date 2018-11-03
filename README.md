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
        "type": "Temperature",
        "symbol": "°C",
        "max": {
            "value": 36.06,
            "time": "2018-11-01 19:55:24"
        },
        "min": {
            "value": 33,
            "time": "2018-11-01 19:55:09"
        },
        "values": [
            {
                "value": 33.22,
                "time": "2018-11-01 19:54:59"
            },
            {
                "value": 35.97,
                "time": "2018-11-01 19:55:04"
            },
            {
                "value": 33,
                "time": "2018-11-01 19:55:09"
            },
            {
                "value": 35.89,
                "time": "2018-11-01 19:55:14"
            },
            {
                "value": 35.5,
                "time": "2018-11-01 19:55:19"
            },
            {
                "value": 36.06,
                "time": "2018-11-01 19:55:24"
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
        "type": "Humidite",
        "symbol": "%",
        "max": {
            "value": 89.59,
            "time": "2018-11-01 19:55:09"
        },
        "min": {
            "value": 51.64,
            "time": "2018-11-01 19:55:24"
        },
        "values": [
            {
                "value": 57.8,
                "time": "2018-11-01 19:54:59"
            },
            {
                "value": 76.67,
                "time": "2018-11-01 19:55:04"
            },
            {
                "value": 89.59,
                "time": "2018-11-01 19:55:09"
            },
            {
                "value": 87.05,
                "time": "2018-11-01 19:55:14"
            },
            {
                "value": 88.81,
                "time": "2018-11-01 19:55:19"
            },
            {
                "value": 51.64,
                "time": "2018-11-01 19:55:24"
            }
        ]
    }
}
```


#### /terraium/humidite/getCurrentParametresVO(recuperer les donnees de tous les sensors )

``` json
{
    "code": 0,
    "msg": "reussi",
    "data": {
        "sensors": [
            {
                "type": "temperature",
                "symbol": "°C",
                "name": "Temperature",
                "id": 1,
                "max": {
                    "value": 36.06,
                    "time": "2018-11-01 19:55:24"
                },
                "min": {
                    "value": 33,
                    "time": "2018-11-01 19:55:09"
                },
                "values": [
                    {
                        "value": 33.22,
                        "time": "2018-11-01 19:54:59"
                    },
                    {
                        "value": 35.97,
                        "time": "2018-11-01 19:55:04"
                    },
                    {
                        "value": 33,
                        "time": "2018-11-01 19:55:09"
                    },
                ]
            },
            {
                "type": "humidite",
                "symbol": "%",
                "name": "Humidite",
                "id": 2,
                "max": {
                    "value": 89.59,
                    "time": "2018-11-01 19:55:09"
                },
                "min": {
                    "value": 51.64,
                    "time": "2018-11-01 19:55:24"
                },
                "values": [
                    {
                        "value": 57.8,
                        "time": "2018-11-01 19:54:59"
                    },
                    {
                        "value": 76.67,
                        "time": "2018-11-01 19:55:04"
                    },
            
                ]
            }
        ]
    }
}
```

#### /terraium/humidite/getCurrentParametresGenereVO(recuperer les donnees de tous les sensors pour les graphes)

``` json
{
    "code": 0,
    "msg": "reussi",
    "data": {
        "sensors": [
            {
                "type": "temperature",
                "symbol": "°C",
                "name": "Temperature",
                "id": 1,
                "max": {
                    "y": 36.06,
                    "x": "2018-11-01 19:55:24"
                },
                "min": {
                    "y": 33,
                    "x": "2018-11-01 19:55:09"
                },
                "values": [
                    {
                        "y": 33.22,
                        "x": "2018-11-01 19:54:59"
                    },
                    {
                        "y": 35.97,
                        "x": "2018-11-01 19:55:04"
                    },
                    {
                        "y": 33,
                        "x": "2018-11-01 19:55:09"
                    },
                    {
                        "y": 35.89,
                        "x": "2018-11-01 19:55:14"
                    },
                    {
                        "y": 35.5,
                        "x": "2018-11-01 19:55:19"
                    },
                    {
                        "y": 36.06,
                        "x": "2018-11-01 19:55:24"
                    }
                ]
            },
            {
                "type": "humidite",
                "symbol": "%",
                "name": "Humidite",
                "id": 2,
                "max": {
                    "y": 89.59,
                    "x": "2018-11-01 19:55:09"
                },
                "min": {
                    "y": 51.64,
                    "x": "2018-11-01 19:55:24"
                },
                "values": [
                    {
                        "y": 57.8,
                        "x": "2018-11-01 19:54:59"
                    },
                    {
                        "y": 76.67,
                        "x": "2018-11-01 19:55:04"
                    },
                    {
                        "y": 89.59,
                        "x": "2018-11-01 19:55:09"
                    },
                    {
                        "y": 87.05,
                        "x": "2018-11-01 19:55:14"
                    },
                    {
                        "y": 88.81,
                        "x": "2018-11-01 19:55:19"
                    },
                    {
                        "y": 51.64,
                        "x": "2018-11-01 19:55:24"
                    }
                ]
            }
        ]
    }
}
```