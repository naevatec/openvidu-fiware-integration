#!/bin/bash
sed -i '' -e 's/# spring\.resources\.static-locations/spring\.resources\.static-locations/g' ./src/main/resources/application.properties
rm -r ./src/main/resources/static/*

## Moves the jar into the docker folder ##
cp target/fiware-integration-0.1.0-SNAPSHOT.jar ../docker/fiware-integration.jar
