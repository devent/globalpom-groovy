#!/bin/bash
#
# Copyright 2011-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

SHELL := /bin/bash
.DEFAULT_GOAL := help
globalpom.custom.jenkins.build.badge.main = $(shell mvn help:evaluate -Dexpression=globalpom.custom.jenkins.build.badge.main -q -DforceStdout)
globalpom.custom.jenkins.url = $(shell mvn help:evaluate -Dexpression=globalpom.custom.jenkins.url -q -DforceStdout)
globalpom.custom.sonarqube.qualitygate.badge = $(shell mvn help:evaluate -Dexpression=globalpom.custom.sonarqube.qualitygate.badge -q -DforceStdout)
globalpom.custom.sonarqube.qualitygate.link = $(shell mvn help:evaluate -Dexpression=globalpom.custom.sonarqube.qualitygate.link -q -DforceStdout)
project.inceptionYear = $(shell mvn help:evaluate -Dexpression=project.inceptionYear -q -DforceStdout)
globalpom.custom.current.year = $(shell mvn help:evaluate -Dexpression=globalpom.custom.current.year -q -DforceStdout)
project.version = $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

.PHONY: debug
debug:
	@echo $(globalpom.custom.jenkins.build.badge.main)
	@echo $(globalpom.custom.jenkins.url)
	@echo $(globalpom.custom.sonarqube.qualitygate.badge)
	@echo $(globalpom.custom.sonarqube.qualitygate.link)
	@echo $(project.inceptionYear)
	@echo $(globalpom.custom.current.year)
	@echo $(project.version)

.PHONY: update-versions
update-versions: mvn-versions-set ##@targets Updates the versions of all sub-modules
	rm -f README.textile
	rm -f README.md
	$(MAKE) update-readme VERSION=$(VERSION)

.PHONY: mvn-versions-set
mvn-versions-set:
	$(call check_defined, VERSION, VERSION must be set)
	mvn versions:set -DnewVersion=$(VERSION) && mvn versions:commit

.PHONY: update-readme
update-readme: README.textile README.md

README.textile:
	cp README.textile.tpl README.textile
	sed -ie 's|%globalpom.custom.jenkins.build.badge.main%|$(globalpom.custom.jenkins.build.badge.main)|g' README.textile
	sed -ie 's|%globalpom.custom.jenkins.url%|$(globalpom.custom.jenkins.url)|g' README.textile
	sed -ie 's|%globalpom.custom.sonarqube.qualitygate.badge%|$(globalpom.custom.sonarqube.qualitygate.badge)|g' README.textile
	sed -ie 's|%globalpom.custom.sonarqube.qualitygate.link%|$(globalpom.custom.sonarqube.qualitygate.link)|g' README.textile
	sed -ie 's|%project.inceptionYear%|$(project.inceptionYear)|g' README.textile
	sed -ie 's|%globalpom.custom.current.year%|$(globalpom.custom.current.year)|g' README.textile
	sed -ie 's|%project.version%|$(project.version)|g' README.textile

README.md:
	pandoc -t markdown -f textile -o README.md README.textile
	sed -i '1,4d' README.md
	sed -i '1 s%^%© $(project.inceptionYear)-$(globalpom.custom.current.year) Erwin Müller\n%' README.md
	sed -i '1 s%^%[![Apache License, Version 2.0](https://project.anrisoftware.com/attachments/download/217/apache2.0-small.gif)](http://www.apache.org/licenses/LICENSE-2.0)\n%' README.md
	sed -i '1 s%^%[![Gate]($(globalpom.custom.sonarqube.qualitygate.badge))]($(globalpom.custom.sonarqube.qualitygate.link))\n%' README.md
	sed -i '1 s%^%[![Build Status]($(globalpom.custom.jenkins.build.badge.main))]($(globalpom.custom.jenkins.url))\n%' README.md

#COLORS
GREEN  := $(shell tput -Txterm setaf 2)
WHITE  := $(shell tput -Txterm setaf 7)
YELLOW := $(shell tput -Txterm setaf 3)
RED := $(shell tput -Txterm setaf 1)
RESET  := $(shell tput -Txterm sgr0)

# Add the following 'help' target to your Makefile
# And add help text after each target name starting with '\#\#'
# A category can be added with @category
HELP_FUN = \
    %help; \
    while(<>) { push @{$$help{$$2 // 'options'}}, [$$1, $$3] if /^([0-9a-zA-Z\-]+)\s*:.*\#\#(?:@([a-zA-Z\-]+))?\s(.*)$$/ }; \
    print "USAGE\n\nmake [target]\n\n"; \
    for (sort keys %help) { \
    print "${WHITE}$$_:${RESET}\n"; \
    for (@{$$help{$$_}}) { \
    $$sep = " " x (32 - length $$_->[0]); \
    print "  ${YELLOW}$$_->[0]${RESET}$$sep${GREEN}$$_->[1]${RESET}\n"; \
    }; \
    print "\n"; }

help: ##@other Show this help.
	@perl -e '$(HELP_FUN)' $(MAKEFILE_LIST)

# Check that given variables are set and all have non-empty values,
# die with an error otherwise.
#
# Author: http://stackoverflow.com/questions/10858261/abort-makefile-if-variable-not-set
#
# Params:
#   1. Variable name(s) to test.
#   2. (optional) Error message to print.
check_defined = \
    $(foreach 1,$1,$(__check_defined))
__check_defined = \
    $(if $(value $1),, \
      $(error ${RED}Undefined $1$(if $(value 2), ($(strip $2)))))
