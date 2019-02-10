# -*- coding: utf-8 -*-

#import mysql.connector
import json
import asyncio
import websockets
from Adafruit_BME280 import *

#terradb = mysql.connector.connect(
#    host="localhost",
#    user="root",
#    passwd="123456",
#    database="runoob_db"
#)

async def sendData() :
    async with websockets.connect(
            'ws://145.239.196.28:2681') as websocket:

#terraCursor = terradb.cursor()
        sensor = BME280(t_mode=BME280_OSAMPLE_8, p_mode=BME280_OSAMPLE_8, h_mode=BME280_OSAMPLE_8)
        temp = round(sensor.read_temperature(), 2)
        humidity = round(sensor.read_humidity(), 2)

        sensorVal = (humidity, temp)
        await websocket.send(json.dumps(sensorVal))

asyncio.get_event_loop().run_until_complete(sendData())
#insertValQuery = "INSERT INTO terrarium (create_time, humidite, temperature, update_time) VALUES (CURTIME(), %s, %s, CURTIME())"
#terraCursor.execute(insertValQuery, sensorVal)
#terradb.commit()
#print(terraCursor.rowcount, "record inserted.")

#getTempBoundsQuery = "SELECT * FROM chauffage"

#terraCursor.execute(getTempBoundsQuery)

#for(id, heure_debut, heure_fin, max, min, mois_debut, mois_fin) in terraCursor:
#    print("Mois de début: {}, Mois de fin: {}, Heure de début: {}, Heure de fin: {}, Temperature min: {}, Temperature max: {}".format(mois_debut, mois_fin, heure_debut, heure_fin, min, max))
    
#terraCursor.close();
#terradb.close();
