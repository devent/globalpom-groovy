set mainjar=%CD%\lib\${project.artifactId}-${project.version}-jar-with-dependencies.jar
set log="-Dlog4j.configuration=file:///%CD%/etc/log4j.properties"

java %log% -jar %mainjar%
