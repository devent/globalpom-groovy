symlink=`find "$0" -printf "%l"`
cd "`dirname "${symlink:-$0}"`"

set bootstrapversion=${project.custom.bootstrap.version}
set bootstrap="../../bootstrap/bootstrap-core-$bootstrapversion-jar-with-dependencies.jar"
set libs=../../lib
set mainclass="${project.custom.bootstrap.mainclass}"
set log="-Dlog4j.configuration=file:///$PWD/../../etc/log4j.properties"

java %log% -jar %bootstrap% -libs %libs% -mainclass %mainclass%
