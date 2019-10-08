#!/bin/bash
echo "POST_BUILD - INIT"

## Moves the jar into the docker folder ##
cp target/openvidu-filters-client-1.0.0.jar ../docker/openvidu-filters-client.jar
echo "POST_BUILD - package moved to docker/ folder"
echo "POST_BUILD - END"
