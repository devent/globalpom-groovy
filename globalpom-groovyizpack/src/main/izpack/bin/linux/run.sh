#!/bin/sh

symlink=`find "$0" -printf "%l"`
cd "`dirname "${symlink:-$0}"`"

export _JAVA_OPTIONS="-Dawt.useSystemAAFontSettings=on"
libs=../../lib
mainclass="${project.custom.mainclass}"
log="-Dlog4j.configuration=file:///$PWD/../../etc/log4j.properties"

java "$log" -cp "$libs/*" "$mainclass" $*
