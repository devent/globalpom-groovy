# globalpom-groovy

## Description

The project is suppose to be used by all Maven projects that wants to have
Groovy support. It is added as the parent POM to the project, and is adding
the groovy-eclipse-compiler and groovy-eclipse-batch plug-ins into the build
cycle. The build-helper-maven-plugin is also added to the build cycle to
recognize src/main/groovy and src/test/groovy if the java source directories
are empty.


## SCM

* [Main Repository](https://anrisoftware.com/projects/projects/globalpom-groovy/repository)
* git@anrisoftware.com:globalpom-groovy.git
* [Github Mirror Repository](https://github.com/devent/globalpom-groovy)
* git@github.com:devent/globalpom-groovy.git

## Maven

```
<parent>
    <artifactId>globalpom-groovy</artifactId>
    <groupId>com.anrisoftware.globalpom</groupId>
    <version>2.1</version>
    <relativePath />
</parent>
```

## License

Copyright 2011-2016 Erwin Müller <erwin.mueller@deventm.org>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
