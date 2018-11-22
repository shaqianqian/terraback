import sys
import urllib

f=open("../python/chauffage_test.txt","w+")
f.write(str(sys.argv[1]))
print "python : l'etat de heater a change a ç"+str(sys.argv[1])
#print sys.path

