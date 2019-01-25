import sys
import urllib
import RPi.GPIO as GPIO
import time


f=open("../python/chauffage_test.txt","w+")
f.write(str(sys.argv[1]))
print "python : l'etat de heater a change a "+str(sys.argv[1])
#print sys.path

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(27,GPIO.OUT)
if int(str(sys.argv[1]))==1:
    print "LED on"
    GPIO.output(27,GPIO.HIGH)

else:
    print "LED off"
    GPIO.output(27,GPIO.LOW)


