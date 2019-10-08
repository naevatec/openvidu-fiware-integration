#!/bin/bash
echo "POST_BUILD - INIT"

sed -i '' -e 's/# spring\.resources\.static-locations/spring\.resources\.static-locations/g' ./src/main/resources/application.properties
echo "POST_BUILD - application.properties restored"

rm -r ./src/main/resources/static/*
echo "POST_BUILD - resources removed"

## Moves the jar into the docker folder ##
cp target/fiware-integration-0.1.0-SNAPSHOT.jar ../docker/fiware-integration.jar
echo "POST_BUILD - package moved to docker/ folder"
echo "POST_BUILD - END"
