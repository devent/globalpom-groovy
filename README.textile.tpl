!%globalpom.custom.jenkins.build.badge.main%(Build Status)!:%globalpom.custom.jenkins.url%/job/main !%globalpom.custom.sonarqube.qualitygate.badge%(Gate)!:%globalpom.custom.sonarqube.qualitygate.link% !https://project.anrisoftware.com/attachments/download/217/apache2.0-small.gif(Apache License, Version 2.0)!:http://www.apache.org/licenses/LICENSE-2.0 © %project.inceptionYear%&#45;%globalpom.custom.current.year% Erwin Müller

h1. Description

Declares all dependencies and all Maven plug-ins needed for a Advanced Natural Research Institute software project. It will declare the listed properties, add plug-in to deploy source and test sources and deploy the project-tests with the project jar.

h1. Links

* "Generated Site":https://javadoc.anrisoftware.com/com.anrisoftware.globalpom/globalpom-groovy-parent/%project.version%/index.html
* "Download (Central)":https://search.maven.org/artifact/com.anrisoftware.globalpom/globalpom-groovy-base/%project.version%/pom
* "Source code":https://gitea.anrisoftware.com/com.anrisoftware.globalpom/globalpom-groovy
* "Source code (Github)":https://github.com/devent/globalpom-groovy
* "Project Home":https://project.anrisoftware.com/projects/globalpom-groovy
* "Project Roadmap":https://project.anrisoftware.com/projects/globalpom-groovy/roadmap
* "Project Issues":https://project.anrisoftware.com/projects/globalpom-groovy/issues
* "Jenkins":%globalpom.custom.jenkins.url%

h1. Packages Overview

!https://project.anrisoftware.com/attachments/download/447/packages.svg(Packages Overview)!

h1. License

Copyright ©%project.inceptionYear% - %globalpom.custom.current.year% "Advanced Natural Research Institute":https://anrisoftware.com/. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

h5. Markdown

<pre>
pandoc -t markdown -f textile -o README.md README.textile
</pre>

<pre>
[![Build Status](%globalpom.custom.jenkins.build.badge.main%)](%globalpom.custom.jenkins.url%/job/main)
[![Gate](%globalpom.custom.sonarqube.qualitygate.badge%)](%globalpom.custom.sonarqube.qualitygate.link%)
[![Apache License, Version 2.0](https://project.anrisoftware.com/attachments/download/217/apache2.0-small.gif)](http://www.apache.org/licenses/LICENSE-2.0)
© %project.inceptionYear% - %globalpom.custom.current.year% Erwin Müller
</pre>
