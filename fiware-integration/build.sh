#!/bin/bash
#Call pre build commands
source build-pre.sh

mvn clean install

#Call post build commands
source build-post.sh
