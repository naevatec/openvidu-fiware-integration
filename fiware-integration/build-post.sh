#!/bin/bash
sed -i '' -e 's/# spring\.resources\.static-locations/spring\.resources\.static-locations/g' ./src/main/resources/application.properties
rm -r ./src/main/resources/static/*
