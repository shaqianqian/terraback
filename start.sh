#!/bin/bash
sudo mvn clean
sudo mvn package
cd target
java -jar sha-0.0.1-SNAPSHOT.jar

