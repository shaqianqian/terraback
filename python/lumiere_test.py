import sys
import urllib
import RPi.GPIO as GPIO
import time



f=open("../python/lumiere_test.txt","w+")
f.write(str(sys.argv[1]))
print "python : l'etat de lumiere a change a "+str(sys.argv[1])
#print sys.path
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(17,GPIO.OUT)
if int(str(sys.argv[1]))==1:
    print "LED on"
    GPIO.output(17,GPIO.HIGH)

else:
    print "LED off"
    GPIO.output(17,GPIO.LOW)



