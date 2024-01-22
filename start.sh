#!/bin/bash
npm install http-server -g

# kill $(ps aux | grep '[j]ava' | awk '{print $2}')
# kill $(ps aux | grep '[h]ttp-server' | awk '{print $2}')
http-server ./full_build/build -p 8081 &
java -jar ./full_build/StrayCatsSG-0.0.1-SNAPSHOT.jar

