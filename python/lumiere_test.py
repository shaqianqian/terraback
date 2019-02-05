import sys
import urllib

f=open("../python/lumiere_test.txt","w+")
f.write(str(sys.argv[1]))
print "python : l'etat de lumiere a change a "+str(sys.argv[1])
#print sys.path

