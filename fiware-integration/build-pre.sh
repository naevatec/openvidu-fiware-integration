#!/bin/bash
cp -r ./src/frontend/* ./src/main/resources/static/
sed -i '' -e 's/spring\.resources\.static-locations/# spring\.resources\.static-locations/g' ./src/main/resources/application.properties

# Set the minified versions to production.
find ./src/main/resources/static -name "*.min.js" | while read oldFileName; do
  newFileName=$(echo $oldFileName | sed s/.min//g)
  mv $oldFileName $newFileName
done
