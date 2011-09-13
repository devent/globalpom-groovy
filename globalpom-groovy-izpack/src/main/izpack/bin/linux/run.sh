#!/bin/sh

symlink=`find "$0" -printf "%l"`
cd "`dirname "${symlink:-$0}"`"

export _JAVA_OPTIONS="-Dawt.useSystemAAFontSettings=on"
bootstrapversion=${project.custom.bootstrap.version}
bootstrap="../../bootstrap/bootstrap-core-$bootstrapversion-jar-with-dependencies.jar"
libs=../../lib
mainclass="${project.custom.bootstrap.mainclass}"
log="-Dlog4j.configuration=file:///$PWD/../../etc/log4j.properties"

java "$log" -jar "$bootstrap" -libs "$libs" -mainclass "$mainclass" $*
