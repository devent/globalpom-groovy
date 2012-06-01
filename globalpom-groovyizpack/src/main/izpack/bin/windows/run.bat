set libs=%CD%\lib
set mainclass="${project.custom.mainclass}"
set log="-Dlog4j.configuration=file:///%CD%/etc/log4j.properties"

start javaw %log% -cp "%libs%/*"; %mainclass%
