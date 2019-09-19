
# Instructions

## Build

To build the project you must call the build.sh
This script comments the XXX line in application.properties to configure it to deploy, then it executes the command
`mvn clean install` and then it restores the line.

> **NOTE**: if the script is interrupted, uncomment the line in application.properties manually.
