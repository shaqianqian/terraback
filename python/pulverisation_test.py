import sys
import urllib

f=open("./python/pulverisation_test.txt","w+")
f.write(str(sys.argv[1]))
print "python : l'etat de pulverisation a change a "+str(sys.argv[1])
#print sys.path

