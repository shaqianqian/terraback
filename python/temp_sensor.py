import mysql.connector
from Adafruit_BME280 import *

terradb = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="123456",
    database="runoob_db"
)

terraCursor = terradb.cursor()

sensor = BME280(t_mode=BME280_OSAMPLE_8, p_mode=BME280_OSAMPLE_8, h_mode=BME280_OSAMPLE_8)

degrees = round(sensor.read_temperature(), 2)
humidity = round(sensor.read_humidity(), 2)

val = (humidity, degrees)
sql = "INSERT INTO terrarium (create_time, humidite, temperature, update_time) VALUES (CURTIME(), %s, %s, CURTIME())"

terraCursor.execute(sql, val)

terradb.commit()

print(terraCursor.rowcount, "record inserted.")




