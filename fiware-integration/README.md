
# Instructions

## Build

To build the project you must execute the `mvn clean install` command to compile the project. This command executes the build-pre.sh and build-pro.sh to prepare the project to deploy and then restore it again.
This scripts move the generated .jar to the docker/ folder.

> **NOTE**: if the script is interrupted, uncomment the `spring.resources.static-locations` line in application.properties manually.
