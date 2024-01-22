#!/bin/bash
platform=$1

be_ip=""
if [[ "$platform" == "aws" ]]; then
    be_ip="52.76.130.23"
else
    be_ip="localhost"
fi


# build FE
cd "./react/frontend/"
rm .env
touch .env
echo "REACT_APP_BACKEND_IP='$be_ip'" > .env
npm install
npm run build
cd ../../

# build BE
mvn clean install

if [[ ! -e full_build ]]; then
    rm -r full_build
fi
mkdir -p full_build

cp -pr ./react/frontend/build ./full_build/.
cp -pr ./target/StrayCatsSG-0.0.1-SNAPSHOT.jar ./full_build/.


if [[ "$platform" == "aws" ]]; then
    # update PEM file to CICD secret
    scp -pr -i /Users/zheng/.ssh/jms.pem ./full_build ec2-user@52.76.130.23:/home/ec2-user/uploaded
    ssh -i /Users/zheng/.ssh/jms.pem ec2-user@52.76.130.23 ./restart.sh
fi

