# globalpom-groovy

## Description

The project is suppose to be used by all Maven projects that wants to have
Groovy support. It is added as the parent POM to the project, and is adding
the groovy-eclipse-compiler and groovy-eclipse-batch plug-ins into the build
cycle. The build-helper-maven-plugin is also added to the build cycle to
recognize src/main/groovy and src/test/groovy if the java source directories
are empty.

## Maven

```
<parent>
    <artifactId>globalpom-groovy</artifactId>
    <groupId>com.anrisoftware.globalpom</groupId>
    <version>2.2</version>
    <relativePath />
</parent>
```

# globalpom-groovy-osgi

## Description

The project is suppose to be used by all Maven OSGi bundles that wants to have
Groovy support. In addition to the Groovy plug-ins, the `maven-bundle-plugin` is added
that exports the package defined in the `project.custom.bundle.namespace`
property and protects the package defined in the `project.custom.bundle.namespace.internal`
property. Also, the `osgi.bnd` is added to define project specific OSGi properties.
The `maven-scr-plugin` is used to declare declarative services.

| Property      | Description   |
| ------------- |:-------------:|
| `project.custom.bundle.namespace` | The exported package, defaults to `com.anrisoftware.sscontrol` |
| `project.custom.bundle.namespace.internal` | The protected package, defaults to `com.anrisoftware.sscontrol.internal` |

## Maven Module

```
<parent>
    <groupId>com.anrisoftware.globalpom</groupId>
    <artifactId>globalpom-groovy-osgi</artifactId>
    <version>2.2</version>
    <relativePath />
</parent>
```

# globalpom-groovytestutils

## Description

The project is a collection of test utilities on top of JUnit.

* <<<com.anrisoftware.globalpom.utils.frametesting>>>
  * FrameTesting
  Creates a frame fixture to test components in a frame.
  * DialogTesting
  Creates a frame fixture to test components in a dialog.

## Maven Module

```
<dependency>
    <groupId>com.anrisoftware.globalpom</groupId>
    <artifactId>globalpom-groovytestutils</artifactId>
    <version>2.2</version>
    <scope>test</scope>
</dependency>
```

# SCM

* [Main Repository](https://anrisoftware.com/projects/projects/globalpom-groovy/repository)
* `git@anrisoftware.com:globalpom-groovy.git`
* [Github Mirror Repository](https://github.com/devent/globalpom-groovy)
* `git@github.com:devent/globalpom-groovy.git`

# License

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
