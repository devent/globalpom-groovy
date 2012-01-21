set bootstrapversion=${project.custom.bootstrap.version}
set bootstrap="bootstrap/bootstrap-cli-%bootstrapversion%-jar-with-dependencies.jar"
set libs=lib
set mainclass="${project.custom.bootstrap.mainclass}"
set log="-Dlog4j.configuration=file:///%CD%/etc/log4j.properties"

java %log% -jar %bootstrap% -libs %libs% -mainclass %mainclass%
