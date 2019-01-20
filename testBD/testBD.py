import mysql.connector

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="123456"
)

print(mydb)
file = open("data.txt")
lines = file.readlines()
size=len(lines)
print (size)
for line in lines:
    temperature=(line.split(",")[1])
    humity=(line.split(",")[3])
        #print(temperature+" "+humity)
    mycursor = mydb.cursor()
        # mycursor.execute("CREATE DATABASE runoob_db")
    sql="Insert into runoob_db.terraium (temperature,humite,create_time,update_time) values("+temperature+","+ humity+",CURTIME(),CURTIME())"
    mycursor = mydb.cursor()
    mycursor.execute(sql)
    mydb.commit()
    mycursor.close()
#
# # mycursor.execute("CREATE DATABASE runoob_db")
#
# sql="Insert into runoob_db.terraium (temperature,humite,temp_ajoute) values(23,23,CURTIME())"
# # sql="INSERT INTO `runoob_db`.`terraium` (`temperature`, `humite`,`temp_ajoute`) VALUES ('34', '43',CURTIME())";



