symlink=`find "$0" -printf "%l"`
cd "`dirname "${symlink:-$0}"`"

set mainjar="..\..\lib\${project.artifactId}-${project.version}-jar-with-dependencies.jar"
set log="-Dlog4j.configuration=file:///$PWD/../../etc/debug-log4j.properties"

java "%log%" -jar "%mainjar%"
