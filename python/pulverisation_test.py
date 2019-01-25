import sys
import urllib
import RPi.GPIO as GPIO
import time

f=open("../python/pulverisation_test.txt","w+")
f.write(str(sys.argv[1]))
print "python : l'etat de pulverisation a change a "+str(sys.argv[1])
#print sys.path

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(22,GPIO.OUT)

print "LED on"
GPIO.output(22,GPIO.HIGH)
time.sleep(int(str(sys.argv[1])))
print "LED off"
GPIO.output(22,GPIO.LOW)



