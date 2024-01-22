#!/bin/bash
npm install http-server -g
cd build
http-server -p 8081 &
cd ..

java -jar JMSChallenge.jar

