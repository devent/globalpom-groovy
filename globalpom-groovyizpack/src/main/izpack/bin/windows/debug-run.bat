set libs=%CD%\lib
set mainclass="${project.custom.mainclass}"
set log="-Dlog4j.configuration=file:///%CD%/etc/debug-log4j.properties"

java %log% -cp "%libs%/*"; %mainclass%
