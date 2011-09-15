#!/bin/sh

symlink=`find "$0" -printf "%l"`
cd "`dirname "${symlink:-$0}"`"

export _JAVA_OPTIONS="-Dawt.useSystemAAFontSettings=on"
mainjar="../../lib/${project.artifactId}-${project.version}-jar-with-dependencies.jar"
log="-Dlog4j.configuration=file:///$PWD/../../etc/debug-log4j.properties"

java "$log" -jar "$mainjar" $*
