import threading
import time
import random
import mysql.connector

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="123456"
)
print(mydb)
def fun_timer():
    temperature=str(round(random.uniform(30,37),2))
    humidity=str(round(random.uniform(60,80),2))
    print("temperature: "+temperature+" humidite: "+humidity)
    global mycursor
    mycursor = mydb.cursor()
    # mycursor.execute("CREATE DATABASE runoob_db")
    sql = "Insert into runoob_db.terrarium (temperature,humidite,create_time,update_time) values(" + temperature + "," + humidity + ",CURTIME(),CURTIME())"
    mycursor = mydb.cursor()
    mycursor.execute(sql)
    mydb.commit()
    mycursor.close()
    global timer
    timer = threading.Timer(5, fun_timer)
    timer.start()

timer = threading.Timer(1, fun_timer)
timer.start()
time.sleep(100)
timer.cancel()
