#!/bin/bash

mvn clean package && mv target/gallery-war-1.0-SNAPSHOT.war /NotBackedUp/tools/wildfly/standalone/deployments/gallery.war
